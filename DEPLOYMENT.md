# DigitalOcean Deployment Guide

This guide provides multiple options for deploying your personal website to DigitalOcean.

## Prerequisites

- DigitalOcean account
- Docker installed locally (for Option 1)
- SSH access to your DigitalOcean droplet (for Option 1)

## Option 1: Docker Deployment (Recommended)

### Step 1: Create a DigitalOcean Droplet

1. Go to [DigitalOcean Control Panel](https://cloud.digitalocean.com/)
2. Click "Create" → "Droplets"
3. Choose:
   - **Image**: Ubuntu 22.04 LTS
   - **Size**: Basic plan ($6/month minimum)
   - **Authentication**: SSH Key or Password
4. Create the droplet

### Step 2: Install Docker on Droplet

```bash
# SSH into your droplet
ssh root@YOUR_DROPLET_IP

# Install Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

# Start Docker service
systemctl start docker
systemctl enable docker
```

### Step 3: Deploy Using Script

1. Update the `deploy.sh` script with your droplet IP:
   ```bash
   # Edit deploy.sh and replace YOUR_DROPLET_IP_HERE with your actual IP
   nano deploy.sh
   ```

2. Make the script executable and run it:
   ```bash
   chmod +x deploy.sh
   ./deploy.sh
   ```

### Step 4: Access Your Website

Your website will be available at: `http://YOUR_DROPLET_IP`

## Option 2: DigitalOcean App Platform

### Step 1: Connect GitHub Repository

1. Go to [DigitalOcean App Platform](https://cloud.digitalocean.com/apps)
2. Click "Create App"
3. Choose "GitHub" as source
4. Select your `personal_website` repository
5. Choose branch: `main`

### Step 2: Configure App

1. **Build Command**: `./gradlew build --no-daemon`
2. **Run Command**: `java -jar build/libs/personal-website-1.0-SNAPSHOT.jar`
3. **Environment**: Java
4. **Instance Size**: Basic XXS ($5/month)

### Step 3: Deploy

1. Click "Create Resources"
2. Wait for deployment to complete
3. Your app will be available at the provided URL

## Option 3: Manual VPS Deployment

### Step 1: Create Droplet

Same as Option 1, Step 1

### Step 2: Install Java and Dependencies

```bash
# SSH into droplet
ssh root@YOUR_DROPLET_IP

# Update system
apt update && apt upgrade -y

# Install Java 17
apt install openjdk-17-jdk -y

# Install Git
apt install git -y
```

### Step 3: Clone and Build

```bash
# Clone repository
git clone https://github.com/mohamedfaridelsherbini/personal_website.git
cd personal-website

# Build application
./gradlew build --no-daemon

# Run application
java -jar build/libs/personal-website-1.0-SNAPSHOT.jar
```

### Step 4: Setup as Service

Create a systemd service:

```bash
# Create service file
nano /etc/systemd/system/personal-website.service
```

Add this content:

```ini
[Unit]
Description=Personal Website
After=network.target

[Service]
Type=simple
User=root
WorkingDirectory=/root/personal-website
ExecStart=/usr/bin/java -jar build/libs/personal-website-1.0-SNAPSHOT.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Enable and start the service:

```bash
systemctl daemon-reload
systemctl enable personal-website
systemctl start personal-website
```

## Domain Setup (Optional)

### Step 1: Add Domain to DigitalOcean

1. Go to "Networking" → "Domains"
2. Add your domain
3. Configure DNS records:
   - A record: `@` → Your droplet IP
   - A record: `www` → Your droplet IP

### Step 2: SSL Certificate (Recommended)

For Docker deployment, add SSL with Let's Encrypt:

```bash
# Install Certbot
apt install certbot -y

# Get SSL certificate
certbot certonly --standalone -d yourdomain.com -d www.yourdomain.com

# Update docker-compose.yml to include SSL
```

## Monitoring and Maintenance

### Health Checks

The application includes health checks accessible at `/health`

### Logs

View application logs:

```bash
# Docker
docker logs personal-website-container

# Systemd service
journalctl -u personal-website -f
```

### Updates

To update your application:

1. Push changes to GitHub
2. Run deployment script again (Option 1)
3. Or redeploy through App Platform (Option 2)

## Cost Estimation

- **Basic Droplet**: $6/month
- **App Platform**: $5/month
- **Domain**: $12/year (optional)
- **Total**: ~$6-7/month

## Troubleshooting

### Common Issues

1. **Port 8080 not accessible**: Check firewall settings
2. **Application won't start**: Check Java version and logs
3. **SSL issues**: Verify domain configuration

### Useful Commands

```bash
# Check if application is running
curl http://localhost:8080/

# Check Docker containers
docker ps

# Check systemd services
systemctl status personal-website
```

## Security Considerations

1. **Firewall**: Configure UFW or iptables
2. **SSH**: Use key-based authentication
3. **Updates**: Keep system and dependencies updated
4. **SSL**: Always use HTTPS in production

## Support

For issues with deployment, check:
- DigitalOcean documentation
- Docker documentation
- Ktor documentation
