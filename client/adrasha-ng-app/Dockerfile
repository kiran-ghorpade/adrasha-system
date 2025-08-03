# Use nginx to serve static files
FROM nginx:alpine

# Copy built Angular files to nginx html folder
COPY ./dist/adrasha-ng-app/browser /usr/share/nginx/html

# Expose port 80
EXPOSE 80
