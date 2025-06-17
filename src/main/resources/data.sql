INSERT INTO room (name, capacity) VALUES('Room A',10);
INSERT INTO room (name, capacity) VALUES('Room B',8);
INSERT INTO room (name, capacity) VALUES('Room C',6);

INSERT INTO member (name, email, password) VALUES('Brown','1@a.com','wooteco7');
INSERT INTO member (name, email, password) VALUES('Murphy','2@a.com','wooteco7');
INSERT INTO member (name, email, password) VALUES('Aina','3@a.com','wooteco7');

INSERT INTO reservation (date, time, description, room_id, member_id) VALUES('2025-07-01','10:00:00','회의',1,1);
INSERT INTO reservation (date, time, description, room_id, member_id) VALUES('2025-07-01','12:00:00','컨퍼런스콜',2,2);
INSERT INTO reservation (date, time, description, room_id, member_id) VALUES('2025-07-02','13:00:00','과제',3,3);
