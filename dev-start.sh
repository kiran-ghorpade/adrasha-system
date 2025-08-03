#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

echo "Starting LAMPP..."
sudo /opt/lampp/lampp start
echo "LAMPP Started..."

echo "Starting Docker Compose services..."
cd backend
# docker compose up -d
cd ..
# gnome-terminal -- bash -c "docker stats"

echo "Starting Angular app..."
cd client/adrasha-ng-app
ng serve --open &

echo "All services started!"

