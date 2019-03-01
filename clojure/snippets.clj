# Snippets

* `wget` implementation

```clojure
(require '[clojure.java.io :as io])

(defn download!
  "Download `uri` and store it in `file`"
  [uri file]
  (with-open [in (io/input-stream uri)
              out (io/output-stream file)]
    (io/copy in out)))
```
