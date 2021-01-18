# Attention and Transformers

Attention is an added layer that lets a model focus on what is important. `Queries`, `Values` and `Keys` are used for information retrieval inside the Attention layer.

The attention mechanism uses encoded representations of both the input (encoder hidden states) and the outputs (decoder hidden states)

Three ways of attention:
* **Encoder/Decoder attention**: One sentence (decoder) looks at another one (encoder)
* **Causal (self) attention**: In one sentence, words look at previous words (used for generation)
* **Bi-directional self attention**: In one sentence, words look at both previous and future words - used in BERT and T5 that use a masking operation to train.

Q, K and V are obtained by adding a dense layers after the embedding layers.

Why do we need transformers?
* LSTMS are hard to train
* Very hard/near impossible to apply transfer learning techniques on LSTMs
* Transformers can scale, all the computation can be done fully in parallel

## Dot Product Attention

Q, K, V are matrices of embeddings.
```
Attention(Q,K,V) = softmax(Q*K.T)*V
```

## Causal Attention

It helps the encoder look at other words in the input sentence while encoding a specific word.
A mask is added to prevent looking at future words.
```
M = [[0 -inf -inf ... -inf]
     [0    0 -inf ... -inf]
     ...
     [0    0    0 ...    0]]
SelfAttention(Q,K,V) = softmax(Q*K.T + M)*V
```

## Multi-Head Attention

It is similar to the regular attention mechanism but done multiple time in parallel to let the network learn different semantic meanings of attention (one for grammar, one for vocabulary, one for conjugation, etc).

It adds a Position Encoding module to treat the input tokens in order (not just bag of words) The position can be either learned or fixed. It lets the model reason about the relative position of every tokens.

## LSH based Self-Attention

## Transformer

### Reformer improvement

To be able to run a Transformer on a large context vector (million tokens) we need to make it memory an compute efficient.
* __Reversible Layers__ are used to make the Transformer more memory efficient as we do not need to store the forward pass activations to compute the backward pass and gradients.
* __Locality Sensitive Hashing (LSH)__ is used to reduce the compute of the Dot Product attention for large input sizes.

## Resources

* [Attention is All you need](https://arxiv.org/pdf/1706.03762.pdf)
* [NLP from scratch: Translation with a Seq2Seq Network and Attention](https://pytorch.org/tutorials/intermediate/seq2seq_translation_tutorial.html#training-the-model)
* [Exploring the limits of Transfer Learning with a Unified Text to Text Transformer](https://arxiv.org/abs/1910.10683)
* [Reformer: The Efficient Transformer](https://arxiv.org/abs/2001.04451)
* [Deep contextualized word representations](https://arxiv.org/pdf/1802.05365.pdf)
* [The Illustrated Transformer](http://jalammar.github.io/illustrated-transformer/)
* [The Illustrated GPT-2](http://jalammar.github.io/illustrated-gpt2/)
* [BERT: Pre-training of Deep Bidirectional Transformers for Language Understanding](https://arxiv.org/abs/1810.04805)
* [How GPT3 Works - Visualizations and Animations](http://jalammar.github.io/how-gpt3-works-visualizations-animations/)
* [LSTM is dead. Long Live Transformers!](https://www.youtube.com/watch?v=S27pHKBEp30)
* [Attn: Illustrated Attention](https://towardsdatascience.com/attn-illustrated-attention-5ec4ad276ee3)
* [Transformers beyond NLP: OpenAI Jukebox](https://openai.com/blog/jukebox/)
