# AWS 배포 가이드

## 인프라 구성

### 필요한 AWS 리소스

- **Dev 환경**: EC2 1대 + RDS MySQL 1대
- **Prod 환경**: EC2 1대 + RDS MySQL 1대

### VPC 구성

```
VPC
├── Public Subnet (EC2 인스턴스용)
└── Private Subnet (RDS 인스턴스용)
```

## RDS 설정

### Dev RDS

- 엔진: MySQL 8.0
- 인스턴스: db.t3.micro
- 데이터베이스명: `shh_dev`
- 마스터 사용자: `admin`

### Prod RDS

- 엔진: MySQL 8.0
- 인스턴스: db.t3.small (최소)
- 데이터베이스명: `shh_prod`
- 마스터 사용자: `admin`
- 백업 보존: 7일
- Multi-AZ 배포 권장

## EC2 설정

### 보안그룹

**Web Server 보안그룹**

- SSH: 22 (관리자 IP만)
- HTTP: 80 (0.0.0.0/0)
- HTTPS: 443 (0.0.0.0/0)
- Custom: 8080 (ALB에서만 - 필요시)

**Database 보안그룹**

- MySQL: 3306 (EC2 보안그룹에서만)

### EC2 인스턴스 준비

```bash
# Java 21 설치
sudo yum update -y
sudo yum install -y java-21-amazon-corretto

# 애플리케이션 디렉토리 생성
sudo mkdir -p /opt/shh-app
sudo chown ec2-user:ec2-user /opt/shh-app
```

## 배포 방법

### 1. 코드 업로드

```bash
# Git으로 코드 받기
cd /opt/shh-app
git clone <your-repository>
cd <project-directory>
```

### 2. 환경변수 설정

```bash
# Dev 환경용
export DB_HOST=your-dev-rds-endpoint.rds.amazonaws.com
export DB_PASSWORD=your-dev-password
export JWT_SECRET=your-long-jwt-secret-key

# Prod 환경용  
export DB_HOST=your-prod-rds-endpoint.rds.amazonaws.com
export DB_PASSWORD=your-prod-password
export JWT_SECRET=your-long-jwt-secret-key
```

### 3. 배포 실행

```bash
# Dev 환경 배포
chmod +x deploy-dev.sh
./deploy-dev.sh

# Prod 환경 배포
chmod +x deploy-prod.sh  
./deploy-prod.sh
```

## 환경별 차이점

| 구분       | Dev    | Prod     |
|----------|--------|----------|
| DDL 모드   | update | validate |
| SQL 로깅   | true   | false    |
| 커넥션 풀    | 기본값    | 최적화됨     |
| JWT 만료시간 | 10분    | 60분      |

## 주의사항

1. **prod 환경에서는 DDL auto가 validate** → 스키마 변경시 수동으로 해야 함
2. **환경변수로 민감정보 관리** → AWS Parameter Store 사용 권장
3. **RDS는 Private Subnet에 배치** → 보안 강화
4. **Prod는 백그라운드 실행** → nohup 사용

## AWS Parameter Store 사용 (권장)

환경변수 대신 Parameter Store 사용:

```bash
# Parameter 생성 예시
aws ssm put-parameter --name "/mj/dev/db-password" --value "your-password" --type "SecureString"
aws ssm put-parameter --name "/mj/prod/jwt-secret" --value "your-secret" --type "SecureString"
```

EC2에서 Parameter Store 값 가져오기:

```bash
export DB_PASSWORD=$(aws ssm get-parameter --name "/mj/dev/db-password" --with-decryption --query "Parameter.Value" --output text)
```
