# 스케쥴러를 활용한 예약 시스템

## 기능 구현 목록

### ✅ 사용자 인증
- 로그인 : 이메일과 패스워드를 통한 세션 기반 로그인
- 회원가입 : 이메일과 패스워드를 통한 회원가입

### ✅ 예약 관리
- 예약 생성 : 날짜, 시간대, 인원수를 지정하여 예약 생성
- 예약 조회 : 사용자별 예약 목록 조회
- 예약 수정 : 기존 예약의 날짜, 시간, 인원수 변경
- 예약 삭제 : 예약 취소 및 취소된 예약 이력 저장

### ✅ 휴일 관리
- 외부 API 연동 : 공공데이터포털 API를 통한 공휴일 정보 자동 수집
- 휴일 검증 : 공휴일에는 예약 생성 및 수정 불가
- 휴일 저장 스케줄러 : 매월 1일 공휴일 데이터를 저장

### ✅회의실 관리(관리자)
- 회의실 추가 : 회의실 추가 기능

## API 명세서

### ✅ 인증

#### `POST /login`
**설명**: 사용자 로그인
**요청**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```
**응답**: 200 OK (응답 본문 없음, 세션 생성)

#### `POST /signup`
**설명**: 사용자 회원가입
**요청**:
```json
{
  "email": "newuser@example.com",
  "password": "mypassword123"
}
```
**응답**: 201 Created
```json
{
  "email": "newuser@example.com"
}
```

### ✅ 예약

#### `POST /reservation`
**설명**: 예약 생성
**요청**:
```json
{
  "reservationDate": "2024-12-25",
  "startTime": 9,
  "endTime": 11,
  "numberOfPeople": 4
}
```
**응답**: 201 Created
```json
{
  "id": 1,
  "email": "user@example.com",
  "startTime": 9,
  "endTime": 11,
  "numberOfPeople": 4
}
```

#### `GET /reservation`
**설명**: 예약 목록 조회
**요청**: 요청 본문 없음
**응답**: 200 OK
```json
[
  {
    "id": 1,
    "email": "user@example.com",
    "startTime": 9,
    "endTime": 11,
    "numberOfPeople": 4
  },
  {
    "id": 2,
    "email": "user@example.com",
    "startTime": 14,
    "endTime": 16,
    "numberOfPeople": 2
  }
]
```

#### `PATCH /reservation/{id}`
**설명**: 예약 수정
**요청**:
```json
{
  "reservationDate": "2024-12-26",
  "startTime": 10,
  "endTime": 12,
  "numberOfPeople": 6
}
```
**응답**: 200 OK
```json
{
  "id": 1,
  "email": "user@example.com",
  "startTime": 10,
  "endTime": 12,
  "numberOfPeople": 6
}
```

#### `DELETE /reservation/{id}`
**설명**: 예약 삭제
**요청**: 요청 본문 없음
**응답**: 204 No Content

### ✅ 관리자

#### `POST /admin/room`
**설명**: 룸 생성
**요청**:
```json
{
  "name": "회의실 A",
  "maxNumberOfPeople": 10
}
```
**응답**: 201 Created
```json
{
  "id": 1,
  "name": "회의실 A",
  "maxNumberOfPeople": 10
}
```
