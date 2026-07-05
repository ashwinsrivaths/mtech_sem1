"""
PPO — Proximal Policy Optimization
===================================

Two examples:
   Example 1: Hand-built 2x2 grid (same as Q-learning/SARSA/DQN/REINFORCE examples)
   Example 2: CartPole-v1 with Gymnasium

PPO components:
   - Actor network  (policy: outputs action probabilities via softmax)
   - Critic network (value: outputs single V(s) estimate)
   - Advantage      A_t = G_t - V(s)
   - Clipping       ratio clipped to [1-epsilon, 1+epsilon]
   - K epochs       reuse same batch multiple times
   - Bootstrapping  handle truncated episodes via critic

Key differences from REINFORCE:
   REINFORCE:  1 network, raw G_t, no clipping, 1 update per episode
   PPO:        2 networks, advantage A_t, clipped ratio, K epochs per batch
"""

import numpy as np
import torch
import torch.nn as nn
import gymnasium as gym
from collections import deque

# ─────────────────────────────────────────────────────────────────────────────
# SHARED HELPERS
# ─────────────────────────────────────────────────────────────────────────────
def compute_returns(rewards, dones, last_value, gamma):
    """
    Compute discounted returns G_t backwards from the end of batch.
    Handles both terminated (done=True, future=0) and
    truncated (done=True but bootstrap from last_value) episodes.
    """
    returns = []
    G = last_value   # bootstrap value: 0 if terminated, V(last_state) if truncated
    for r, d in zip(reversed(rewards), reversed(dones)):
        G = r + gamma * G * (1.0 - d)   # (1-d) zeroes out future if episode ended
        returns.insert(0, G)
    return returns


def compute_advantages(returns, values):
    """
    A_t = G_t - V(s_t)
    Normalized to zero mean, unit variance (reduces variance in training).
    """
    returns_t  = torch.FloatTensor(returns)
    values_t   = torch.FloatTensor(values)
    advantages = returns_t - values_t
    # normalize
    advantages = (advantages - advantages.mean()) / (advantages.std() + 1e-8)
    return advantages, returns_t


# ═════════════════════════════════════════════════════════════════════════════
# EXAMPLE 1 — PPO ON HAND-BUILT 2x2 GRID
# ═════════════════════════════════════════════════════════════════════════════
"""
Grid:
    +------+------+
    |  S0  |  S1  |
    | start|      |
    +------+------+
    |  S2  | GOAL |
    |      | +10  |
    +------+------+

Same environment as Q-learning, SARSA, DQN, REINFORCE examples.
Only the algorithm changes.
"""

# ── Environment ───────────────────────────────────────────────────────────────
S0, S1, S2, GOAL = 0, 1, 2, 3
ACTION_NAMES = ["UP", "DOWN", "LEFT", "RIGHT"]
STATE_NAMES  = ["S0", "S1", "S2", "GOAL"]

TRANSITIONS = {
    S0: {0: S0,   1: S2,   2: S0,   3: S1},   # UP, DOWN, LEFT, RIGHT
    S1: {0: S1,   1: GOAL, 2: S0,   3: S1},
    S2: {0: S0,   1: S2,   2: S2,   3: GOAL},
}

def env_step(state, action):
    """Hand-built environment — same as previous examples."""
    next_state = TRANSITIONS[state][action]
    if next_state == GOAL:
        return GOAL, 10.0, True
    return next_state, -1.0, False

def env_reset():
    return S0

def encode(state):
    """One-hot encode state for neural network input."""
    v = np.zeros(3, dtype=np.float32)
    if state < 3:
        v[state] = 1.0
    return v


# ── Networks ──────────────────────────────────────────────────────────────────
class ActorGrid(nn.Module):
    """
    Actor: state -> action probabilities
    Input:  3 (one-hot encoded state)
    Output: 4 (probability per action, via softmax)
    """
    def __init__(self):
        super().__init__()
        self.net = nn.Sequential(
            nn.Linear(3, 16), nn.ReLU(),
            nn.Linear(16, 4),
            nn.Softmax(dim=-1)   # outputs probabilities that sum to 1
        )
    def forward(self, x):
        return self.net(x)


