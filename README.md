# 운동 클래스 예약 서비스

## 관리자 기능

- [ ] 클래스 시간 관련 기능
    - [ ] 클래스 시간 조회
    - [ ] 클래스 시간 생성
    - [ ] 클래스 시간 삭제
- [ ] 클래스 관련 기능
    - [ ] 클래스 조회
    - [ ] 클래스 생성
    - [ ] 클래스 삭제
- [ ] 운동 클래스 예약 기능
    - [ ] 전체 회원 조회
    - [ ] 특정 회원 예약 생성
    - [ ] 특정 회원 예약 삭제

## 회원 기능

- [ ] 회원가입 기능
- [ ] 로그인 기능
- [ ] 운동 클래스 예약 기능
    - [ ] 예약 생성
    - [ ] 예약 조회
    - [ ] 예약 삭제

## 외부 API 연동 - 공휴일 정보 조회

- [ ] 예약하려는 날짜가 공휴일이면 예약 불가
- [ ] 예약 시간 정보를 응답할 때 해당 날짜가 공휴일인지 여부를 함께 안내

# 운동 클래스 예약 서비스 API

## 관리자

### 전체 회원 조회

```
Request
GET /admin/reservations

Response
Content-Type: application/json
HTTP/1.1 200 
{
    "id": Long,
    "name": String,
    "date": LocalDate (YYYY-MM-DD),
    "reservationTime": {
        "id": Long,
        "startAt": LocalTime (HH: mm)
    },
    "exerciseCourseName": String
}
```

### 관리자 예약 추가

```
Request
Content-Type: application/json
POST /admin/reservations
{
    "name": String,
    "date": LocalDate (YYYY-MM-DD),
    "timeId": Long,
    "exerciseCourseId": Long
}

Response
Content-Type: application/json
HTTP/1.1 201 
{
    "id": Long
}

```

### 관리자 예약 삭제

```
Request
DELETE /admin/reservations/{id} HTTP/1.1

Response
HTTP/1.1 204
```

## 유저

### 본인 예약 조회

```
Request
GET /reservations/{id}

Response
Content-Type: application/json
HTTP/1.1 200
{
    "id": Long,
    "name": String,
    "date": LocalDate (YYYY-MM-DD),
    "reservationTime": {
        "id": Long,
        "startAt": LocalTime (HH: mm)
    },
    "exerciseCourseName": String
}
```

### 본인 예약 추가

```
Request
Content-Type: application/json
POST /reservations/{id}
{
    "name": String,
    "date": LocalDate (YYYY-MM-DD),
    "timeId": Long,
    "exerciseCourseId": Long
}

Response
Content-Type: application/json
HTTP/1.1 201
{
    "id": Long
}

```

### 본인 예약 삭제

```
Request
DELETE /reservations/{id} HTTP/1.1

Response
HTTP/1.1 204
```

### 클래스 시간 조회

```
Request
GET /times HTTP/1.1

Response
HTTP/1.1 200 
Content-Type: application/json

{
    "id": Long,
    "startAt": LocalTime (HH:mm)
}
```

### 클래스 시간 추가

```
Request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": LocalTime (HH:mm)
}

Response
HTTP/1.1 201
Content-Type: application/json

{
    "id": Long
}
```

### 클래스 시간 삭제

```
Request
DELETE /times/{id} HTTP/1.1

Response
HTTP/1.1 204
```

### 클래스 목록 조회

```
Request
GET /courses HTTP/1.1

Response
HTTP/1.1 200 
Content-Type: application/json

{
    "id": Long,
    "name": String,
    "description": String
}
```

### 클래스 추가

```
Request
POST /courses HTTP/1.1
content-type: application/json

{
    "name": String
    "description": String
}

Response
HTTP/1.1 201
Location: /courses/{id}
Content-Type: application/json

{
    "id": Long
}
```

### 클래스 삭제

```
Request
DELETE /courses/{id} HTTP/1.1

Response
HTTP/1.1 204
```

### 로그인

```
Request
POST /login HTTP/1.1
content-type: application/json
host: localhost:8080

{
    "email": String,
    "password": String
}

Response
HTTP/1.1 200 OK
Content-Type: application/json
Keep-Alive: timeout=60
Set-Cookie: token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6ImFkbWluIiwicm9sZSI
```

### 인증 정보 조회

```
Request
GET /login/check HTTP/1.1
cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
host: localhost:8080

Response
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/json
Date: Sun, 03 Mar 2025 19:16:56 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "name": String
}
```
