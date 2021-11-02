docker build -t eval .
docker run --rm -v ${PWD}/result/:/home/eval/result/ -v /srv/data/mpch/benchmark-leapfrog/benchmark/:/home/eval/data/ -d eval