class CriticGrid(nn.Module):
    """
    Critic: state -> single value V(s)
    Input:  3 (one-hot encoded state)
    Output: 1 (expected return from this state, NO softmax)
    """
    def __init__(self):
        super().__init__()
        self.net = nn.Sequential(
            nn.Linear(3, 16), nn.ReLU(),
            nn.Linear(16, 1)     # single unconstrained value
        )
    def forward(self, x):
        return self.net(x).squeeze(-1)   # shape (batch,) not (batch,1)


def run_ppo_grid(n_iterations=100, T=32, K=4, gamma=0.9,
                 lr_actor=0.01, lr_critic=0.01, epsilon=0.2, verbose=True):
    """
    PPO on the 2x2 hand-built grid.

    n_iterations: how many collect+train cycles
    T:            steps per batch (small for this tiny environment)
    K:            epochs per batch
    epsilon:      clipping range [1-epsilon, 1+epsilon]
    """
    print("\n" + "="*60)
    print("PPO on Hand-Built 2x2 Grid")
    print("="*60)

    actor  = ActorGrid()
    critic = CriticGrid()

    opt_actor  = torch.optim.Adam(actor.parameters(),  lr=lr_actor)
    opt_critic = torch.optim.Adam(critic.parameters(), lr=lr_critic)

    scores = []

    for iteration in range(n_iterations):

        # ── PHASE 1: COLLECT T steps ─────────────────────────────────────
        states_buf    = []
        actions_buf   = []
        rewards_buf   = []
        dones_buf     = []
        log_probs_old = []   # saved during collection, FIXED reference
        values_buf    = []   # V(s) from critic during collection

        state = env_reset()
        done  = False
        ep_reward = 0
        ep_scores = []

        for _ in range(T):
            state_t = torch.FloatTensor(encode(state))

            # actor: sample action from probabilities
            with torch.no_grad():
                probs = actor(state_t)
                value = critic(state_t)

            dist   = torch.distributions.Categorical(probs)
            action = dist.sample()
            log_prob_old = dist.log_prob(action)   # SAVE — fixed reference

            next_state, reward, done = env_step(state, action.item())
            ep_reward += reward

            # store experience
            states_buf.append(encode(state))
            actions_buf.append(action.item())
            rewards_buf.append(reward)
            dones_buf.append(float(done))
            log_probs_old.append(log_prob_old.item())
            values_buf.append(value.item())

            state = next_state

            if done:
                ep_scores.append(ep_reward)
                ep_reward = 0
                state = env_reset()
                done  = False

        # bootstrap: if last episode truncated (not terminated), use critic
        with torch.no_grad():
            last_value = 0.0 if done else critic(
                torch.FloatTensor(encode(state))).item()

        # ── PHASE 2: COMPUTE returns and advantages ───────────────────────
        returns    = compute_returns(rewards_buf, dones_buf, last_value, gamma)
        advantages, returns_t = compute_advantages(returns, values_buf)

        # convert everything to tensors
        states_t      = torch.FloatTensor(np.array(states_buf))
        actions_t     = torch.LongTensor(actions_buf)
        log_probs_old_t = torch.FloatTensor(log_probs_old)
        # log_probs_old_t is FIXED — never recomputed during K epochs

        # ── PHASE 3: TRAIN for K epochs ──────────────────────────────────
        for epoch in range(K):

            # recompute log_prob_new with CURRENT (updated) actor
            probs_new    = actor(states_t)
            dist_new     = torch.distributions.Categorical(probs_new)
            log_probs_new = dist_new.log_prob(actions_t)

            # ratio = pi_new / pi_old  (in log space for stability)
            ratio = torch.exp(log_probs_new - log_probs_old_t)

            # clipped PPO objective
            unclipped = ratio * advantages
            clipped   = torch.clamp(ratio, 1 - epsilon, 1 + epsilon) * advantages

            # actor loss: negative of conservative minimum
            actor_loss = -torch.min(unclipped, clipped).mean()

            # critic loss: MSE between predicted V(s) and actual G_t
            values_pred  = critic(states_t)
            critic_loss  = nn.MSELoss()(values_pred, returns_t)

            # update actor
            opt_actor.zero_grad()
            actor_loss.backward()
            opt_actor.step()

            # update critic
            opt_critic.zero_grad()
            critic_loss.backward()
            opt_critic.step()

        # ── PHASE 4: DISCARD batch, start fresh ──────────────────────────
        avg_score = np.mean(ep_scores) if ep_scores else 0
        scores.append(avg_score)

        if verbose and iteration % 20 == 0:
            print(f"  Iteration {iteration:3d} | "
                  f"avg score {avg_score:6.2f} | "
                  f"actor_loss {actor_loss.item():6.3f} | "
                  f"critic_loss {critic_loss.item():6.3f}")

    # ── Results ──────────────────────────────────────────────────────────────
    if verbose:
        print("\nLearned policy (action probabilities per state):")
        print(f"{'':6}" + "".join(f"{a:>8}" for a in ACTION_NAMES))
        for s in [S0, S1, S2]:
            with torch.no_grad():
                probs = actor(torch.FloatTensor(encode(s))).numpy()
            print(f"{STATE_NAMES[s]:6}" + "".join(f"{probs[a]:8.3f}" for a in range(4)))

        print("\nOptimal policy (highest prob action per state):")
        for s in [S0, S1, S2]:
            with torch.no_grad():
                probs = actor(torch.FloatTensor(encode(s))).numpy()
            best = ACTION_NAMES[int(np.argmax(probs))]
            prob = np.max(probs)
            print(f"  {STATE_NAMES[s]} -> {best}  (prob={prob:.3f})")

        print("\nCritic's learned V(s):")
        for s in [S0, S1, S2]:
            with torch.no_grad():
                v = critic(torch.FloatTensor(encode(s))).item()
            print(f"  V({STATE_NAMES[s]}) = {v:.2f}")

        print(f"\nAvg score (last 20 iterations): {np.mean(scores[-20:]):.2f}")

    return actor, critic


