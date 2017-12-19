# Bash

## Tips and Tricks

* Loop through dates

```bash
d=2016-06-03
while [ "$d" != 2016-06-04 ]; do
  # Do something with date $d
  echo $d
  # Increment date $d
  d=$(date -I -d "$d + 1 day")
done
```

## mv

* Move 1000 files from one directory to another
```bash
 mv -- *([1, 1000]) /src/directory/
 ```
* Move 1000 files from one directory to another with a given prefix
```bash
 mv -- prefix*([1, 1000]) /src/directory/
```
