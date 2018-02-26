# Stack

* Stack is a build tool
* Stack is not a package manager

## Resources

* [Guide to Haskell in 2018](https://lexi-lambda.github.io/blog/2018/02/10/an-opinionated-guide-to-haskell-in-2018/)

## Build

* Build a target
```
stack build <target>
```
* Never use `stack install`

* Build project without optimization, for development
```
stack build --fast
```
* Run tests along with the code
```
stack test --fast
```
```
stack build --fast --test
```
* Build documentation
```
stack test --fast --haddock-deps
```
* Rerun tests as code changes
```
stack test --fast --haddock-deps --file-watch
```

## Documentation

* Open local documentation
```
stack haddock --open lens
```
* Build the Hoogle Search Index
```
stack hoogle -- generate --local
```
* Start Hoogle local webserver
```
stack hoogle -- server --local --port=8080
```

## Configuring Project

* `stack.yaml`
* `project.cabal` OR `project.yaml` that generates the cabal file
* [Online Documentation](https://docs.haskellstack.org/en/stable/yaml_configuration/)

## Editor Integration

* Build ghc-mod, hlint, hoogle, ...
```
stack build ghc-mod hlint hoogle
```
