# Python numpy

* Filter
```
# 1. Using generators
np.fromiter((x for x in arr if x < k), dtype=arr.dtype)

# 2. Using boolean mask slicing
arr[arr < k]

# 3. Using np.where()
arr[np.where(arr < k)]
```

* Map
```
# Given that x, y are np.arrays
x * 2
x ** 2
x - y
```
