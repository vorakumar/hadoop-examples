#!/bin/bash -x

JAR_FILE=/hadoop.examples-1.0-SNAPSHOT-jar-with-dependencies.jar

function setup {
  cd /
  rm -rf /tmp/dataset
  unzip dataset.zip -d /tmp
  cd $HADOOP_PREFIX
  bin/hadoop fs -rm -f -r -skipTrash dataset
  bin/hadoop fs -put /tmp/dataset dataset
}

function run-word-count {
    bin/hadoop jar $JAR_FILE examples.hadoop.mapreduce.simple.WordCount dataset/wordcount
    rm -f /wordcount-output
    bin/hadoop fs -get dataset/wordcount/output/part-r-00000 /wordcount-output
}

function run-amount-by-city {
    bin/hadoop jar $JAR_FILE examples.hadoop.mapreduce.join.MapReduceDriver dataset/amount-by-city
    rm -f /amount-by-city-output
    bin/hadoop fs -get dataset/amount-by-city/output/part-r-00000 /amount-by-city-output
}

case "${1:-}" in
word-count)
  setup
  run-word-count
  ;;
amount-by-city)
  setup
  run-amount-by-city
  ;;
*)
  echo "unrecognized command: $1"
	;;
esac