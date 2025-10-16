#!/bin/bash

# Production 환경 배포 스크립트
echo "Starting production deployment..."

# 환경변수 설정 (실제 배포시 이 값들은 EC2 환경변수나 Parameter Store에서 가져와야 함)
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=your-prod-rds-endpoint.rds.amazonaws.com
export DB_PORT=3306
export DB_NAME=mj_prod
export DB_USER=admin
export DB_PASSWORD=your-prod-password
export JWT_SECRET=your-prod-jwt-secret-key
export RANDOMMER_SECRET=your-randommer-api-key

# JAR 파일 빌드
echo "Building application..."
./gradlew clean build

# 기존 프로세스 종료
echo "Stopping existing application..."
pkill -f "java.*spring.profiles.active=prod" || echo "No existing process found"

# 애플리케이션 백그라운드 실행
echo "Starting application with prod profile..."
nohup java -jar -Dspring.profiles.active=prod build/libs/*.jar > app.log 2>&1 &

echo "Production deployment completed!"
echo "Application is running in background. Check app.log for logs."
