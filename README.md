# HashingEval
Mainly evaluation of hashing in Java and Apache Jena, but can be used to evaluate anything.

## Build
Build a container from the Docker image.

```
docker build -t eval .
```

## Run
Run the container with volumes to dataset.

```
docker run --rm -v ${PWD}/result/:/home/eval/result/ -v <DATASET-PATH>:/home/eval/data/ -e DATA='<DATASET>' -d eval
```

```<DATASET-PATH>``` is the path to the input dataset and ```<DATASET>``` is the name of the dataset file.
