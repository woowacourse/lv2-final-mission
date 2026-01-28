INSERT INTO shop (name, type, detail)
VALUES ('칰칰폭폭 치킨집', '야식', '맛있어요. 살안찝니다.');
INSERT INTO shop (name, type, detail)
VALUES ('뿡뿡이가 좋아요 중국집', '중식', '사천짜장이 기가막혀요');
INSERT INTO shop (name, type, detail)
VALUES ('민수네 떡볶이', '분식', '컵떡 500원');

INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (1, 'MONDAY', '12:00');
INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (1, 'TUESDAY', '12:00');
INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (1, 'WEDNESDAY', '12:00');
INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (1, 'THURSDAY', '12:00');
INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (1, 'FRIDAY', '12:00');

INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (2, 'SATURDAY', '20:00');
INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (2, 'SATURDAY', '21:00');
INSERT INTO operating_hour (shop_id, day_of_week, time)
VALUES (2, 'SATURDAY', '22:00');

INSERT INTO users (name, email, password)
VALUES ('고영희', 'meow@example.com', '1234');
INSERT INTO users (name, email, password)
VALUES ('모코', 'moko@example.com', '1234');
INSERT INTO users (name, email, password)
VALUES ('메론', 'melon@example.com', '1234');

INSERT INTO owner (user_id, business_license_url, business_registration_number)
VALUES (1, 'license', '123-45-67890');
