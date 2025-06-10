INSERT INTO member(name, email, password, role)
VALUES ('flint', 'test@test.com', '1234', 'ADMIN');

INSERT INTO reservation_time(start_at)
VALUES ('10:00');

INSERT INTO meeting_room(name, describe, available_people_count)
VALUES ('회의실', '설명', 10);
