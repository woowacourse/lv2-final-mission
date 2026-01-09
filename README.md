# 주제 : 항공사 항공권 예약
- 항공권 예약 프로그램
- 기본적인 예약 CRUD 구현, 토스 페이먼츠 결제 API 연동 (예약 등록은 결제 승인이 필요함)
---
# Entity 설계
### Member

- Id(pk)
- 이름: String
- email(Id로 사용): String
- password: String

### Reservation

- Id (Pk)
- Member (Fk)
- `departureDateTime`: LocalDateTime
- `arrivalDateTime`: LocalDateTime
- 출발지: String
- 도착지: String
- passportId: String
- 항공기 편명: String

### Payment
- Id(pk)
- Reservation(Fk)
- paymentKey: String
- orderId: String
- amount: Long
---

# 기능 api 목록

1. 내 예약 조회 GET("/reservations/{id}")
2. 내 예약 전체 조회 GET("/reservations")
3. 내 예약 삭제 GET("/reservations/{id}")
4. 내 예약 여권 번호 수정 POST("/reservations/{id}"), RequestBody = String passportId
5. 결제 승인 및 예약 POST("/reservations"), RequestBody = ReservationRequest

