# 멍구 약속 예약 서비스

## 📌 기능 요구사항

- 사용자는 멍구와의 약속을 예약할 수 있습니다.
  - 날짜와 시간슬롯을 선택하여 예약
- 모든 사용자는 예약 현황을 확인할 수 있습니다. 
  - 사용자 본인은 자신이 한 예약의 상세 정보까지 확인할 수 있습니다.
- 사용자 본인은 자신의 예약 기록들을 확인할 수 있습니다.
- 사용자는 본인의 예약만 수정하고 삭제할 수 있습니다.

## 용어 정의

- 약속(Plan)


- 예약(Reservation)
  - 정의: 약속에 대한 예약
  - 약속 날짜, 약속 시간, 사용자를 가지고 있음
- 약속 시간(TimeSlot)
  - 정의: 예약 가능한 날짜의 시간들 중 하나
  - 시작 시간, 끝나는 시간을 가지고 있음
- 시간 슬롯들(TimeSlots)
  - 정의: 특정 날짜(PlanDate)에서 가능한 시간들
  - 약속 시간 여러 개를 가지고 있음
- 약속 날짜(PlanDate)
  - 정의: 예약 가능한 하나의 날짜
  - 약속 날짜는 시간 슬롯을 가지고 있음

- 사용자(User)

## API

### 약속 예약 API

날짜와 시간을 선택하여 약속을 예약한다.

GET /reservations
request: planDateId, timeSlot
response: 예약된 Reservation 정보

### 사용자 예약 현황 확인 API

사용자는 자신의 특정 예약에 대해 확인할 수 있습니다.

GET /reservations/{reservationId}
request: 없음
response: Reservation 정보

### 사용자 예약 수정 API

사용자는 자신의 특정 예약에 대해 수정할 수 있습니다.

POST /reservations/{reservationId}
request: 수정할 Reservation 정보 (planDateId, timeSlots)
response: 수정된 Reservation 정보

### 사용자 예약 삭제 API

사용자는 자신의 특정 예약에 대해 삭제할 수 있습니다.

DELETE /reservations/{reservationId}
request: 없음
response: no content

### 사용자 예약 기록 조회 API

사용자는 자신의 예약 기록을 모두 조회할 수 있습니다.

GET /reservations/mine
