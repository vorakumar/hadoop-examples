#!/bin/bash

function usage {
  echo "usage: ./go <command>"
  echo "  setup           setup docker container"
  echo "  build-artifact  mvn package"
  echo "  word-count      run word-count example in container"
  echo "  amount-by-city  run amount-by-city example in container"
  echo
  exit -1
}

function load-docker-env {
  eval $(docker-machine env hadoop)
}

function setup {
  echo "Creating VM..."
  docker-machine create -d virtualbox hadoop
  echo "Starting VM..."
  docker-machine start hadoop
  load-docker-env
  echo "Fetching Docker image..."
  docker pull sequenceiq/hadoop-docker:2.7.0
  echo "Building Maven project..."
  mvn clean compile
  echo "Running container..."
  docker run --name 'hadoop-docker' -it sequenceiq/hadoop-docker:2.7.0 /etc/bootstrap.sh -bash
}

function build-artifact {
  mvn package
}

function copy-to-docker {
  load-docker-env
  docker cp target/hadoop.examples-1.0-SNAPSHOT-jar-with-dependencies.jar hadoop-docker:/
  rm -f /tmp/dataset.zip
  zip -r /tmp/dataset.zip dataset
  docker cp /tmp/dataset.zip hadoop-docker:/
  docker cp run-in-container.sh hadoop-docker:/
}

function run-in-container {
  load-docker-env
  CMD=$1
  docker exec hadoop-docker /run-in-container.sh $CMD
}

function kill-old-container {
  docker ps -a | grep 'hadoop-docker' | awk '{print $1}' | xargs docker rm
}

case "${1:-}" in
'')
  usage
	;;
setup)
  setup
  ;;
build)
  build-artifact
  ;;
word-count)
  build-artifact
  copy-to-docker
  run-in-container word-count
  ;;
amount-by-city)
  build-artifact
  copy-to-docker
  run-in-container amount-by-city
  ;;
*)
  echo "unrecognized command: $1"
  usage
	;;
esac