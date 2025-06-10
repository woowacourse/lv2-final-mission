INSERT INTO reservation_time(start_at)
VALUES ('10:00');
INSERT INTO reservation_time(start_at)
VALUES ('11:00');
INSERT INTO reservation_time(start_at)
VALUES ('12:00');

INSERT INTO member(name, email, password, role)
VALUES ('admin', 'wooteco@gmail.com', '1234', 'admin');
INSERT INTO member(name, email, password, role)
VALUES ('riwon', 'riwon@gmail.com', '5678', 'user');

INSERT INTO reservation(name, date, time_id, member_id)
VALUES ('admin', '2025-05-25', 1, 1);
INSERT INTO reservation(name, date, time_id, member_id)
VALUES ('riwon', '2025-05-25', 2, 2);
