# GHCI

Interactive shell for the GHC compiler.

## Commands

| Command   | Shortcut | Action                                             |
|-----------|----------|----------------------------------------------------|
| `:reload` | `:r`     | Code reload                                        |
| `:type`   | `:t`     | Type inspection                                    |
| `:kind`   | `:k`     | Kind inspection                                    |
| `:info`   | `:i`     | Information                                        |
| `:print`  | `:p`     | Print the expression                               |
| `:edit`   | `:e`     | Load file in system editor                         |
| `:load`   | `:l`     | Set the active Main module in the REPL             |
| `:add`    | `:ad`    | Load a file into the REPL namespace                |
| `:browse` | `:bro`   | Browse all available symbols in the REPL namespace |

* View module-level bindings and types
```
:show bindings
```
* Examining module-level imports
```
:show imports
```
* See compiler level flags
```
:set
```
```
:showi language
```
* Change prompt
```
:set prompt "> "
```
* run main function with arguments from ghci
```
:main arg1 arg2
```

## Language Extensions

|      | Function                                                |
|------|---------------------------------------------------------|
| `+t` | Show types of evaluated expressions                     |
| `+s` | Show timing and memory usage                            |
| `+m` | Enable multi-line expression delimited by `:{` and `:}` |

## Resources

* [WIWIKWLH](http://dev.stephendiehl.com/hask/)
