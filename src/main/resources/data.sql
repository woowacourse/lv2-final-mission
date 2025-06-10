INSERT INTO Member (name) VALUES ('test1');
INSERT INTO Member (name) VALUES ('test2');

INSERT INTO Album (name, artist_name, total_tracks, release_date, spotify_id)
VALUES ('Graduation', 'Kanye west', 14, '2007-12-04', 'WEQWFAFA');
INSERT INTO Album (name, artist_name, total_tracks, release_date, spotify_id)
VALUES ('DONDA', 'Kanye west', 21, '2012-12-04', 'WEQWF23112312AFA');

INSERT INTO Reservation (album_id, member_name, address) VALUES (1, 'test1', '서울시 관악구');
INSERT INTO Reservation (album_id, member_name, address) VALUES (2, 'test1', '서울시 관악구');
INSERT INTO Reservation (album_id, member_name, address) VALUES (2, 'test2', '서울시 송파구');

