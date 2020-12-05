# ReLU

Rectified linear unit `f(x) = max(0,x)`

## Why ReLU works better than other activation functions (sigmoid, tanh, ...)

* Allows neuron to express a strong opinion (it does not need to be a very small interval like tanh or sigmoid)
* No saturation for Gradient Descent
* Less sensitive to random initialization
* Stupidly easy to compute + gradient compute
* Runs great on low precision hardware

## Downsides

* Dead neurons (always output zero) - but can be fixed with leaky ReLUs `f(x, alpha) = max(alpha.x, x) with alpha small (0.01 for instance`
* Gradient discontinuous at origin
