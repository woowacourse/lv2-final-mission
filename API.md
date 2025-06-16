# REST API DOCS

## GYM

### 헬스장 등록
> POST /gyms

- REQUEST BODY
  - "name": 헬스장 이름 (3자 이상 10자 이하)
  - "street": 도로명 주소
  - "detail": 상세 주소
  
- RESPONSE
  - 201 CREATED

## Member

### 사용자 등록 (회원가입)
> POST /members

- REQUEST BODY
  - "id": 사용자 ID
  - "password": 비밀번호
  - "name": 사용자 이름 (2자 이상 5자 이하)

- RESPONSE
  - 201 CREATED

### 사용자 로그인
> POST /members/login

- REQUEST BODY
  - "id": 사용자 ID
  - "password": 비밀번호

- RESPONSE
  - 200 CREATED
  - **헤더** 
    - Set-Cookie: "token={발급한 인증 토큰}"

## BOOKING

### 일일권 예약
> POST /bookings

- REQUEST HEADER
  - COOKIE
    - "token": 로그인을 통해 발급받은 JWT 토큰

- REQUEST BODY
  - "gymId": 헬스장 ID
  - "date": 예약할 날짜

- RESPONSE
  - 201 CREATED

### 내 예약 조회
> GET /bookings/mine

- REQUEST HEADER
  - COOKIE
    - "token": 로그인을 통해 발급받은 JWT 토큰

- RESPONSE
  - 200 OK
  - **바디**
    - 아래와 같은 각각의 예약들로 이루어진 컬렉션 응답
    - ```
      id : 예약 ID
      member : 예약자
      gym : 헬스장
      date : 예약 날짜
      ```

### 내 예약 수정
> PATCH /bookings/{id}

- REQUEST HEADER
  - COOKIE
    - "token": 로그인을 통해 발급받은 JWT 토큰

- PATH VARIABLE
  - "id": 예약 ID

- REQUEST BODY
  - "dateToModify": 수정할 예약 날짜

- RESPONSE
  - 200 OK
  - **바디**
    - 아래와 같이 수정된 예약 응답
    - ```
      id : 예약 ID
      member : 예약자
      gym : 헬스장
      date : 예약 날짜
      ```
