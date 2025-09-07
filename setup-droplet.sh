#!/bin/bash

# DigitalOcean Droplet Setup Script
# This script installs Docker and prepares the droplet for deployment

set -e

echo "🐳 Setting up DigitalOcean Droplet for Docker deployment..."

# Update system packages
echo "📦 Updating system packages..."
apt update && apt upgrade -y

# Install required packages
echo "🔧 Installing required packages..."
apt install -y curl wget git unzip

# Install Docker
echo "🐳 Installing Docker..."
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

# Start and enable Docker service
echo "🚀 Starting Docker service..."
systemctl start docker
systemctl enable docker

# Add current user to docker group (if not root)
if [ "$USER" != "root" ]; then
    usermod -aG docker $USER
    echo "👤 Added $USER to docker group"
fi

# Install Docker Compose
echo "🔧 Installing Docker Compose..."
curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# Verify installations
echo "✅ Verifying installations..."
docker --version
docker-compose --version

# Configure firewall (optional)
echo "🔥 Configuring firewall..."
ufw allow 22/tcp   # SSH
ufw allow 80/tcp   # HTTP
ufw allow 443/tcp  # HTTPS
ufw --force enable

echo "🎉 Droplet setup completed successfully!"
echo "📋 Next steps:"
echo "1. Update deploy.sh with this droplet's IP address"
echo "2. Run ./deploy.sh from your local machine"
echo "3. Your website will be available at http://$(curl -s ifconfig.me)"
