# 회의실 예약 시스템

## 기능

### 예약
예약은 주간 시간(09:00 ~ 18:00)간 한시간 단위로 가능합니다.
- 사용자는 회의실을 예약할 수 있습니다.
  - `POST /reservations` 
- 모든 사용자는 예약 현황을 확인할 수 있습니다.
  - `GET /reservations?meetingroom={meetingRoomId}&date={date}`
- 사용자 본인은 자신이 한 예약의 상세 정보까지 확인할 수 있습니다.
  - `GET /member/reservaitons`
- 사용자는 본인의 예약만 수정하고 삭제할 수 있습니다.
  - `PATCH /member/reservations/{id}`
  - `DELETE /member/reservations/{id}`
- 회의실 예약에 성공 시, 사용자에게 이메일을 전송합니다.

### 사용자
- jwt 토큰을 이용하여 사용자를 식별합니다.
- 로그인시 해당하는 사용자가 있다면 jwt 토큰을 반환합니다.
  - `POST /login`
