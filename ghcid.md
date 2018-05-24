# ghcid

* One of the most important tool for Haskell Dev Environments
* One can use it along any editors without integrating it

* Fake type-of-expression support: just add `:: ()` to the type you want to check
* Reload your web app on every edit
```
ghcid \
    --command "stack ghci" \
    --test "DevelMain.update"
# It calls DevelMain.update function on every successful compile
```
* Run your tests suite on every edit
```
ghcid \
    --command "stack ghci cardano-sl-wallet-new:lib cardano-sl-wallet-new:test:wallet-new-specs --ghci-options=-fobject-code" \
    --test "main"
# Specify which package targets to load
# Specify the test-suite to run
```


## Commands

* For a TH heavy project
```
ghci --command "stack ghci --ghci-options=-fobject-code"
```
* Pick a single executable at target
```
ghci --command "stack ghci package:exe:main-node"
```
* Defer type errors
```
ghci --command "stack ghci ghci-options=-fdefer-type-errors"
```

## Resources

* [ghcid for the win!](http://www.parsonsmatt.org/2018/05/19/ghcid_for_the_win.html)
* [Auto-reload threepenny-gui apps during development](https://binarin.ru/post/auto-reload-threepenny-gui/)
