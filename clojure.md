# Clojure

## Style Guide

* [Clojure Style Guide](https://github.com/Chouffe/clojure-style-guide)

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
