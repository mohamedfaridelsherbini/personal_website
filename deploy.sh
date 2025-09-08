#!/bin/bash

# DigitalOcean Deployment Script for Personal Website
# This script builds and deploys the Kotlin/Ktor application to DigitalOcean

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Error handling
trap 'echo -e "${RED}❌ Deployment failed! Check the logs above.${NC}"; exit 1' ERR

# Configuration
DROPLET_IP="YOUR_DROPLET_IP_HERE"
DROPLET_USER="root"
APP_NAME="personal-website"
CONTAINER_NAME="personal-website-container"
IMAGE_NAME="personal-website:latest"

echo -e "${BLUE}🚀 Starting deployment to DigitalOcean...${NC}"

# Build Docker image locally for AMD64 platform
echo -e "${YELLOW}📦 Building Docker image...${NC}"
docker build --platform linux/amd64 -t $IMAGE_NAME .

# Save image to tar file
echo -e "${YELLOW}💾 Saving Docker image...${NC}"
docker save $IMAGE_NAME > ${APP_NAME}.tar

# Copy image to DigitalOcean droplet
echo -e "${YELLOW}📤 Uploading image to DigitalOcean...${NC}"
scp -i ~/.ssh/id_ed25519_digital ${APP_NAME}.tar ${DROPLET_USER}@${DROPLET_IP}:/root/

# Deploy on DigitalOcean droplet
echo -e "${YELLOW}🔄 Deploying on DigitalOcean...${NC}"
ssh -i ~/.ssh/id_ed25519_digital ${DROPLET_USER}@${DROPLET_IP} << 'EOF'
    # Load Docker image
    docker load < /root/personal-website.tar
    
    # Stop and remove existing container if it exists
    docker stop personal-website-container 2>/dev/null || true
    docker rm personal-website-container 2>/dev/null || true
    
    # Run new container
    docker run -d \
        --name personal-website-container \
        --restart unless-stopped \
        -p 8080:8080 \
        personal-website:latest
    
    # Wait for container to start
    echo "⏳ Waiting for application to start..."
    sleep 10
    
    # Health check
    if curl -f http://localhost:8080/ >/dev/null 2>&1; then
        echo "✅ Application is running and healthy!"
    else
        echo "⚠️  Warning: Application may not be fully started yet"
        echo "📋 Container logs:"
        docker logs personal-website-container --tail 10
    fi
    
    # Clean up
    rm /root/personal-website.tar
    
    echo -e "${GREEN}✅ Deployment completed successfully!${NC}"
    echo -e "${GREEN}🌐 Your website is now available at:${NC}"
    echo -e "${GREEN}   🔄 HTTP:  http://$(curl -s ifconfig.me)${NC}"
EOF

# Clean up local files
rm ${APP_NAME}.tar

echo -e "${GREEN}🎉 Deployment completed successfully!${NC}"
echo -e "${GREEN}🌐 Your website is now available at:${NC}"
echo -e "${GREEN}   🔄 HTTP:  http://$DROPLET_IP${NC}"
