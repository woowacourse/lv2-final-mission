INSERT INTO member (name, password, email, role)
VALUES ('엠제이', '1234', 'test1@email.com', 'MEMBER');

INSERT INTO reservation_time (start_at)
VALUES ('10:00');
INSERT INTO reservation_time (start_at)
VALUES ('10:15');
INSERT INTO reservation_time (start_at)
VALUES ('10:30');
INSERT INTO reservation_time (start_at)
VALUES ('10:45');

INSERT INTO reservation_time (start_at)
VALUES ('11:00');
INSERT INTO reservation_time (start_at)
VALUES ('11:15');
INSERT INTO reservation_time (start_at)
VALUES ('11:30');
INSERT INTO reservation_time (start_at)
VALUES ('11:45');

INSERT INTO reservation_time (start_at)
VALUES ('12:00');
INSERT INTO reservation_time (start_at)
VALUES ('12:15');
INSERT INTO reservation_time (start_at)
VALUES ('12:30');
INSERT INTO reservation_time (start_at)
VALUES ('12:45');

INSERT INTO reservation_time (start_at)
VALUES ('13:00');
INSERT INTO reservation_time (start_at)
VALUES ('13:15');
INSERT INTO reservation_time (start_at)
VALUES ('13:30');
INSERT INTO reservation_time (start_at)
VALUES ('13:45');

INSERT INTO reservation_time (start_at)
VALUES ('14:00');
INSERT INTO reservation_time (start_at)
VALUES ('14:15');
INSERT INTO reservation_time (start_at)
VALUES ('14:30');
INSERT INTO reservation_time (start_at)
VALUES ('14:45');

INSERT INTO reservation_time (start_at)
VALUES ('15:00');
INSERT INTO reservation_time (start_at)
VALUES ('15:15');
INSERT INTO reservation_time (start_at)
VALUES ('15:30');
INSERT INTO reservation_time (start_at)
VALUES ('15:45');

INSERT INTO reservation_time (start_at)
VALUES ('16:00');
INSERT INTO reservation_time (start_at)
VALUES ('16:15');
INSERT INTO reservation_time (start_at)
VALUES ('16:30');
INSERT INTO reservation_time (start_at)
VALUES ('16:45');

INSERT INTO reservation_time (start_at)
VALUES ('17:00');
INSERT INTO reservation_time (start_at)
VALUES ('17:15');
INSERT INTO reservation_time (start_at)
VALUES ('17:30');
INSERT INTO reservation_time (start_at)
VALUES ('17:45');

INSERT INTO stall (name)
VALUES ('명상의 방');
INSERT INTO stall (name)
VALUES ('소화의 방');

INSERT INTO reservation (date, time_id, stall_id, member_id, alias)
VALUES (DATEADD('DAY', 0, CURRENT_DATE), 1, 2, 1, 'Roy');
INSERT INTO reservation (date, time_id, stall_id, member_id, alias)
VALUES (DATEADD('DAY', 0, CURRENT_DATE), 2, 2, 1, 'Thomas');
INSERT INTO reservation (date, time_id, stall_id, member_id, alias)
VALUES (DATEADD('DAY', 0, CURRENT_DATE), 3, 2, 1, 'Ben');
INSERT INTO reservation (date, time_id, stall_id, member_id, alias)
VALUES (DATEADD('DAY', 0, CURRENT_DATE), 4, 2, 1, 'Heather');
