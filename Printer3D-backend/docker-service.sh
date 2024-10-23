#!/bin/bash
# - PARAMETERS & CONSTANTS
COMMAND=$1
ENVIRONMENT=$2

WORKING_DIRECTORY="$(dirname "$0")"
DOCKER_DIRECTORY="${WORKING_DIRECTORY}/src/main/resources/docker"
DOCKER_COMPOSER_COMMAND="docker-compose --file src/test/scripts/printer3d-docker-${ENVIRONMENT}/docker-compose.yml"
# - S E T   J A V A.  V E R S I O N
setJavaVersion(){
  export JAVA_HOME="/Users/adam/Library/Java/JavaVirtualMachines/azul-11.0.23/Contents/Home"
}
# - G E N E R A T E   C O N T A I N E R
generateContainer() {
  cd "${WORKING_DIRECTORY}" || exit 1;
  rm -v "${DOCKER_DIRECTORY}"/*.jar
  export JAVA_HOME=/Users/adam/Library/Java/JavaVirtualMachines/azul-11.0.23/Contents/Home
  ./gradlew clean bootJar
  cp ./build/libs/*.jar "$DOCKER_DIRECTORY"
  cp ./.app-banner "$DOCKER_DIRECTORY"
  cp ./.app-banner ./build/libs/.app-banner
  cd "$DOCKER_DIRECTORY" || exit 1;
  mv -v printer3d-backend-*.jar "printer3d-backend-acceptance.jar"
  echo "${DOCKER_DIRECTORY}/Dockerfile"
  docker build -t dimensinfin/printer3d.backend .
}
# - S T A R T / S T O P
start() {
  cd "${WORKING_DIRECTORY}" || exit 1;
  RUN_COMMAND="${DOCKER_COMPOSER_COMMAND} ${DOCKER_PREFERENCES}"
  echo 'Running -> '$RUN_COMMAND
  $RUN_COMMAND up &
}
stop() {
  cd "${WORKING_DIRECTORY}" || exit 1;
  RUN_COMMAND="${DOCKER_COMPOSER_COMMAND}"
  $RUN_COMMAND down
}
recycle() {
  # - STOP
  cd "${WORKING_DIRECTORY}" || exit 1;
  RUN_COMMAND="${DOCKER_COMPOSER_COMMAND}"
  $RUN_COMMAND down
  generateContainer
  cd "${WORKING_DIRECTORY}" || exit 1;
  RUN_COMMAND="${DOCKER_COMPOSER_COMMAND}"
  $RUN_COMMAND up &
}

case $COMMAND in
'generate')
  setJavaVersion
  generateContainer
  ;;
'start')
  start
  ;;
'stop')
  stop
  ;;
'recycle')
  recycle
  ;;
*)
  echo "Usage: $0 { generate | start | stop | recycle } development|acceptance"
  echo
  exit 1
  ;;
esac
exit 0
