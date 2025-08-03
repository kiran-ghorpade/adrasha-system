#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

echo "Stopping Docker Compose services..."
cd backend
docker compose down
cd ..
echo "Docker Compose services stopped."

echo "Stopping LAMPP..."
sudo /opt/lampp/lampp stop
echo "LAMPP stopped."

echo "All services stopped successfully!"

