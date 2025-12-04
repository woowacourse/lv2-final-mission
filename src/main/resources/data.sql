-- 사용자 등록
INSERT INTO users (username)
VALUES ('사용자1'),
       ('사용자2');

-- 상품 카테고리 등록
INSERT INTO category (id, name)
VALUES (1, '사무 용품'),
       (2, '청소 용품'),
       (3, '사내 간식');

-- 카테고리 별 품목 등록
INSERT INTO product (name, price, category_id)
VALUES ('볼펜', '1000', 1), -- 사무 용품
       ('A4용지', '3500', 1), -- 사무 용품
       ('물티슈', '2000', 2), -- 청소 용품
       ('비닐장갑', '500', 2), -- 청소 용품
       ('녹차티백', '3000', 3), -- 사내 간식
       ('미니약과', '4500', 3); -- 사내 간식
