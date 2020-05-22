#!/bin/bash
# - PARAMETERS & CONSTANTS
SERVICE=$1
COMMAND=$2

WORKING_DIRECTORY="$(dirname "$0")"
DOCKER_DIRECTORY="${WORKING_DIRECTORY}/src/main/resources/docker"
DOCKER_COMMAND="docker-compose --file src/test/resources/docker/docker-compose-"

# - G E N E R A T E   C O N T A I N E R
generateContainer() {
  cd "${WORKING_DIRECTORY}" || exit 1;
  rm -v "${DOCKER_DIRECTORY}"/*.jar
  ./gradlew clean bootJar
  cp ./build/libs/*.jar "$DOCKER_DIRECTORY"
  cd "$DOCKER_DIRECTORY" || exit 1;
  docker build -t dimensinfin/printer3d.backend .
}
# - S T A R T / S T O P
start() {
  cd "${WORKING_DIRECTORY}" || exit 1;
  RUN_COMMAND="${DOCKER_COMMAND}${SERVICE}.yml"
  $RUN_COMMAND up &
}
stop() {
  cd "${WORKING_DIRECTORY}" || exit 1;
  RUN_COMMAND="${DOCKER_COMMAND}${SERVICE}.yml"
  $RUN_COMMAND down &
}

case $COMMAND in
'generate')
  generateContainer
  ;;
'start')
  start
  ;;
'stop')
  stop
  ;;
*)
  echo "Usage: $0 { <service> | backend } { generate | start | stop }"
  echo
  exit 1
  ;;
esac
exit 0
