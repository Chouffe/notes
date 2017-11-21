# Bash

## Tips and Tricks

* Loop through dates

```
d=2016-06-03
while [ "$d" != 2016-06-04 ]; do
  # Do something with date $d
  echo $d
  # Increment date $d
  d=$(date -I -d "$d + 1 day")
done
```
