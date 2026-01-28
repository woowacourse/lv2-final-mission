  맛집 예약 서비스

맛집 리스트를 조회하고 원하는 맛집을 예약할 수 있는 서비스입니다.

## 제공 API

### 식당

#### 식당 목록 조회

Path: `GET /shops`

Request Param:

Request Body:
```

```

Response Body:
```
[
    {
        "id": 1,
        "name": "칰칰폭폭 치킨집",
        "type": "야식"
    }
]
```

#### 식당 상세 정보 조회

Path: `GET /shops/{id}`

Request Param:

Request Body:
```

```

Response Body:
```
{
    "id": 2,
    "name": "뿡뿡이가 좋아요 중국집",
    "type": "중식",
    "detail": "사천짜장이 기가막혀요",
    "innerOperatingHours": [
        {
            "dayOfWeek": "SATURDAY",
            "time": [
                "08:00:00",
                "09:00:00",
                "10:00:00"
            ]
        }
    ]
}
```

#### 식당 예약 가능 시간 확인

Path: `GET /shops/{id}/times`

Request Param:
`date=2025-06-10`

Request Body:
```

```

Response Body:
```
[
    "20:00:00",
    "21:00:00",
    "22:00:00"
]
```

#### 식당 예약(결제)

Path: `POST /shops/{id}`

Request Param:
`date=2025-06-10&time=12:00`

Response Body:
```
{
    "id": 1,
    "userId": 2,
    "shop": {
        "id": 3,
        "name": "민수네 떡볶이",
        "type": "분식"
    },
    "date": "2025-06-13",
    "time": "12:00:00"
}
```

#### 식당 예약 취소

Path: `DELETE /shops/{id}`

Request Param:
`reservationId=1`

Response Body:
```
```

#### 내 예약 목록 조회

Path: `DELETE /shops/{id}`

Response Body:
```
[
    {
        "id": 1,
        "shop": {
            "id": 1,
            "name": "칰칰폭폭 치킨집",
            "type": "야식"
        },
        "date": "2025-06-13",
        "time": "12:00:00"
    }
]
```

---

### 사용자


#### 회원가입

Path: `POST /users/join`

Request Param:

Request Body:
```
{
    "name": "[optional] nickname",
    "email": "moko@example.com",
    "password": "1234"
}
```

Response Body:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsImlhdCI6MTc0OTUzNTQ2NSwiZXhwIjoxODQ5NTM1NDY0fQ.choEAoZF0v_qoXcP1NRxOwy3RXObIxFkuvA27qf1xAE"
}
```

#### 로그인

Path: `POST /users/login`

Request Param:

Request Body:
```
{
    "email": "moko@example.com",
    "password": "1234"
}
```

Response Body:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsImlhdCI6MTc0OTUzNTQ2NSwiZXhwIjoxODQ5NTM1NDY0fQ.choEAoZF0v_qoXcP1NRxOwy3RXObIxFkuvA27qf1xAE"
}
```

---

### 식당 사장님

#### 회원가입

Path: `POST /owners/register`

Request Body:
```
{
    "businessLicenseUrl": "사업자등록증 링크",
    "businessRegistrationNumber": "사업자등록번호"
}
```

Response Body:
```
```

#### 식당 등록

Path: `POST /owners/register/shops`

Request Body:
```
{
  "id": 1,
  "name": "마라마라마라탕",
  "type": "중식",
  "detail": "마라탕먹지마라탕",
  "operatingHours": [
    {
        "dayOfWeek": "MONDAY",
        "time": "10:00"
    },
    {
        "dayOfWeek": "FRIDAY",
        "time": "12:00"
    }
  ]
}
```

Response Body:
```
{
    "id": 4,
    "name": "마라마라마라탕",
    "type": "중식",
    "detail": "마라탕먹지마라탕",
    "innerOperatingHours": [
        {
            "dayOfWeek": "MONDAY",
            "time": [
                "10:00:00"
            ]
        },
        {
            "dayOfWeek": "FRIDAY",
            "time": [
                "12:00:00"
            ]
        }
    ]
}
```

#### 식당 정보 수정

Path: `PUT /owners/shops/{id}`

Request Body:
```
{
  "name": "마라탕",
  "type": "중식",
  "detail": "마라탕먹어",
  "operatingHours": [
    {
        "dayOfWeek": "MONDAY",
        "time": "10:00"
    },
    {
        "dayOfWeek": "FRIDAY",
        "time": "12:01"
    }
  ]
}
```

Response Body:
```
{
    "id": 4,
    "name": "마라탕",
    "type": "중식",
    "detail": "마라탕먹어",
    "innerOperatingHours": [
        {
            "dayOfWeek": "MONDAY",
            "time": [
                "10:00:00"
            ]
        },
        {
            "dayOfWeek": "FRIDAY",
            "time": [
                "12:01:00"
            ]
        }
    ]
}
```

## 걸린 시간
최소 요구사항(사장님 제외): 5시간 + 1시간 10분
사장님까지 구현: 5시간 + 1시간 55분
전체 구현(테스트 포함): 5시간 + 2시간 40분
