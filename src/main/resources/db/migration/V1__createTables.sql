CREATE TABLE book_genres(
   genre_id int primary key,
   type varchar(255) not null unique
)

CREATE TABLE book(
 book_id int PRIMARY KEY,
 book_name varchar(255) not null,
 book_quantity int not null default 1,
 book_genre int not null,
 book_availability bit not null default 1,
 borrow_times int default 0,
 FOREIGN KEY (book_genre) REFERENCES book_genres(genre_id)
)

CREATE TABLE role(
 role_id int PRIMARY KEY,
 role varchar(255) not null
)

CREATE TABLE users(
 users_id int PRIMARY KEY,
 user_name varchar(255) not null,
 user_password varchar(255) not null,
 role_id int not null,
 FOREIGN KEY (role_id) REFERENCES ROLE(role_id)
)

CREATE TABLE users_borrow(
   trx_Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT LOWER(CONVERT(NVARCHAR(36), NEWID())),
   start_date date not null,
   return_date date not null,
   users_id int not null,
   books_id int not null,
   accepted int not null default 0,
   returned int not null default 0,
   FOREIGN KEY (users_id) REFERENCES USERS(users_id),
   FOREIGN KEY (books_id) REFERENCES BOOK(book_id)
)


CREATE SEQUENCE role_sequence
    START WITH 1
    INCREMENT BY 1 ;

CREATE SEQUENCE users_sequence
    START WITH 1
    INCREMENT BY 1 ;

CREATE SEQUENCE book_sequence
    START WITH 1
    INCREMENT BY 1 ;

CREATE SEQUENCE genre_sequence
    START WITH 1
    INCREMENT BY 1 ;


INSERT INTO ROLE(role_id,role)
VALUES( NEXT VALUE FOR role_sequence,'admin')
INSERT INTO ROLE(role_id,role)
VALUES( NEXT VALUE FOR role_sequence,'customer')


INSERT INTO book_genres VALUES(next value for genre_sequence,'fiction')

INSERT INTO book_genres VALUES(next value for genre_sequence,'novel')

INSERT INTO book_genres VALUES(next value for genre_sequence,'mystery')


Insert into users VALUES(next value for  users_sequence,'mohamed_osama','Banquemisruser',2)

Insert into users VALUES(next value for  users_sequence,'admin','Banquemisr',1)

Insert into book VALUES(next value for book_sequence,'To Kill a Mockingbird',1,1,1,0)
Insert into book VALUES(next value for book_sequence,'The Great Gatsby',1,1,1,0)
Insert into book VALUES(next value for book_sequence,'The Lord of the Rings',1,1,1,0)

Insert into book VALUES(next value for book_sequence,'Gone girl',1,3,1,0)
Insert into book VALUES(next value for book_sequence,'Rebecca',1,3,1,0)
Insert into book VALUES(next value for book_sequence,'The Da Vinci Code',1,3,1,0)
