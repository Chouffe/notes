# Python sklearn

## Metrics

* Precision-Recall curve
```python
from sklearn.metrics import precision_recall_curve

precision, recall = precision_recall_curve(y_test, y_pred)
```

* ROC Curve
```python
from sklearn.metrics import roc_curve, roc_auc_score

fpr, tpr, thresholds = roc_curve(y_test, y_pred, pos_label=1)
auc_score = roc_auc_score(y_test, y_score)
```
