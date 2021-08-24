# Clojure

## Style Guide

* [Clojure Style Guide](https://github.com/Chouffe/clojure-style-guide)
* [Clojure How to ns](https://stuartsierra.com/2016/clojure-how-to-ns.html)

## Development

### Vim Plugin

#### Vim s-exp

* [Repository](https://github.com/tpope/vim-sexp-mappings-for-regular-people)
* Move a form
```
>f
<f
```
* Move an element
```
>e
<e
```
* Slurpage and Burfage
```
>)
>(
<)
<(
```
* Insertion at the end/beginning of a form
```
<I
>I
```
* Splice: Delete surroundings of form
```
dsf
```
* Surround element in parantheses
```
cse(/cse)/cseb
```
* Surround element in brackets
```
cse[/cse]
```
* Surround element in braces
```
cse{/cse}
```

#### nvim Conjure

* Evaluate s-expression under cursor
```
,ee
```
* Evaluate s-expression at mark `F`
```
,emF
```

#### Vim Fireplace

* [Repository](https://github.com/tpope/vim-fireplace)
* Evaluate the s-expression under cursor
```
cpp
```
* Macro expansion
```
cm
```
* Lookup the doc of the symbol
```
K
```
* Source of the symbol
```
[d
```
* Jump to the definition of a symbol
```
[<C-D>
```
* Go to file
```
gf
```
* Evaluate the whole file
```
:%Eval
```
* Require a namespace with `:reload`
```
:Require
```

## Language

### Multimethods

* A dispatching function returns some value that is used to determine which method implementation to use
```
(ns were-creatures)

;; Definition
(defmulti full-moon-behavior
  (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :default
  [were-creature]
  (str "I am the default behavior"))

(defmethod full-moon-behavior :were-wolf
  [were-creature]
  (str "I bite!"))

;; Usage
(full-moon-behavior {:were-type :were-wolf}) ;; Dispatch to :wer-wold
(full-moon-behavior {:were-type :were-wolf-typo})  ;; Dispatch to :default
(full-moon-behavior {})  ;; Dispatch to :default
```
* Openly extensible by user of the library
```
(ns other-namespace
  (:require [were-creatures]))

(defmethod were-creatures/full-moon-behavior :chuck-norris
  [were-creature]
  (str "I am Chuck!"))
```
* Multimethods take arbitrary number of params
```
(defmulti foo (fn [bar baz] [(class bar) (class baz)]))
```

### Protocols

* Protocols are multimethods with one argument that dispatch on a type
* A protocol is a set of polymorphic operations
```
(defprotocol Psychodynamics
  "Plumbs the inner depths of your data types"
  (thoughts [x] "The DT's innermost thoughts")
  (feeling-abouts [x] [x y] "Feelings about self or other"))

(extend-type java.lang.String
  Psychodynamics
  (thoughts [x] (str x " thinks..."))
  (feeling-abouts
    ([x] (str x " is longing for a simpler way of life"))
    ;; y can be any type...
    ([x y] (str x " is envious of " y "'s simpler way of life"))))

(thoughts "hello")
(feeling-abouts "hello" 42)
```
* `extend-protocol` extends multiple  types at once
```
(extend-protocol Psychodynamics
  java.lang.String
  (thoughts [x] (str x " thinks..."))
  (feeling-abouts
    ([x] (str x " is longing for a simpler way of life"))
    ;; y can be any type...
    ([x y] (str x " is envious of " y "'s simpler way of life")))

  java.lang.Long
  (thoughts [x] (str x " thinks... about numbers"))
  (feeling-abouts
    ([x] (str x " meh"))
    ;; y can be any type...
    ([x y] (str x "blah " y "meh"))))

(thoughts 42)
(thoughts "John")
(feeling-abouts 42)
```
* Protocols methods belong to the namespace they are defined in
```
(ns my-namespace)

...

my-namespace/thoughts
my-namespace/feeling-abouts
```

### Records

* Maplike data types in which one specifies fields (slots for data) for records
* They are extendable to implement protocols
```
; Creating a record (same as creating a class/type)
; It also creates two implicit functions
; * ->WereWolf
; * map->WereWolf
(defrecord WereWolf [name title])

; Making an object - different ways
(->WereWolf "David" "Tourist")  ;; ->WereWolf is a function
(WereWolf. "Jacob" "Meat Technical Officer")  ;; Using class instanciation interop call
(map->WereWolf {:name "Lucian" :title "Wizard"})
```
* Using a record in another namespace is done via `import`
```
(ns other-namespace
  (:import [were_records WereWolf]))
```
* Access attributes
```
(def david (->WereWolf "David" "Tourist"))

(:name david)
(.name david)
(get david :name)
```
