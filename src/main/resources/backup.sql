INSERT INTO restaurant(name, address, description)
VALUES ('식당1', '인계동', '아주 맛있는 식당');
INSERT INTO restaurant(name, address, description)
VALUES ('식당2', '미아동', '꽤 맛있는 식당');
INSERT INTO restaurant(name, address, description)
VALUES ('식당3', '명륜동', '끝내줘요.');

INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-21', '13:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-21', '14:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-21', '15:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-22', '13:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-22', '14:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-22', '15:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-23', '13:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-23', '14:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-23', '15:00', 20);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-23', '18:00', 30);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-23', '19:00', 30);
INSERT INTO schedule(restaurant_id, date, time, maximum_capacity)
VALUES (1, '2025-06-23', '20:00', 30);

INSERT INTO reservation(schedule_id, number_of_guest, message, nickname, email, password)
VALUES (1, 10, '10명 예약', '예약자1', 'norang@gamil.com', '1234');
INSERT INTO reservation(schedule_id, number_of_guest, message, nickname, email, password)
VALUES (1, 5, '5명 예약', '예약자2', 'norang@gamil.com', '1234');
