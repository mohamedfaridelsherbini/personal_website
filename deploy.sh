#!/bin/bash

# DigitalOcean Deployment Script for Personal Website
# This script builds and deploys the Kotlin/Ktor application to DigitalOcean

set -e

# Configuration
DROPLET_IP="YOUR_DROPLET_IP_HERE"
DROPLET_USER="root"
APP_NAME="personal-website"
CONTAINER_NAME="personal-website-container"
IMAGE_NAME="personal-website:latest"

echo "🚀 Starting deployment to DigitalOcean..."

# Build Docker image locally
echo "📦 Building Docker image..."
docker build -t $IMAGE_NAME .

# Save image to tar file
echo "💾 Saving Docker image..."
docker save $IMAGE_NAME > ${APP_NAME}.tar

# Copy image to DigitalOcean droplet
echo "📤 Uploading image to DigitalOcean..."
scp ${APP_NAME}.tar ${DROPLET_USER}@${DROPLET_IP}:/tmp/

# Deploy on DigitalOcean droplet
echo "🔄 Deploying on DigitalOcean..."
ssh ${DROPLET_USER}@${DROPLET_IP} << 'EOF'
    # Load Docker image
    docker load < /tmp/personal-website.tar
    
    # Stop and remove existing container if it exists
    docker stop personal-website-container 2>/dev/null || true
    docker rm personal-website-container 2>/dev/null || true
    
    # Run new container
    docker run -d \
        --name personal-website-container \
        --restart unless-stopped \
        -p 80:8080 \
        personal-website:latest
    
    # Clean up
    rm /tmp/personal-website.tar
    
    echo "✅ Deployment completed successfully!"
    echo "🌐 Your website is now available at: http://$(curl -s ifconfig.me)"
EOF

# Clean up local files
rm ${APP_NAME}.tar

echo "🎉 Deployment completed successfully!"
echo "🌐 Your website should be available at: http://$DROPLET_IP"