# ═════════════════════════════════════════════════════════════════════════════
# EXAMPLE 2 — PPO ON CARTPOLE WITH GYMNASIUM
# ═════════════════════════════════════════════════════════════════════════════
"""
CartPole-v1:
   State:  4 continuous floats [pos, vel, angle, angular_vel]
   Action: 2 discrete (push left=0, push right=1)
   Reward: +1 every step pole stays upright
   Done:   pole falls or 500 steps (truncated at 500 = success)

No encoding needed — state is already numbers.
Wider networks (64 units) to handle more complex state.
"""

class ActorCartPole(nn.Module):
    """
    Actor for CartPole.
    Input:  4 continuous state values (no encoding needed)
    Output: 2 action probabilities via softmax
    """
    def __init__(self, n_states=4, n_actions=2):
        super().__init__()
        self.net = nn.Sequential(
            nn.Linear(n_states, 64), nn.ReLU(),
            nn.Linear(64, 64),       nn.ReLU(),
            nn.Linear(64, n_actions),
            nn.Softmax(dim=-1)
        )
    def forward(self, x):
        return self.net(x)


class CriticCartPole(nn.Module):
    """
    Critic for CartPole.
    Input:  4 continuous state values
    Output: single V(s) value, no softmax
    """
    def __init__(self, n_states=4):
        super().__init__()
        self.net = nn.Sequential(
            nn.Linear(n_states, 64), nn.ReLU(),
            nn.Linear(64, 64),       nn.ReLU(),
            nn.Linear(64, 1)
        )
    def forward(self, x):
        return self.net(x).squeeze(-1)


