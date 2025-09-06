insert into member(name, email, password)
values ('name', 'user@email.com','1234')
    ;

insert into cake (cake_id, name, description, price, is_available)
values (1L, '하트케이크', '하트 모양 케이크 입니다. 연인들에게 추천합니다', 15000, true),
       (2L, '캐릭터케이크', '애니메이션 캐릭터 케이크 입니다. 어린아이들에게 추천합니다', 30000, true),
       (3L, '2층케이크', '2층 케이크 입니다. 이벤트에 추천합니다', 55000, false),
       (4L, '떡케이크', '백설기로 만든 케이크입니다. 부모님 선물로 추천합니다.', 25000, true)
;

insert into flavor (flavor_id, name, description, additional_price)
values (1L, '기본맛', '기본 쉬폰 시트에 생크림 크림입니다.', 0),
       (2L, '오레오맛', '기본 쉬폰 시트에 오레오 크림입니다.', 2500),
       (3L, '초코맛', '초코 시트에 가나슈 크림입니다.', 5000),
       (4L, '딸기요거트맛', '기본 쉬폰 시트에 요거트 생크림과 딸기가 들어있습다.', 7500)
;

insert into size (size_id, dimension, description, additional_price)
values (1L, 10, '도시락 사이즈', 0),
       (2L, 12, '미니 사이즈', 3000),
       (3L, 15, '1호 사이즈', 10000),
       (4L, 18, '2호 사이즈', 15000)
;

insert into reservation_time(reservation_time_id, time)
values (1L, '10:00'),
       (2L, '11:00'),
       (3L, '12:00'),
       (4L, '13:00'),
       (5L, '14:00')
    ;

insert into reservation (date, time_id, member_id, cake_id, flavor_id, size_id, lettering)
values ('2025-06-21', 1L, 1L, 1L, 1L, 1L, '생일축하해')
;