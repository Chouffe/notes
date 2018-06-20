# Stack

* Stack is a build tool
* Stack is not a package manager
* Curated package lists known to have no conflicts: `resolvers`
* Sandbox builds by default
* Reproducible builds

## hpack

* `package.yaml`
* Other way to generate cabal config files
  * Simplicity
  * Focus on Essentials
  * Less boilerplate

* Generate `.cabal` file with hpack
```
hpack
```

## Cabal

* Package and dependency manager
* Organize package with .cabal file
* Stack is a layer on top of cabal

## New project

* Creating new stack project
```
stack new project-name
```
* docker integration
```
# stack.yaml

docker:
  enable: true
```


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

* Force everything to be re-built, removes local cache
```
stack clean
```

## Execution

* Run specified executable from latest build
```
stack exec name-exe
```

* Run ghci
```
stack ghci
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

## stack ghc

* GHC will create a program if there is a module named `Main` and a `main` function. Otherwise use the `-is-main` ghc flag.
```
stack ghc -- -O2 src/Map.hs -main-is Map.main && src/Map
```

## Resources

* [Guide to Haskell in 2018](https://lexi-lambda.github.io/blog/2018/02/10/an-opinionated-guide-to-haskell-in-2018/)
* [Haskell Stack Mega-Tutorial](https://www.youtube.com/watch?v=sRonIB8ZStw)

