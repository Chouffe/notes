# Tensorflowjs

## WebGL backend

The currently most powerful backend for the browser. It runs 100x faster than the plain JS backend.
* Avoid blocking the UI thread: use `x.data()` and `x.array()` over `x.dataSync()` and `x.arraySync()`
* Explicit memory management is required
```js
// destroy a tensor with dispose()
const a = tf.tensor([[1, 2], [3, 4]]);
a.dispose();

// clean up all tensors that are not returned by a function after executing with tidy()
const a = tf.tensor([[1, 2], [3, 4]]);
const y = tf.tidy(() => {
  const result = a.square().log().neg();
  return result;
});
```
* Precision: on mobile devices, only 16bit floating point textures are supported
* Compilation of shaders happen on the CPU on the main thread and can be slow - this is why the first inference is often slow. Waermup may be necessary if one cares about the latency of the first inference.
```
const model = await tf.loadLayersModel(modelUrl);

// Warmup the model before using real data.
const warmupResult = model.predict(tf.zeros(inputShape));
warmupResult.dataSync();
warmupResult.dispose();

// The second predict() will be much faster
const result = model.predict(userData);
```

* [Resource](https://www.tensorflow.org/js/guide/platform_environment#webgl_backend)

