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

* Rename column names
```
df.rename(columns={'foo bar': 'foo_bar', 'qu ux': 'quux'}

# Renaming in Batch
renamed_labels = [label.replace(' ', '_') for label in df.columns]
df.columns = renamed_labels
```
* Split a column in two columns
```
df['first_name'], df['last_name'] = df['name'].str.split(' ', 1).str
```
* Convert column values with a mapping
```
df["category"] = df["category"].replace({0: 'cat', 1: 'dog'})
```
* Convert column values with a mapping
```
level_map = {1: 'high', 2: 'medium', 3: 'low'}
df['c_level'] = df['c'].map(level_map)
```
* Check value distributions
```
df['c'].value_counts()
df['c'].value_counts(normalize=True) # To check for frequency instead of raw counts
df['c'].value_counts(dropna=False)   # To include the missing values in the counts
```

## Resources

* [Pandas mask](https://pythonhealthcare.org/2018/04/07/30-using-masks-to-filter-data-and-perform-search-and-replace-in-numpy-and-pandas/)
