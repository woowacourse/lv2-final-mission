### 필수 기능
- 사용자 본인은 자신이 한 예약의 상세 정보까지 확인할 수 있습니다.
  - 멤버 id로 찾기
- 사용자는 본인의 예약만 수정하고 삭제할 수 있습니다.
  - 로그인 기능

- 데이터베이스는 H2 사용


---
### 기능 구현

- 멤버
  - [x] 멤버 회원가입
  - [x] 멤버 로그인
    - jwt + 쿠키
      - [x] 생성
      - [x] 추출
- 식당
  - [x] 식당 생성
  - [x] 식당 id로 조회

- 예약
  - [x] 예약 생성
  - [x] 예약 조회
    - [x] 예약 단건 조회
    - [x] 예약 전체 조회
    - [x] 예약 삭제
    - [ ] 예약 수정

- 외부 API: 특일
  - [ ] 예약 생성 시 공휴일 알림

---
## 구현한 API
- **MemberController**
  - [POST] /members : 회원가입
  - [POST] /login : 로그인
  
- **RestaurantController**
  - [POST] /restaurants : 식당 생성
  - [GET] /restaurants/{id} : 식당 단건 조회
  
- **ReservationController**
  - [POST] /reservations : 예약 생성
  - [GET] /reservations : 예약 전체 조회
  - [GET] /reservations/{id} : 예약 단건 조회
  - [DELETE] /reservations/{id} : 예약 단건 삭제

