# Python pandas

* Derive new column from current using arbitrary function
```python
def f(string):
    ...
    return result

df['B'] = df['A'].apply(f)
```

* Derive new column from current using logical operators
```python
df['B'] = df.A < threshold
```
