-- 영화 정보 추가

INSERT INTO movie(name, description)
VALUES ('드래곤 길들이기', '실사판 드래곤 길들이기'),
       ('승부', '바둑 영화');

-- 영화 슬롯 추가
INSERT INTO movie_slot(movie_id, date, start_at, seats)
VALUES (1, '2025-06-10', '10:00', 10),
       (2, '2025-06-10', '12:00', 1),
       (2, '2025-06-06', '12:00', 10);

