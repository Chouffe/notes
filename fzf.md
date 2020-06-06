# FZF

* Live `awk` preview
```
 echo '' | fzf --print-query --preview 'echo "a\nb\nc\nd" | awk {q}'
```
* Live file exploration
```
echo '' | fzf --preview 'ls {q}'
```
* Live jq preview
```
echo '' | fzf --print-query --preview "cat *.json | jq {q}"
```

## Resources

* [FZF Live REPL](https://paweldu.dev/posts/fzf-live-repl/)
* [Github Repository](https://github.com/junegunn/fzf)
