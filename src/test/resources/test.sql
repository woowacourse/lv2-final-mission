INSERT INTO customer (name, password, email)
VALUES ('엠제이', '1234', 'mj043000@naver.com');

-- Restaurant
INSERT INTO restaurant (name, region)
VALUES ('김밥천국', 'SEOUL');
INSERT INTO restaurant (name, region)
VALUES ('한우명가', 'INCHEON');
INSERT INTO restaurant (name, region)
VALUES ('해물파전집', 'BUSAN');

-- Menu 더미
INSERT INTO menu (name, restaurant_id)
VALUES ('참치김밥', 1);
INSERT INTO menu (name, restaurant_id)
VALUES ('소고기국밥', 2);
INSERT INTO menu (name, restaurant_id)
VALUES ('해물파전', 3);
