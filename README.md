# 운동 수업 예약 서비스

## API 명세

* 공통

- PREFIX URI = "/reservations"

1. 예약 등록

- POST

1.1 RequestBody

- 이름
- 전화번호
- 수업이름
- 예약날짜
- 예약시간

1.2 ResponseBody

- HttpStatus : 201 CREATED

- 이름
- 전화번호
- 수업이름
- 예약날짜
- 예약시간

---

2. 현재 예약 현황 조회

- GET
- /current-situations

2.1 ResponseBody
ReservationResponse List

- HttpStatus : 200 OK

- 이름
- 전화번호
- 수업이름
- 예약날짜
- 예약시간

---

3. 자신의 예약 정보 조회

- GET
- /mine/{id}

3.1 PathVariable

- member_id

3.2 ResponseBody
ReservationResponse List

- HttpStatus : 200 OK

- 이름
- 전화번호
- 수업이름
- 예약날짜
- 예약시간

---

4. 본인 예약 삭제

- DELETE
- /{id}

4.1 PathVariable

- reservation_id

4.2 ResponseBody

- HttpStatus : 204 NO_CONTENT

---

5. 본인 예약 수정

- PATCH
- "/update/{id}"

5.1 PathVariable

- reservation_id

5.2 RequestBody

- 예약날짜
- 예약시간

5.3 ResponseBody

- HttpStatus : 200 OK

- 이름
- 전화번호
- 수업이름
- 예약날짜
- 예약시간

---