#!/bin/bash
# - PARAMETERS & CONSTANTS
# $1 - the command to execute
COMMAND=$1

SERVICE_PORT=5111
ADMIN_PORT=$(($SERVICE_PORT + 1000))
SIMULATION_NAME='printer3d-backend-simulation'

WORKING_DIRECTORY="."
APISIMULATOR_COMMAND="${HOME}/.apisimulator/apisimulator-http-1.12/bin/apisimulator"
export APISIMULATOR_LOG_LEVEL=debug
export APISIMULATOR_JAVA='/Users/adam/Library/Java/JavaVirtualMachines/azul-11.0.23/Contents/Home'

# - S T A R T
start() {
  cd ${WORKING_DIRECTORY}
  APISIMULATOR_SIMULATION="${WORKING_DIRECTORY}/support/$SIMULATION_NAME"
  echo ">>> Starting api simulator with: $APISIMULATOR_SIMULATION"
  echo ">>> Service port: $SERVICE_PORT"
  echo ">>> Administration port: $ADMIN_PORT"
  echo ">>> Simulation: $SIMULATION_NAME"
  $APISIMULATOR_COMMAND start $APISIMULATOR_SIMULATION -p ${SERVICE_PORT} -admin_port ${ADMIN_PORT} &
}
# - S T O P
stop() {
  cd ${WORKING_DIRECTORY}
  echo "Stopping api simulator..."
  APISIMULATOR_SIMULATION="${WORKING_DIRECTORY}/support/$SIMULATION_NAME"
  $APISIMULATOR_COMMAND stop "support/$SIMULATION_NAME" -admin_port ${ADMIN_PORT}
}

case $COMMAND in
'start')
  start
  ;;
'stop')
  stop
  ;;
*)
  echo "Usage: $0 { start | stop }"
  echo
  exit 1
  ;;
esac
exit 0
