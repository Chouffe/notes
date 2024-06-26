# Python sklearn

## Data Preparation

* Split into train/dev/test sets:

```python
from sklearn.model_selection import train_test_split

def train_dev_test_split(data, targets, train_size=0.8, test_size=0.5, random_state=0):
    '''
    Splits data and target in train/dev/test set
    '''
    train_x, valid_test_x, train_y, valid_test_y = train_test_split(data,
                                                                    targets,
                                                                    test_size=(1.0
                                                                    -
                                                                    train_size),
                                                                    random_state=random_state)
    valid_x, test_x, valid_y, test_y = train_test_split(valid_test_x,
                                                        valid_test_y,
                                                        test_size=0.5,
                                                        random_state=random_state)

    return train_x, valid_x, test_x, train_y, valid_y, test_y
```

## Metrics

* Precision-Recall curve:

```python
from sklearn.metrics import precision_recall_curve

precision, recall = precision_recall_curve(y_test, y_pred)
```

* ROC Curve:

```python
from sklearn.metrics import roc_curve, roc_auc_score

fpr, tpr, thresholds = roc_curve(y_test, y_pred, pos_label=1)
auc_score = roc_auc_score(y_test, y_score)
```