def run_ppo_cartpole(n_iterations=200, T=2048, K=10, gamma=0.99,
                     epsilon=0.2, lr=3e-4, minibatch_size=64, verbose=True):
    """
    PPO on CartPole-v1 with Gymnasium.

    n_iterations:    how many collect+train cycles
    T:               steps per batch (2048 is standard PPO setting)
    K:               epochs per batch (10 is standard)
    epsilon:         clipping range (0.2 is standard)
    minibatch_size:  size of each minibatch within K epochs
    """
    print("\n" + "="*60)
    print("PPO on CartPole-v1 (Gymnasium)")
    print("="*60)

    env = gym.make("CartPole-v1")
    n_states  = env.observation_space.shape[0]   # 4
    n_actions = env.action_space.n               # 2

    print(f"States: {n_states} continuous, Actions: {n_actions} discrete")
    print(f"T={T} steps/batch, K={K} epochs, minibatch={minibatch_size}")

    actor  = ActorCartPole(n_states, n_actions)
    critic = CriticCartPole(n_states)

    # single optimizer for both (common PPO practice)
    optimizer = torch.optim.Adam(
        list(actor.parameters()) + list(critic.parameters()), lr=lr
    )

    scores = []
    state, _ = env.reset()

    for iteration in range(n_iterations):

        # ── PHASE 1: COLLECT T steps ─────────────────────────────────────
        states_buf    = []
        actions_buf   = []
        rewards_buf   = []
        dones_buf     = []
        log_probs_old = []
        values_buf    = []

        ep_rewards = []
        ep_reward  = 0
        terminated = False

        for _ in range(T):
            state_t = torch.FloatTensor(state)   # no encoding needed for CartPole

            with torch.no_grad():
                probs = actor(state_t)
                value = critic(state_t)

            dist     = torch.distributions.Categorical(probs)
            action   = dist.sample()
            log_prob = dist.log_prob(action)     # SAVE log_prob_old

            next_state, reward, term, trunc, _ = env.step(action.item())
            done = term or trunc
            ep_reward += reward

            states_buf.append(state)
            actions_buf.append(action.item())
            rewards_buf.append(reward)
            dones_buf.append(float(done))
            log_probs_old.append(log_prob.item())
            values_buf.append(value.item())

            state = next_state
            terminated = term

            if done:
                ep_rewards.append(ep_reward)
                ep_reward = 0
                state, _ = env.reset()
                terminated = False

        # bootstrap: truncated episode (still going) -> use critic
        # terminated episode -> future = 0
        with torch.no_grad():
            last_value = 0.0 if terminated else critic(
                torch.FloatTensor(state)).item()

        # ── PHASE 2: COMPUTE returns and advantages ───────────────────────
        returns    = compute_returns(rewards_buf, dones_buf, last_value, gamma)
        advantages, returns_t = compute_advantages(returns, values_buf)

        states_t        = torch.FloatTensor(np.array(states_buf))
        actions_t       = torch.LongTensor(actions_buf)
        log_probs_old_t = torch.FloatTensor(log_probs_old)
        # log_probs_old_t FIXED — never recomputed during K epochs

        # ── PHASE 3: TRAIN for K epochs with minibatches ─────────────────
        total_actor_loss  = 0
        total_critic_loss = 0

        for epoch in range(K):
            # shuffle indices for minibatching
            indices = torch.randperm(T)

            for start in range(0, T, minibatch_size):
                idx = indices[start:start + minibatch_size]

                mb_states     = states_t[idx]
                mb_actions    = actions_t[idx]
                mb_advantages = advantages[idx]
                mb_returns    = returns_t[idx]
                mb_log_old    = log_probs_old_t[idx]

                # recompute log_prob_new with CURRENT actor
                probs_new     = actor(mb_states)
                dist_new      = torch.distributions.Categorical(probs_new)
                log_probs_new = dist_new.log_prob(mb_actions)

                # ratio = pi_new / pi_old
                ratio = torch.exp(log_probs_new - mb_log_old)

                # clipped PPO objective
                unclipped  = ratio * mb_advantages
                clipped    = torch.clamp(ratio, 1-epsilon, 1+epsilon) * mb_advantages
                actor_loss = -torch.min(unclipped, clipped).mean()

                # critic loss
                values_pred  = critic(mb_states)
                critic_loss  = nn.MSELoss()(values_pred, mb_returns)

                # optional entropy bonus (encourages exploration)
                entropy = dist_new.entropy().mean()

                # total loss
                loss = actor_loss + 0.5 * critic_loss - 0.01 * entropy

                optimizer.zero_grad()
                loss.backward()
                torch.nn.utils.clip_grad_norm_(
                    list(actor.parameters()) + list(critic.parameters()), 0.5
                )   # gradient clipping for stability
                optimizer.step()

                total_actor_loss  += actor_loss.item()
                total_critic_loss += critic_loss.item()

        # ── PHASE 4: DISCARD batch ────────────────────────────────────────
        avg_score = np.mean(ep_rewards) if ep_rewards else 0
        scores.append(avg_score)

        if verbose and iteration % 20 == 0:
            recent_avg = np.mean(scores[-20:]) if len(scores) >= 20 else np.mean(scores)
            print(f"  Iter {iteration:3d} | "
                  f"score {avg_score:6.1f} | "
                  f"avg(20) {recent_avg:6.1f} | "
                  f"actor_loss {total_actor_loss/K:.3f} | "
                  f"critic_loss {total_critic_loss/K:.3f}")

    env.close()

    if verbose:
        print(f"\nFinal avg score (last 20 iterations): {np.mean(scores[-20:]):.1f}")
        print("Score 500 = perfect (pole balanced for max steps)")

    return actor, critic


