INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (1, 'ROLE_ADMIN'); -- 1

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (2, 'ROLE_USUARIO'); -- 2

-- password: admin
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, ativo, password)
    values(1, 'admin@gmail.com', 'Admin', 'Admin', null, null, null, null, 'S', '$2a$08$eApn9x3qPiwp6cBVRYaDXed3J/usFEkcZbuc3FDa74bKOpUzHR.S.');

insert into user_cargo (ID_USER, ID_CARGO)
    values (1, 1);

