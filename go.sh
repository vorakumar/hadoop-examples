#!/bin/bash
set -e -u

function usage {
  echo "usage: ./go <command>"
  echo "  setup           setup docker container"
  echo "  run-example     copy jar to container, and execute"
  echo
  exit -1
}

function build-artifact {
  mvn package
}

function copy-artifact {
  
}

case "${1:-}" in
'')
  usage
	;;
setup)
  gulp clean
  gulp watch
  ;;
run-word-count)
  build-artifact
  copy-artifact
  word-count-example
  ;;
*)
  echo "unrecognized command: $1"
	;;
esac