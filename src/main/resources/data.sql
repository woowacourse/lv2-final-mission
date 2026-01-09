INSERT INTO member(email, password) values ('123@gmail.com', '123');
INSERT INTO member(email, password) values ('234@gmail.com', '123');

INSERT INTO reservation_time(start_at) values ('9:00');
INSERT INTO reservation_time(start_at) values ('10:00');
INSERT INTO reservation_time(start_at) values ('11:00');
INSERT INTO reservation_time(start_at) values ('12:00');
INSERT INTO reservation_time(start_at) values ('13:00');
INSERT INTO reservation_time(start_at) values ('14:00');
INSERT INTO reservation_time(start_at) values ('15:00');
INSERT INTO reservation_time(start_at) values ('16:00');
INSERT INTO reservation_time(start_at) values ('17:00');

INSERT INTO meeting_room(name) values ('어드레스룸');
INSERT INTO meeting_room(name) values ('코치룸');
INSERT INTO meeting_room(name) values ('나이스샷');

INSERT INTO reservation(member_id, meeting_room_id, time_id, date) values (1, 1, 1, '2025-06-10');
INSERT INTO reservation(member_id, meeting_room_id, time_id, date) values (1, 1, 2, '2025-06-10');
INSERT INTO reservation(member_id, meeting_room_id, time_id, date) values (1, 1, 3, '2025-06-10');
