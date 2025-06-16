## 커피챗 예약 서비스 - TALK COFFEE☕️

### 기능 요구사항

### 로그인
- [x] 유효한 이메일을 입력할 경우, 토큰을 발급할 수 있어야 한다.

### 예약
- [x] 크루ID, 코치ID, 예약 시간이 주어졌을 때, 예약을 생성할 수 있어야 한다.
- [x] 코치는 본인의 예약을 삭제할 수 있어야 한다.
- [x] 크루는 본인의 예약을 삭제할 수 있어야 한다.

### 유저
- [x] 본인의 예약을 조회할 수 있어야 한다.
- [ ] 유저는 커피챗을 예약할 수 있어야 한다.
  - [ ] 이미 해당 시간에 다른 유저의 예약이 존재한다면, 예약할 수 없다.

### 코치
- [x] 본인의 예약을 조회할 수 있어야 한다.
- [x] 코치는 예약을 수락하거나 거절할 수 있어야 한다.
- [x] 예약을 수락하면 유저에게 메일 발송이 되어야한다.


## API 명세서
- [x] GET /reservations - 모든 예약을 가져온다
- [x] GET /reservations/{crewId} - 해당 크루가 예약한 모든 예약을 조회한다.
- [x] GET /reservations/{coachId} - 해당 코치가 예약한 모든 예약을 조회한다.
- [x] POST /reservations - 예약을 추가한다
- [x] DELETE /reservations/{id} - 특정 예약을 삭제한다

- [x] POST /reservations/{reservationId}/accept - 예약 신청을 수락하거나 거절한다
- [x] POST /login-coach - 코치 로그인
- [x] POST /login-crew - 크루 로그인