# ─────────────────────────────────────────────────────────────────────────────
# INFERENCE — deploy any trained PPO model
# ─────────────────────────────────────────────────────────────────────────────
def inference_grid(actor):
    """Run greedy inference on the 2x2 grid."""
    print("\n--- Inference: PPO on 2x2 Grid ---")
    state = env_reset()
    done  = False
    path  = [STATE_NAMES[state]]
    total_reward = 0

    while not done:
        with torch.no_grad():
            probs  = actor(torch.FloatTensor(encode(state)))
        action = int(torch.argmax(probs))   # greedy: highest prob
        next_state, reward, done = env_step(state, action)
        path.append(f"--({ACTION_NAMES[action]})--> {STATE_NAMES[next_state]}")
        total_reward += reward
        state = next_state

    print(f"  Path: {'  '.join(path)}")
    print(f"  Total reward: {total_reward}")


def inference_cartpole(actor, n_episodes=5):
    """Run greedy inference on CartPole."""
    print("\n--- Inference: PPO on CartPole ---")
    env = gym.make("CartPole-v1")
    scores = []

    for ep in range(n_episodes):
        state, _ = env.reset()
        done = False
        score = 0
        while not done:
            with torch.no_grad():
                probs  = actor(torch.FloatTensor(state))
            action = int(torch.argmax(probs))   # greedy at inference
            state, reward, term, trunc, _ = env.step(action)
            done = term or trunc
            score += reward
        scores.append(score)

    env.close()
    print(f"  Scores over {n_episodes} episodes: {[int(s) for s in scores]}")
    print(f"  Average: {np.mean(scores):.1f}")


# ─────────────────────────────────────────────────────────────────────────────
# PPO vs REINFORCE — side by side comparison
# ─────────────────────────────────────────────────────────────────────────────
def print_comparison():
    print("\n" + "="*60)
    print("PPO vs REINFORCE — Key Differences")
    print("="*60)
    rows = [
        ("Networks",       "1 (actor only)",           "2 (actor + critic)"),
        ("Update signal",  "raw G_t",                  "advantage A_t = G_t - V(s)"),
        ("Variance",       "HIGH (noisy G_t)",          "LOW (baseline reduces it)"),
        ("Update size",    "unlimited",                 "clipped to [1-e, 1+e] ratio"),
        ("Stability",      "LOW (can destroy policy)",  "HIGH (clip prevents jumps)"),
        ("Data reuse",     "once and discard",          "K epochs on same batch"),
        ("Batch",          "1 episode at a time",       "T steps across many episodes"),
        ("Truncation",     "just end episode",          "bootstrap from critic"),
        ("Exploration",    "stochastic policy",         "stochastic policy + entropy bonus"),
        ("log_prob_old",   "not needed",                "saved during collection, fixed"),
    ]
    print(f"\n{'Property':<20} {'REINFORCE':<30} {'PPO'}")
    print("-"*75)
    for prop, reinforce, ppo in rows:
        print(f"{prop:<20} {reinforce:<30} {ppo}")


