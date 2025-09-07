# Quick DigitalOcean Deployment Guide

## Prerequisites
- DigitalOcean account
- Docker installed locally
- SSH access to your droplet

## Step-by-Step Deployment

### 1. Create DigitalOcean Droplet
1. Go to [DigitalOcean Control Panel](https://cloud.digitalocean.com/)
2. Click "Create" → "Droplets"
3. Choose:
   - **Image**: Ubuntu 22.04 LTS
   - **Size**: Basic plan ($6/month)
   - **Authentication**: SSH Key (recommended)
   - **Hostname**: `personal-website-server`
4. Click "Create Droplet"
5. **Note the IP address** (e.g., `164.90.xxx.xxx`)

### 2. Setup Droplet
```bash
# SSH into your droplet
ssh root@YOUR_DROPLET_IP

# Run setup script
curl -fsSL https://raw.githubusercontent.com/mohamedfaridelsherbini/personal_website/main/setup-droplet.sh | bash
```

### 3. Update Deployment Script
```bash
# Edit deploy.sh and replace YOUR_DROPLET_IP_HERE with your actual IP
nano deploy.sh
```

### 4. Deploy Application
```bash
# Run deployment script
./deploy.sh
```

### 5. Access Your Website
Your website will be available at: `http://YOUR_DROPLET_IP`

## Troubleshooting

### If deployment fails:
1. Check if Docker is running on droplet:
   ```bash
   ssh root@YOUR_DROPLET_IP
   docker --version
   ```

2. Check if port 80 is accessible:
   ```bash
   curl http://localhost:8080/
   ```

3. View container logs:
   ```bash
   ssh root@YOUR_DROPLET_IP
   docker logs personal-website-container
   ```

### If website is not accessible:
1. Check firewall:
   ```bash
   ssh root@YOUR_DROPLET_IP
   ufw status
   ```

2. Check if container is running:
   ```bash
   ssh root@YOUR_DROPLET_IP
   docker ps
   ```

## Cost
- **Droplet**: $6/month
- **Total**: ~$6/month

## Next Steps (Optional)
1. **Add Domain**: Configure DNS to point to your droplet IP
2. **SSL Certificate**: Add HTTPS with Let's Encrypt
3. **Monitoring**: Set up health checks and alerts
