INSERT INTO Member (name, role) VALUES ('user', 'USER');
INSERT INTO Member (name, role) VALUES ('admin', 'ADMIN');

INSERT INTO Album (name, artist_name, total_tracks, release_date, spotify_id)
VALUES ('Graduation', 'Kanye west', 14, '2007-12-04', 'WEQWFAFA');
INSERT INTO Album (name, artist_name, total_tracks, release_date, spotify_id)
VALUES ('DONDA', 'Kanye west', 21, '2012-12-04', 'WEQWF23112312AFA');

INSERT INTO Lottery (album_id, expiration_date_time) VALUES (1, '2025-06-17');
INSERT INTO Lottery (album_id, expiration_date_time) VALUES (2, '2025-06-14');

INSERT INTO Reservation (lottery_id, member_name, address) VALUES (1, 'user', '서울시 관악구');
INSERT INTO Reservation (lottery_id, member_name, address) VALUES (2, 'user', '서울시 관악구');
INSERT INTO Reservation (lottery_id, member_name, address) VALUES (2, 'admin', '서울시 송파구');

