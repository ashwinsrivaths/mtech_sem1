# LLM

- finetuning model
  - note that if the fine tuning data set is as large as training data set, these methods are useless
  - following are the methods used with lora being the best
    - prefix layer
      - making changes to the promt
      - as i increase this there is increase in quality then drop making it nm monotonous and not scalable
    - prefix embeding
      - modify word embedings vectors
      - as i increase this there is increase in quality then drop making it nm monotonous and not scalable
    - adapter layer
      - bw two frozen layers there is an adapter layer
    - lora
      - i can apply lora to any frozen block
      - LoRA adds low-rank matrices to the frozen original machine learning model. The low-rank matrices are updated through gradient descent during fine-tuning, without modifying the weights of the base model. These matrices contain new weights to apply to the model when generating results. The multiplied change matrix is added to the base model weights to get the final fine-tuned model. This process alters the outputs that the model produces with minimal computing power and training time.
      - for every transformer bolck (every attention block) no need to add lora or low rank trainiable matrix
        - An attention block is just one specific component inside a larger transformer block
      - https://www.ibm.com/think/topics/lora
        - during training
          - in the image the blue is the original weights that are frozen
          - the yellow is the lora component
          - first the input is mulitplied with a matrix reducing its dimention down (this helps us reduce the number of trainable components)
          - then another matrix B is multiplied with this low rank matrix to bring it back to the required output size
          - this is then added to the original output
        - after training
          - this is then merged with the original weights to give us back a single block
            - h = (W + BA)x
            - h = final output
            - W = original weights matrix
            - A = input shrinking matrix
            - B = shrinked matrix expansion matrix
            - x = input
- integration of lora into Multi-head Attention
  - In multihead attention block 
    - we have 3 matrix Wq, Wk, Wv and we multiply the input to the three of them to get Q, K and V
  - in lora, we multiply the input to 3 more matrics - the lora matricx and then add their outputs the their corresponding Q, K and V matrics and we then continue as usual as we do in attention model












