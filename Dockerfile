# 사용할 JDK 버전의 베이스 이미지를 선택합니다. (Corretto 21)
FROM amazoncorretto:21-alpine-jdk

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 빌드된 Jar 파일의 이름을 `app.jar`로 변경하여 이미지 안으로 복사합니다.
# build/libs/*.jar 경로는 Gradle 기준이며, Maven은 target/*.jar 입니다.
COPY build/libs/*.jar app.jar

# 애플리케이션이 사용할 포트를 외부에 알립니다.
EXPOSE 8080

# 컨테이너가 시작될 때 실행할 명령어를 지정합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]
