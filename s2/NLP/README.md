corpus is a collection of documents
document is a collection of tokens

watsapp message -> hi (document)
watsapp message - > long paragraph/sentence/line (document)
collection of messages/documents -> corpus (page/multiple messages)



word to vect -> cosine similarity captured

pos tagging is associating a word with noun/verb characterstic

- NER name entity recoginition of words in a sentence
  - eg gorge washington - person
  - washington dc - place


- Bag of words can not capture the order of words
  - count vectoriser is a orderless table capturing only the ferquency of occurance of words
    - each row in the table is a document
    - the columns are words
  - TF-IDF
    - this capures the importance of words
    - if a word occurs in my document or sentence and does not occure any where else in the corpous then the value is very high
    - else if the word occurs freely in the corpous(even though multiple times in my document) then the value is low
    - tf-idf captures the relative importance of words and its value varies based on the corpous
    - if a word occurs acorss all documents in the corpous then the tf-idf value is 0 irrespective of its frequency in my document


- Now we want to capture the order of words using a vector for a word that is defined by capturing the neighbours of words
-  semantics learnt for a given word is only affected by its surrounding words.
- Techniques for finding the vector of word
  - In continous bag of words we know the neighbors and try to find the word
  - in skip gram we know the word and we try to find its neighbors (gives better results and is much more challenging)
  - basically we have a push and pull of vectors and every new document pushes and pulls our vectors


- vector embedding for words
  - related words should have vectors that are having lesser angle between them


- word to vect retains the context only for the window
- count vectorizer and TF-IDF use bag of words and dont concern themselves with order
- tf-IDF is a improvement on count vectorizer where more frequiently occuring words acorss documents are peanilized while count vectorizer is just a frequency table that is very sparce




- pos tagging is identifying the part of speech a particular word is


- static embeding
  - ever word has a corresponding vector in the vector space
- contexual embeding
  - 







- Transformer arcetecture
  - matrix multiplication done by a computer with sufficient cores is O(1) operation or takes constant time 




  - each tocken corresponding to it there is
    - q query matrix
    - k key matrix
    - v value matrix

- self attention algorithm
  - projection
    - generate query, keys and values
  - scoring
    - 


- Match ($Q K^T$): Multiply the Query matrix by the transposed Key matrix using dot products. This computes a raw similarity score between every word and every other word.
- Scale ($\frac{1}{\sqrt{d_k}}$): Divide the scores by the square root of the dimension of the key vectors ($d_k$) to prevent exploding numbers that cause gradient issues during training.
- Normalize ($\text{softmax}$): Apply a softmax function to turn the scores into probabilities (percentages that sum to 1). This is the Attention Map.
- Extract ($V$): Multiply the attention probabilities by the Value matrix ($V$). This produces a final, context-aware representation for each word, blending in the meaning of the words it paid attention to.



- capital => entire document
- small => for that token


