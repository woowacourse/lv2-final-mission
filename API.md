# REST API DOCS

## GYM

### 헬스장 등록
> POST /gyms

- REQUEST BODY
  - "name": 헬스장 이름 (3자 이상 10자 이하)
  - "street": 도로명 주소
  - "detail": 상세 주소
  
- RESPONSE
  - 201 CREATED

## Member

### 사용자 등록 (회원가입)
> POST /members

- REQUEST BODY
  - "id": 사용자 ID
  - "password": 비밀번호
  - "name": 사용자 이름 (2자 이상 5자 이하)

- RESPONSE
  - 201 CREATED
