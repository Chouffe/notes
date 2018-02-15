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

```python
import numpy as np

df['B'] = np.where(df['A'] < 3, 1, 0)
```

* No truncated text
```python
pd.set_option('display.max_colwidth', -1)
```

* Shuffle Dataframe
```python
df.sample(frac=1, random_state=0)
```
