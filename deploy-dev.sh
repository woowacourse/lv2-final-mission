#!/bin/bash

# 개발서버 배포 스크립트

set -e

echo "==================== 개발서버 배포 시작 ===================="

PROJECT_NAME="woowa-level3-final"
IMAGE_NAME="woowa-level3-final"

# 시스템 패키지 업데이트 및 필수 패키지 설치
echo "0-1. 시스템 패키지 업데이트"
sudo apt update
sudo apt install -y curl wget ca-certificates gnupg lsb-release

# Docker 및 Docker Compose 설치 확인
echo "0-2. Docker 및 Docker Compose 설치 확인"
if ! command -v docker &> /dev/null; then
    echo "Docker가 설치되어 있지 않습니다. 설치를 시작합니다..."
    curl -fsSL https://get.docker.com -o get-docker.sh
    sudo sh get-docker.sh
    sudo usermod -aG docker $USER
    rm get-docker.sh
    echo "Docker 설치가 완료되었습니다."
else
    echo "Docker가 이미 설치되어 있습니다."
fi

if ! command -v docker-compose &> /dev/null; then
    echo "Docker Compose가 설치되어 있지 않습니다. 설치를 시작합니다..."
    sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    echo "Docker Compose 설치가 완료되었습니다."
else
    echo "Docker Compose가 이미 설치되어 있습니다."
fi

# Docker 서비스 시작
sudo systemctl start docker
sudo systemctl enable docker

# 기존 컨테이너 중지 및 제거
echo "1. 기존 컨테이너 중지 및 제거"
docker-compose -f docker-compose.dev.yml down || true

# 최신 이미지 풀 (강제)
echo "2. 최신 이미지 풀"
FULL_IMAGE_NAME="koseonje/$IMAGE_NAME:latest-dev"
docker rmi $FULL_IMAGE_NAME || true
docker pull $FULL_IMAGE_NAME

# Docker Compose로 서비스 시작
echo "3. Docker Compose로 서비스 시작"
docker-compose -f docker-compose.dev.yml up -d

# 배포 완료 확인
echo "4. 배포 완료 확인"
sleep 10
if docker ps | grep -q $PROJECT_NAME; then
    echo "✅ 개발서버 배포가 성공적으로 완료되었습니다!"
    docker ps | grep $PROJECT_NAME
else
    echo "❌ 개발서버 배포에 실패했습니다."
    docker logs $PROJECT_NAME
    exit 1
fi

echo "==================== 개발서버 배포 완료 ===================="
