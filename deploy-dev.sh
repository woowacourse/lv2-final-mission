#!/bin/bash

# Dev 환경 배포 스크립트
echo "Starting development deployment..."

# 환경변수 설정 (실제 배포시 이 값들은 EC2 환경변수나 Parameter Store에서 가져와야 함)
export SPRING_PROFILES_ACTIVE=dev
export DB_HOST=your-dev-rds-endpoint.rds.amazonaws.com
export DB_PORT=3306
export DB_NAME=mj_dev
export DB_USER=admin
export DB_PASSWORD=your-dev-password
export JWT_SECRET=your-dev-jwt-secret-key
export RANDOMMER_SECRET=your-randommer-api-key

# JAR 파일 빌드
echo "Building application..."
./gradlew clean build -x test

# 애플리케이션 실행
echo "Starting application with dev profile..."
java -jar -Dspring.profiles.active=dev build/libs/*.jar

echo "Development deployment completed!"
