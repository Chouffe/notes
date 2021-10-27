# MLflow

MLflow’s core philosophy is to put as few constraints as possible on your workflow: it is designed to work with any machine learning library, determine most things about your code by convention, and require minimal changes to integrate into an existing codebase. At the same time, MLflow aims to take any codebase written in its format and make it reproducible and reusable by multiple data scientists.

MLflow provides four components to help manage the ML workflow:

* **MLflow Tracking**: API and UI for logging model parameters, code versions, metrics and model artifacts when running ML code.
* **MLflow Projects**: A standard format for packaging reusable data science code.
* **MLfow Models**: A convention for packaging ML models in multiple flavors and tools to deploy them. They can be deployed as docker based REST servers to AWS Sagemaker
* **MLflow Registry**: A centralized model store to collaboratively manage the full lifecycle of an MLflow Model. Including model lineage, model versioning, stage transitions and annotations.

## Resources

* [Official Tutorials and Examples](https://www.mlflow.org/docs/latest/tutorials-and-examples/tutorial.html)
* [MLflow Tracking official documentation](https://www.mlflow.org/docs/latest/tracking.html#mlflow-tracking)
* [MLflow Concepts](https://www.mlflow.org/docs/latest/concepts.html)
