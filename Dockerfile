FROM maven:3.6-jdk-11

RUN mkdir /home/eval/
RUN mkdir /home/eval/result
RUN mkdir /home/eval/data
RUN touch /home/eval/result/output
RUN apt update
RUN apt install wget -y

RUN mkdir /home/eval/src/
ADD pom.xml /home/eval/
ADD src/ /home/eval/src/

WORKDIR /home/eval
RUN mvn clean install

ENTRYPOINT java -jar target/TripleHashEvaluator-hashing.jar data/${DATA} > result/output
CMD []
