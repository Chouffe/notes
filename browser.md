# Browser

## IndexedDB

* [IndexedDB Documentation](https://developer.mozilla.org/en-US/docs/Web/API/IndexedDB_API/Basic_Concepts_Behind_IndexedDB)
* [IndexedDB summary](https://javascript.info/indexeddb)
* [IDB Transaction explicit commit proposal](https://andreas-butler.github.io/idb-transaction-commit/EXPLAINER.html)
* [Transaction Deactivation Chromium checks](https://chromium.googlesource.com/external/w3c/web-platform-tests/+/refs/heads/master/IndexedDB/transaction-deactivation-timing.html)

## Micro / Macro tasks

* [Microtasks](https://javascript.info/microtask-queue)

## Webworkers - Dedicated Workers

They make it possible to run a script in a background thread separate from the main execution thread. It allows the UI to run without being slowed down.

Almost any code can be run inside a worker thread but DOM manipulataion functions, or default methods from the `window` object.

### Overhead

#### Instantitation

Time to instantiate: 40ms
Instantiating web workers is pretty efficient but one should try to create as few web workers and reuse them.

#### Message latency

`postMessage` is fast and takes less than 1ms to run.
Transfer speed is about 80kB/ms for `postMessage`.

### Communication

Data is sent between the worker and the main thread via a system of messages using the `postMessage()` method, and can respond to messages using the `onmessage` event handler.
Data is copied rather than shared

### Resources

* [Web Workers API](https://developer.mozilla.org/en-US/docs/Web/API/Web_Workers_API)
* [How fast are web workers](https://hacks.mozilla.org/2015/07/how-fast-are-web-workers/)
* [The State of Web Workers in 2021](https://www.smashingmagazine.com/2021/06/web-workers-2021/)
