# 인프라 설정
이 프로젝트는 Spring Boot 애플리케이션과 MySQL 데이터베이스를 분리된 환경에서 실행하도록 구성되었습니다.
•	애플리케이션: EC2 인스턴스에서 실행 (git clone 후 docker-compose로 배포)
•	데이터베이스: Docker 기반 MySQL 컨테이너로 실행 (별도 서버에서 배포)
•	연결 방식: Spring Boot → MySQL (JDBC URL 기반)
•	환경변수 관리: .env 파일과 application.properties 조합
