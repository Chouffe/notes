# Password Store

## Commands
* `ls [subfolder]`: list names of passwords inside the tree at subfolder
* `find [pass-names]`: list name of passwords inside the tree that match pass-names
* `show [--clip, -c] pass-name`: Decrypt and Print a password named pass-name
* `insert [--multiline, -m] pass-name`: Insert a new password into the password store called pass-name
* `edit pas-name`: Insert a new password or edit an existing password using the default text editor
* `mv/cp/rm`

## Examples

* Copy to clipboard
```
pass -c Email/abc@gmail.com`
```
* Add password to store
```
pass insert Business/cheese`
```
* Add multiline password to store
```
pass insert -m Business/cheese
```
* Generate new password - Erase first line of file
```
pass generate Email/abc@gmail
```
* Generate new password with length
```
pass generate Email/abc@gmail 25
```
* grep the password store
```
pass grep accountId
```
* list files
```
pass ls internet/
```
* find a file by name
```
pass find filename
```
* Reencrypting the files in the pass store
```
pass init <NEW-GPG-KEY-ID>
```

## Utils

* [Utils](https://gist.github.com/Chouffe/973328010ad8adaff0bd839ed488b1fe) to query password store from CLI

## Resources

* https://andythemoron.com/blog/2017-08-01/Pass-For-Password-Management
