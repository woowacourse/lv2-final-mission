INSERT INTO CREW(name, email) VALUES('젠슨','spqjekdl1004@naver.com');
INSERT INTO CREW(name, email) VALUES('포포','b@com');
INSERT INTO CREW(name, email) VALUES('가이온','c@com');
INSERT INTO CREW(name, email) VALUES('짱구','d@com');
INSERT INTO CREW(name, email) VALUES('돔푸','e@com');

INSERT INTO COACH(name, email) VALUES('솔라','a1@com');
INSERT INTO COACH(name, email) VALUES('리사','a2@com');
INSERT INTO COACH(name, email) VALUES('네오','spqjekdl1004@naver.com');

INSERT INTO RESERVATION_TIME(start_at) VALUES('10:00');
INSERT INTO RESERVATION_TIME(start_at) VALUES('11:00');
INSERT INTO RESERVATION_TIME(start_at) VALUES('12:00');
INSERT INTO RESERVATION_TIME(start_at) VALUES('13:00');
INSERT INTO RESERVATION_TIME(start_at) VALUES('14:00');

INSERT INTO RESERVATION(coach_id, crew_id, reservation_time, date)
VALUES(1L, 1L, 1L, '2030-06-10');
INSERT INTO RESERVATION(coach_id, crew_id, reservation_time, date)
VALUES(1L, 2L, 1L, '2030-06-11');
INSERT INTO RESERVATION(coach_id, crew_id, reservation_time, date)
VALUES(1L, 3L, 1L, '2030-06-12')
