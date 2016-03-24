#!/bin/bash
set -e -u

function usage {
  echo "usage: ./go <command>"
  echo "  setup           setup docker container"
  echo "  word-count      copy jar to container, and execute"
  echo
  exit -1
}

function start-container {
  docker-machine start hadoop
  eval $(docker-machine env hadoop)
  docker run -it sequenceiq/hadoop-docker:2.7.0 /etc/bootstrap.sh -bash
}

function build-artifact {
  mvn package
}

function copy-artifact {
  echo ""
}

case "${1:-}" in
'')
  usage
	;;
setup)
  ;;
build)
  build-artifact
  ;;
word-count)
  build-artifact
  copy-to-docker
  run-word-count
  ;;
*)
  echo "unrecognized command: $1"
	;;
esac