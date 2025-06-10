# 회의실 예약 서비스 (찜꽁 ver2)

## 기능

### 예약 기능

- POST "/reservations"
- 사용자는 회의실을 예약할 수 있습니다.

#### 요청

```json
Cookies: token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0Iiwibmlja25hbWUiOiJyZWd1bGFyIiwicm9sZSI6IlJFR1VMQVIiLCJpYXQiOjE3NDk1NDQ1MjcsImV4cCI6MTc0OTU0NTQyN30.kCd5J2asZfh6LYrfINxDrhKLz2ce11nNBsGJ1_2mX-Y
{
    "time": [
        2025,
        6,
        11,
        17,
        35
    ],
    "description": "description",
    "roomId": 1
}
```

#### 응답

```json
{
  "reservationId": 1
}
```

### 예약 현황 조회

- GET "/reservations/search"
- 모든 사용자는 예약 현황을 확인할 수 있습니다.
- 날짜마다 회의실별 예약 정보를 조회한다.

#### 요청

```json
{
  "date": [2025, 6, 11],
  "roomId": 1
}
```

#### 응답

```json
{
  "responses": [
    {
      "time": "2025-06-11T17:35:00",
      "description": "description",
      "roomName": "어드레스방"
    }
  ]
}
```

### 자신의 예약 조회

- GET "/reservations/mine"
- 사용자는 자신이 한 예약의 상세 정보를 확인할 수 있습니다.
  - 상세 정보 : 예약 설명 + 날짜 + 위치

#### 요청

```json
Cookies: token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0Iiwibmlja25hbWUiOiJyZWd1bGFyIiwicm9sZSI6IlJFR1VMQVIiLCJpYXQiOjE3NDk1NDQ1MjcsImV4cCI6MTc0OTU0NTQyN30.kCd5J2asZfh6LYrfINxDrhKLz2ce11nNBsGJ1_2mX-Y
{
    "reservationId": 1
}
```

#### 응답

```json
{
  "responses": [
    {
      "time": "2025-06-11T17:35:00",
      "description": "description",
      "roomName": "어드레스방"
    }
  ]
}
```

### 예약 수정

- PUT "/reservations"
- 사용자는 본인의 예약을 수정할 수 있습니다.

#### 요청

```json
Cookies: token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0Iiwibmlja25hbWUiOiJyZWd1bGFyIiwicm9sZSI6IlJFR1VMQVIiLCJpYXQiOjE3NDk1NDQ1MjcsImV4cCI6MTc0OTU0NTQyN30.kCd5J2asZfh6LYrfINxDrhKLz2ce11nNBsGJ1_2mX-Y
{
    "reservationId": 1
}
```

#### 응답

```json
{
  "reservationId": 1,
  "time": [2025, 6, 11, 18, 35, 26, 233220000],
  "description": "description",
  "roomId": 1
}
```

### 예약 삭제

- DELETE "/reservations"
- 사용자는 본인의 예약을 삭제할 수 있습니다.

#### 요청

```json
Cookies: token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0Iiwibmlja25hbWUiOiJyZWd1bGFyIiwicm9sZSI6IlJFR1VMQVIiLCJpYXQiOjE3NDk1NDQ1MjcsImV4cCI6MTc0OTU0NTQyN30.kCd5J2asZfh6LYrfINxDrhKLz2ce11nNBsGJ1_2mX-Y
{
    "reservationId": 1
}
```

#### 응답

```json
{
  "responses": []
}
```

### 회원 가입 - 이름 추천

- POST "/members/signup"
- 랜덤 이름을 추천받아 회원가입합니다.

#### 요청

```json
{
  "wantRandomNickname": true,
  "nickname": null,
  "email": "regular@gmail.com",
  "password": "password"
}
```

#### 응답

```json
{
  "memberId": 4,
  "nickname": "regular"
}
```

### 회원 가입 - 선택된 이름

- POST "/members/signup"
- 지정된 이름으로 회원가입합니다.

#### 요청

```json
{
  "wantRandomNickname": false,
  "nickname": "regular",
  "email": "regular@gmail.com",
  "password": "password"
}
```

#### 응답

```json
{
  "memberId": 4,
  "nickname": "regular"
}
```

### 로그인

- GET "/members/login"
- 사용자는 로그인을 할 수 있습니다.

#### 요청

```json
{
  "email": "regular@gmail.com",
  "password": "password"
}
```

#### 응답

```json
Set-Cookie: token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0Iiwibmlja25hbWUiOiJyZWd1bGFyIiwicm9sZSI6IlJFR1VMQVIiLCJpYXQiOjE3NDk1NDQ1MjUsImV4cCI6MTc0OTU0NTQyNX0.Hw8yluypXhImKE2IIKVaotDBzNuFrz-K0kZRUlrSyEE; Path=/; Max-Age=3600; Expires=Tue, 10 Jun 2025 09:35:25 GMT; HttpOnly; SameSite=Lax
{}
```
