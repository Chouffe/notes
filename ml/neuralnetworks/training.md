# Training Neural Networks

Neural net training fails silently as opposed to regular software development. A fast and furious approach to training NN does not work.
1. Become one with the data: Go through hundreds of examples, understand the distributions and patterns and watch for data imbalances and biasesm, plot the outliers. After this step, one should be able to answer the following questions:
  * Can we only rely on local features or do we need global context?
  * How much variation is there?
  * What variation is spurious and can be preprocessed out?
  * How much downsampling can we afford?
  * How noisy are the labels?
2. Set up the end-to-end training/evaluation skeleton + get dumb baselines: the goal is to gain trust in the correctness via a series of experiments
  * **fix the random seed**.
  * **simplify**. Eg Turn off data augmentation
  * **verify loss at init**.
  * **init well**. Initialize the final layer weights correctly.
  * **human baseline**. Monitor metrics other than loss that are human interpretable and checkable.
  * **input-independant baseline**. Eg set all your inputs to zero or to random values
  * **overfit one batch**. verify that one can reach the lowest achievable loss. Visualize the label and the prediction and ensure that they align perfectly.
  * **verify decreasing training loss**. at this stage, one should be underfitting on the dataset because it is a toy model. Increase the capacity a bit and look at the training loss go down.
  * **visualize just before the net**. What goes exactly into your network. This is the only source of truth.
  * **visualize prediction dynamics**. on a fixed test batch during the course of training. It gives very good intuition for how training progresses.
  * **generalize a special case**. write a very specific function first (using loops if necessary) and then generalize when it is working.
3. Overfit: get a model large enough that it can overfit (focus on the training loss) and then regularize it.
  * **picking the model**. Don't be a hero. If you do CV, just use a ResNet50.
  * **complexity only one at a time**.
  * **do not trust learning rate decay defaults**. tune this all way at the very end.
4. Regularize: gain validation accuracy by giving up some of the training accuracy.
  * **get more data**.
  * **data augment**.
  * **creative augmentation**.
  * **pretrain**.
  * **stick with supervised learning**.
  * **smaller input dimensionality**.
  * **decrease the batch size**.
  * **drop**. Add dropout.
  * **weight decay**. Increase the weight decay penalty.
  * **early stopping**. Stop training based on your measured validation loss.
  * **try a larger model**. early stopped version of larger models can be much better that of smaller models.
5. Tune
  * **random over grid search**.
  * **hyper parameter optimization**.
6. Squeeze out the juice
  * **ensembles**. A pretty much guaranteed 2% accurcay gain.
  * **leave it training**.


## Resources

- [Andrej Karpathy blog - A Recipe for Training Neural Networks](http://karpathy.github.io/2019/04/25/recipe/)
