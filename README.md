### 원온원 예약 서비스

---
+ 코치와의 원온원을 예약할 수 있는 서비스
---

### 요구사항
#### 코치 목록

```
교육분야   코치닉네임
Captain  포비
Captain  제이슨
Android  제임스
Android  레아
Android  디노
BE  브라운
BE  구구
BE  네오
BE  브리
BE  솔라
BE  저스틴
BE  검프
FE  공원
FE  준
FE  크론
FE  시지프
SS  워니
SS  리사
SS  왼손
```

#### 예약 가능 시간
+ 예약 가능 시간은 10:00부터 17:00시 사이에서만 가능하다.
+ 원온원 시간은 1시간이다.
+ 각 코치별로 예약 가능한 시간의 목록을 보여줄 수 있다.
+ 공휴일에는 원온원을 신청할 수 없다.
+ 이미 원온원을 신청한 시간대에는 다른 원온원을 신청할 수 없다.
---

### API 설계
+ api 접근 권한은 다음과 같다.
```
GUEST
CREW
COACH
```
#### 크루
+ [x] 크루 목록 조회(COACH)
```
GET /crews HTTP/1.1
```
```
HTTP/1.1 201
Content-Type: application/json
{
    {
        "id": 1,
        "nickname": "링크"
    }
}
```
+ [ ] 로그인(GUEST)
```
POST /members HTTP/1.1
Content-Type: application/json
{
    "nickname": "링크",
    "password": "qwerty"
}
```
```
HTTP/1.1 200
Content-Type: application/json
```

#### 코치
+ [ ] 코치 목록 조회(COACH)
```
GET /coaches HTTP/1.1
```
```
HTTP/1.1 201
Content-Type: application/json
{
    {
        "id": 1,
        "nickname": "포비",
        "education": "Captain"
    }
}
```

#### 예약 가능 시간
+ [ ] 코치별 예약 가능 시간 추가(COACH)
```
POST /dateTimes HTTP/1.1
{  
    "dateTime": "2025-08-09 10:00"
}
```
```
HTTP/1.1 201
Content-Type: application/json
Location: /dateTimes/{id}
```
+ [ ] 예약 가능 시간 목록 조회(CREW)
```
GET /dateTimes?nickname="포비" HTTP/1.1
```
```
HTTP/1.1 201
Content-Type: application/json
{  
    {
        "id": 1,
        "dateTime": "2025-08-09 10:00"
    }
}
```

+ [ ] 예약 가능 시간 삭제(COACH)
```
DELETE /dateTimes/{id} HTTP/1.1
```
```
HTTP/1.1 204
Content-Type: application/json
```

#### 예약
+ [ ] 예약 추가(CREW)
```
POST /reservations HTTP/1.1
{  
    "coach": "포비"
    "dateTime": "2025-08-09 10:00"
}
```
```
HTTP/1.1 201
Content-Type: application/json
Location: /reservations/{id}
```
+ [ ] 예약 정보 수정(CREW)
```
PUT /reservations HTTP/1.1
{  
    "coach": "포비"
    "dateTime": "2025-08-09 10:00"
}
```
```
HTTP/1.1 200
Content-Type: application/json
Location: /reservations/{id}
```
+ [ ] 크루별 예약 조회(CREW)
```
GET /reservations HTTP/1.1
```
```
HTTP/1.1 201
Content-Type: application/json
{  
    {
        "id": 1,
        "coach": "포비",
        "dateTime": "2025-08-09 10:00"
    }
}
```

+ [ ] 코치별 예약 조회(COACH)
```
GET /reservations HTTP/1.1
```
```
HTTP/1.1 201
Content-Type: application/json
{  
    {
        "id": 1,
        "crew": "링크",
        "dateTime": "2025-08-09 10:00"
    }
}
```
+ [ ] 예약 삭제(CREW)
```
DELETE /reservations/{id} HTTP/1.1
```
```
HTTP/1.1 204
Content-Type: application/json
```