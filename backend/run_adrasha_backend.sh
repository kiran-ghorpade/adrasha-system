#!/bin/bash

# === CONFIGURATION ===
JARS=(
  "/home/cybernaut/Workspace/projects/ADRASHA/backend/auth-service/target/auth-service-0.0.1-SNAPSHOT.jar"
  "/home/cybernaut/Workspace/projects/ADRASHA/backend/user-service/target/user-service-0.0.1-SNAPSHOT.jar"
  "/home/cybernaut/Workspace/projects/ADRASHA/backend/data-service/target/adrasha-data-service-0.0.1-SNAPSHOT.jar"
  "/home/cybernaut/Workspace/projects/ADRASHA/backend/analytics-service/target/adrasha-analytics-service-0.0.1-SNAPSHOT.jar"
  "/home/cybernaut/Workspace/projects/ADRASHA/backend/masterdata-service/target/masterdata-service-0.0.1-SNAPSHOT.jar"
)

LOG_DIR="./logs"
PID_FILE="./adrasha-services.pids"

mkdir -p "$LOG_DIR"

start_services() {
  echo "Starting ADRASHA services..."
  > "$PID_FILE"
  for JAR in "${JARS[@]}"; do
    SERVICE_NAME=$(basename "$JAR" .jar)
    LOG_FILE="$LOG_DIR/$SERVICE_NAME.log"

    echo "Starting $SERVICE_NAME..."
    nohup java -jar "$JAR" > "$LOG_FILE" 2>&1 &

    echo $! >> "$PID_FILE"
  done
  echo "All services started. PIDs saved to $PID_FILE"
}

stop_services() {
  echo "Stopping ADRASHA services..."
  if [[ -f "$PID_FILE" ]]; then
    while read -r PID; do
      if ps -p "$PID" > /dev/null; then
        echo "Stopping PID $PID"
        kill "$PID"
      fi
    done < "$PID_FILE"
    rm "$PID_FILE"
    echo "All services stopped."
  else
    echo "No PID file found. Are services running?"
  fi
}

case "$1" in
  start)
    start_services
    ;;
  stop)
    stop_services
    ;;
  *)
    echo "Usage: $0 {start|stop}"
    ;;
esac

