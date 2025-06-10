# 회의실 예약 서비스 (찜꽁 ver2)

## 기능

### 예약 기능
- POST "/reservations"
- 사용자는 회의실을 예약할 수 있습니다.

### 예약 현황 조회
- GET "/reservations/search"
- 모든 사용자는 예약 현황을 확인할 수 있습니다.
- 날짜마다 회의실별 예약 정보를 조회한다.

### 자신의 예약 조회
- GET "/reservations/mine"
- 사용자는 자신이 한 예약의 상세 정보를 확인할 수 있습니다.
    - 상세 정보 : 예약 설명 + 날짜 + 위치

### 예약 수정
- PUT "/reservations"
- 사용자는 본인의 예약을 수정할 수 있습니다.

### 예약 삭제
- DELETE "/reservations"
- 사용자는 본인의 예약을 삭제할 수 있습니다.

### 회원 가입
- POST "/members/signup"
- 사용자는 회원가입을 할 수 있습니다.
- 이름 추천 기능을 제공합니다. (외부 API : Randommer.io)

### 로그인
- GET "/members/login"
- 사용자는 로그인을 할 수 있습니다.