# ─────────────────────────────────────────────────────────────────────────────
# MAIN
# ─────────────────────────────────────────────────────────────────────────────
if __name__ == "__main__":

    print("PPO — Two Examples")
    print("Example 1: Hand-built 2x2 grid (tiny, see every mechanism)")
    print("Example 2: CartPole-v1 with Gymnasium (real benchmark)")

    # ── Example 1: Hand-built grid ────────────────────────────────────────
    print("\n" + "─"*60)
    print("EXAMPLE 1 — PPO on 2x2 Hand-Built Grid")
    print("─"*60)
    print("Settings: T=32 steps/batch, K=4 epochs, epsilon=0.2")
    print("Small T because grid is tiny (only 3 non-terminal states)")

    actor_grid, critic_grid = run_ppo_grid(
        n_iterations=100,
        T=32,
        K=4,
        gamma=0.9,
        lr_actor=0.01,
        lr_critic=0.01,
        epsilon=0.2,
    )

    inference_grid(actor_grid)

    # ── Example 2: CartPole ───────────────────────────────────────────────
    print("\n" + "─"*60)
    print("EXAMPLE 2 — PPO on CartPole-v1 (Gymnasium)")
    print("─"*60)
    print("Settings: T=2048 steps/batch, K=10 epochs, minibatch=64")
    print("Standard PPO hyperparameters (100 iterations ~5 min)")

    actor_pole, critic_pole = run_ppo_cartpole(
        n_iterations=100,   # increase to 200+ for better scores
        T=2048,
        K=10,
        gamma=0.99,
        epsilon=0.2,
        lr=3e-4,
        minibatch_size=64,
    )

    inference_cartpole(actor_pole, n_episodes=5)

    # ── Comparison ────────────────────────────────────────────────────────
    print_comparison()

    print("\n" + "="*60)
    print("KEY THINGS TO NOTICE IN THE CODE:")
    print("="*60)
    print("""
1. TWO NETWORKS:
      actor  -> action probabilities (softmax output)
      critic -> single value V(s)    (no softmax, any real number)

2. log_prob_old SAVED ONCE during collection, NEVER recomputed
      log_probs_old_t is FIXED across all K epochs
      log_probs_new  is recomputed fresh at start of EACH epoch
      ratio = exp(log_new - log_old) -> grows each epoch

3. CLIPPING:
      unclipped = ratio * advantage
      clipped   = clamp(ratio, 1-e, 1+e) * advantage
      actor_loss = -min(unclipped, clipped).mean()
      min() always picks the conservative option

4. K EPOCHS on same batch:
      ratio starts at 1.0 (epoch 1, no change yet)
      grows each epoch as policy updates
      once ratio hits clip boundary -> gradient -> 0 -> natural stop

5. BOOTSTRAP for truncated episodes:
      last_value = 0           if terminated (pole fell)
      last_value = critic(s)   if truncated  (500 steps, still going)
      used as starting G for backwards return computation

6. ADVANTAGE NORMALIZATION:
      A_t = (A_t - mean) / std
      centers signal, reduces variance, stable training

7. ENTROPY BONUS (CartPole only):
      loss -= 0.01 * entropy
      encourages exploration, prevents premature policy collapse

8. GRADIENT CLIPPING (CartPole only):
      clip_grad_norm_(params, 0.5)
      additional stability measure for larger networks
    """)
