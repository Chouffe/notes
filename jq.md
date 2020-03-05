# jq

## Commands

* input an HTML as a JSON value
```
echo '{"foo": "XXX"}' | jq --arg foo "$(cat index.html)" '.foo = $foo'
```
