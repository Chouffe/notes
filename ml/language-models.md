# Language Models

## Evaluation

1. Extrinsic evaluation: This involves evaluating the language model by using it in an actual NLP task and measure the final loss/accuracy. End to end evaluation.
2. Intrinsic evaluation: This involves evaluating the language model itself, not taking into account the specific tasks it will be used for. Perplexity is an intrinsic evaluation method.

**Perplexity**
```
$$P(W) = \sqrt[N]{\prod_{i=1}^{N} \frac{1}{P(w_i| w_1,...,w_{n-1})}}$$
```

In practice, one uses the log of the formula for numerical stability.

## BERT

## Resources

* [Richer Sentence Embeddings using BERT](https://medium.com/genei-technology/richer-sentence-embeddings-using-sentence-bert-part-i-ce1d9e0b1343)
