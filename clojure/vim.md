# Clojure and Vim

## Piggieback

nREPL middleware that enables the use of a ClojureScript REPL on top of an nREPL session.

* [Piggieback repo](https://github.com/nrepl/piggieback)

## Parinfer

* [Parinfer Demo](https://shaunlebron.github.io/parinfer/)

## Commands

### Wrap Commands

* `<LocalLeader>i` and `<LocalLeader>I` wrap the current COMPOUND FORM with ( and ).
* `<LocalLeader>[` and `<LocalLeader>]` wrap the current COMPOUND FORM with [ and ].
* `<LocalLeader>{` and `<LocalLeader>}` wrap the current COMPOUND FORM with { and }.
* `<LocalLeader>W` and `<LocalLeader>w` wrap the current ELEMENT with ( and ).
* `<LocalLeader>e[` and `<LocalLeader>e]` wrap the current ELEMENT with [ and ].
* `<LocalLeader>e{` and `<LocalLeader>e}` wrap the current ELEMENT with { and }.

* `dsf`: splice (delete surroundings of form)
* `cse(/cse)/cseb`: surround element in parentheses
* `cse[/cse]`: surround element in brackets
* `cse{/cse}`: surround element in braces

### List Manipulation

* `<LocalLeader>@` splices the current COMPOUND FORM into its parent.
* `<LocalLeader>o` raises the current COMPOUND FORM to replace the enclosing COMPOUND FORM.
* `<LocalLeader>O` raises the current ELEMENT to replace the enclosing COMPOUND FORM.
* `<M-k>` and `<M-j>` swap the position of the current COMPOUND FORM with a sibling ELEMENT.
* `<M-h>` and `<M-l>` swap the position of the current ELEMENT with a sibling ELEMENT.
* `<M-S-j>` and `<M-S-k>` emit the terminal ELEMENTS of the current COMPOUND FORM.
* `<M-S-h>` and `<M-S-l>` capture adjacent ELEMENTS into the current COMPOUND FORM.

* `>f` and `<f` to move a form
* `>e` and `<e` to move an element
* `>)`, `<)`, `>(`, and `<(` for slurpage and barfage

### Cursor Insertion

* `<LocalLeader>h` inserts the cursor at the head of the current COMPOUND FORM
* `<LocalLeader>l` inserts the cursor at the tail of the current COMPOUND FORM

* `<I` and `>I` inserts at the beginning and ending of a form

### Text Object Selection

```clojure
(def price 500.0)

(defn enterprise-price
  []
  (* pr|ice 10))
```

* ie `cpie` will evaluate price and print out 10.0 at the bottom of the screen.
* af `cpaf` will evaluate `(* price 10)` and print out 5000.0 at the bottom of the screen
* aF `cpaF` will re-evaluate the defn and update the function definition
Of course, all these objects are compatible with the operators you already know like yank delete

## Resources

* [Clojure and Vim: An overview – It's very possible](https://juxt.pro/blog/posts/vim-1.html)
* [vim-sexp](https://github.com/guns/vim-sexp)
