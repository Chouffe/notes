# clj CLI

## Clojure Tools

Group of convenience tools:
- `Clojure CLI` - clj, a bash script
- `tools.build` - a clojure library (commonly referred to as deps)
- `deps.edn` - an edn file with a specific shape

### clj

Run clojure programs against a classpath.
It actually calls `clojure` and wrapping it with `rlwrap`.

### tools.deps

- Reads in dependencies from a `deps.edn` file
- Resolves the dependencies and their transitive dependencies
- Builds a classpath

### deps.edn

It is just an edn file to configure your project and dependencies.

```clj
{:deps    {...}
 :paths   [...]
 :aliases {...}}
```

### tools.build

clojure library with functions for building clojure projects. For instance a jar or uberjar.

Write a separate program inside your app which builds your app.

convention: `build.clj` in the root of the project that imports `tools.build`

Types of Clojure programs:

- A tool
- A library
- An app

When running the `build.clj`, one will use the `-T` parameter.

```sh
# uber is the name of the function inside build.clj that produces the jar or uberjar
clj -T:build uber
```

## Commands

Run a clojure program:

```sh
clj -M -m your-clojure-program
```

Use an entry function `run` that can be executed by clj using `-X`:

```clj
; src/hello.clj file
(ns hello)

(defn run [opts] 
  (println "Hello World"))
```

```sh
clj -X hello/run
```

Run an alias:

```clj
{:deps ...
 :aliases [:test {...}]}
```

```sh
clj -A:test
```

Observe the modified classpath:

```sh
clj -A:test -Spath
```

Add an optional dependency:

```clj
{:aliases
  {:bench {:extra-deps {criterium/criterium {:mvn/version "0.4.4"}}}}}
```

```sh
clj -A:bench
```

List all dependencies:

```sh
clj -X:deps tree
```

Find version of a specific library:

```sh
clj -X:deps find-versions :lib clojure.java-time/clojure.java-time
```

## Resources

- [Clojure Guide deps and CLI](https://clojure.org/guides/deps_and_cli)
- [What are the Clojure Tools?](https://betweentwoparens.com/blog/what-are-the-clojure-tools/)
