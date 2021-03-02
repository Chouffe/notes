# Python pandas

### Importing

* Build sample data frame
```python
df = pd.DataFrame([[1,'Bob', 'Builder'],
                  [2,'Sally', 'Baker'],
                  [3,'Scott', 'Candle Stick Maker']],
columns=['id','name', 'occupation'])
```

### Viewing and Inspecting

* Get dataframe info
```python
df.info()
```
* Get statistics
```python
df.describe()
```
* Get 10 quantiles statistics
```
df.describe(percentiles=np.arange(0, 1, 0.1))
```
* Check value distributions
```python
df['c'].value_counts()
df['c'].value_counts(normalize=True) # To check for frequency instead of raw counts
df['c'].value_counts(dropna=False)   # To include the missing values in the counts
```
* Count unique rows
```
len(ratings['user_id'].unique())
```
* Get a list of column values
```python
df.columns.tolist()
```

### Combining

* Concatenate dataframes ~ SQL UNION
```python
pd.concat([df1, df2], ignore_index=True)
```
* Merge dataframes ~ SQL LEFT JOIN
```python
df1.merge(df2, left_on='user_id', right_on='user_id', suffixes=('_left', '_right'))
```

### Transforming

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
df['B'] = np.where(df['A'] < 3, 1, 0)
```
* Split a column in two columns
```python
df['first_name'], df['last_name'] = df['name'].str.split(' ', 1).str
```
* Convert column values with a mapping
```python
df["category"] = df["category"].replace({0: 'cat', 1: 'dog'})
```
* Convert column values with a mapping
```python
level_map = {1: 'high', 2: 'medium', 3: 'low'}
df['c_level'] = df['c'].map(level_map)
```
* Rename column names
```
df.rename(columns={'foo bar': 'foo_bar', 'qu ux': 'quux'}

# Renaming in Batch
renamed_labels = [label.replace(' ', '_') for label in df.columns]
df.columns = renamed_labels
```

## Filtering

* Create a new dataframe from a subset of columns
```python
df[['user_id', 'name', rating']]
```
* Drop specified columns
```python
df.drop(['user_id', 'name'], axis=1)
```
* Retrieve rows by numbered index values
```python
df.iloc[0:3]
```
* Retrieve rows where a column's value is in a given list
```python
df[df['type'].isin(['TV', 'Movie'])
```
* Filter by value
```python
df[df['rating'] > 8]
```
* Filter by ranges (between)
```
df[(df.price >= 2) & (df.price <= 4)]
df[df.price.between(2, 4)]
```

### Sorting

* Sort a dataframe by values in a column
```python
df.sort_values('rating', ascending=False)
```

### Aggregating

* Count number of records for each disting value in a column
```python
df.groupby('type').count()
```
* Aggregate columns in different ways
```python
df.groupby(["type"]).agg({
  "rating": "sum",
  "episodes": "count",
  "name": "last"
}).reset_index() # needed otherwise the type column becomes the index column
```

### Cleaning

* Set NaN cells to some values
```python
df.fillna(0)
```

### Misc

* Sample a dataframe
```python
df.sample(frac=0.25)
```
* Shuffle a dataframe
```python
df.sample(frac=1, random_state=0)
df.sample(n=10, random_state=0).reset_index(drop=True)
```
* Iterate over indices and rows
```python
for idx, row in df.iterrows():
    pass
```
* No truncated text
```python
pd.set_option('display.max_colwidth', -1)
```

## Resources

* [Pandas mask](https://pythonhealthcare.org/2018/04/07/30-using-masks-to-filter-data-and-perform-search-and-replace-in-numpy-and-pandas/)
* [Filtering Data in Pandas](https://levelup.gitconnected.com/filtering-data-in-pandas-c7b60d1e1301)
* [My Python Pandas Cheat Sheet](https://towardsdatascience.com/my-python-pandas-cheat-sheet-746b11e44368)
* [Did you know Pandas can do so much](https://medium.com/fintechexplained/did-you-know-pandas-can-do-so-much-f65dc7db3051)
