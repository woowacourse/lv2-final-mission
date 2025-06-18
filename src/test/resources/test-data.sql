INSERT INTO member (id, email, name, password, member_role)
VALUES (1, 'owner1@test.com', '사장님1', 'password', 'MASTER'),
       (2, 'owner2@test.com', '사장님2', 'password', 'MASTER'),
       (3, 'owner3@test.com', '사장님3', 'password', 'MASTER'),
       (4, 'customer1@test.com', '고객1', 'password', 'CUSTOMER'),
       (5, 'customer2@test.com', '고객2', 'password', 'CUSTOMER'),
       (6, 'customer3@test.com', '고객3', 'password', 'CUSTOMER'),
       (7, 'customer4@test.com', '고객4', 'password', 'CUSTOMER');

INSERT INTO store (id, store_name, store_status, description, star_rating, member_id)
VALUES (1, '맛있는 식당', 'OPEN', '24시간 영업하는 맛집입니다.', 4.5, 1),
       (2, '분위기 좋은 카페', 'OPEN', '아늑한 분위기의 카페입니다.', 4.8, 2);

INSERT INTO waiting_line (id, store_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO waiting_member (id, created_at, member_id, waiting_line_id)
VALUES (1, CURRENT_TIMESTAMP, 4, 1),
       (2, CURRENT_TIMESTAMP, 5, 1),
       (3, CURRENT_TIMESTAMP, 6, 2);

ALTER TABLE member
    ALTER COLUMN id RESTART WITH 8;
ALTER TABLE store
    ALTER COLUMN id RESTART WITH 3;
ALTER TABLE waiting_line
    ALTER COLUMN id RESTART WITH 3;
ALTER TABLE waiting_member
    ALTER COLUMN id RESTART WITH 4;
