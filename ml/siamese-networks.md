# Siamese Networks

Siamese Networks are a type of network architecture that identify similarity between things. Such models learn a distance metric.
In NLP, they can be used to find the difference and similarity between different questions, search engine queries, hand written signatures.
In Computer vision, they are used to do face recognition where known images of people are precomputed and compared to a new image.

They solve the problem of not having to retrain a model everytime a new class is added. Eg. Face recognition. The main idea is to be able to make predictions based only on one sample: one shot learning.

## Architecture

Siamese Networks are made of two identical subnetworks sharing the same parameters that work in tandem to produce a vector representation of two inputs. Those two vectors are then compare with a cosine similarity function to capture the similarities and differences in a single score value (`-1 <= y__hat <= 1`)

## Loss function

One often uses Triplet Loss to train a Siamese Network. An anchor (A) is provided with a positive (P) and a negative example (N). one would then want to minimze the following value `diff = max(0, s(A, N) - s(A, P) + alpha)` where `s` is the cosine similarity function.
Eg.
* Anchor A: How old are you?
* Positive P: What is your age?
* Negative N: Where are you from?

The triplets can be selected at random to train the model. But it is more useful to select hard triplet for the model so that it can learn more. Hard triplets: `s(A,N) ~= s(A,P)`.

The vectors need to be L2 normalized to be on the unit hypersphere.

## Resources

* [FaceNet: A Unified Embedding for Face Recognition and Clustering](https://arxiv.org/abs/1503.03832)
* [One Shot learning, Siamese networks and Triplet Loss with Keras](https://medium.com/@crimy/one-shot-learning-siamese-networks-and-triplet-loss-with-keras-2885ed022352)
