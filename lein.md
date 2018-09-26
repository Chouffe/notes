# Leiningen

## Connect Vim Fireplace to cljs repl

* Start a figwheel project
```
lein new figwheel <appname>
```
* Start Figwheel
```
lein repl
(use 'figwheel-sidecar.repl-api)
(start-figwheel!)
```
* Piggieback on Clojurescript from Vim
```
:Piggieback (figwheel-sidecar.repl-api/repl-env)
```

## Resources

* [lein figwheel](https://github.com/bhauman/lein-figwheel)
* [Connect vim fireplace to cljs repl](https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-with-Vim)
