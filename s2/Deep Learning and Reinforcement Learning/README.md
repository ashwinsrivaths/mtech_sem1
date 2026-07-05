# 
# nural network
- until nwe we have used existing model/equation
- now we will build the best equation


# optimizers
- SGD => W = W - neeta partialDerrivative(error vs Weight)
- Nag
- Adarad Elongated
- RMS prop
- Adam => Momentum + RMS Prop


# regularizer
solves the problem of overfitting
- l1 - lasso => feature selection
- l2 - ridge
- l1-l2


# dropuot
- during training, randomly in the pass, a neuron is deactivated
- this helps reduce overfitting
- other neurons are forced to generalize and learn other neurons tasks


# Batch Normalization
- this is a best practice used only during training
- the model learns faster on normal data
- every layer operates on the data and changes its distribution
- this increases the training time
- batch normalization to be used after the activation function to renormalize the data output in the first few layers




# qlearning

# sarsa

# dqn

## ---------- all
- backword ripple
  - even though we move from start to goal the weights updates from goal to start
- above ones dont wait for completion of episode for weights updation



# reinforce 
- waits for episode to complete