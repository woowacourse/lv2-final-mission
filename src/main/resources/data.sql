INSERT INTO Category (name, description)
VALUES ('소설', '소설 카테고리');

INSERT INTO Book (name, author, category_id, stock)
VALUES ('해리포터', 'J.K.롤링', 1, 5);

INSERT INTO Member (name, email, password, role)
VALUES ('관리자', 'admin@test.com', 'password', 'ADMIN');

INSERT INTO Rental (member_id, book_id, rental_date, return_date)
VALUES (1, 1, '2024-01-02', '2024-01-16');
