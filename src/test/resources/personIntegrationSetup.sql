INSERT INTO person (id, first_name, last_name, birth_date) VALUES (100, 'Pierre-Emerick', 'Aubameyang', '1989-06-18');
INSERT INTO person (id, first_name, last_name, birth_date) VALUES (101, 'Roman', 'Weidenfeller', '1980-08-06');
INSERT INTO person (id, first_name, last_name, birth_date) VALUES (102, 'Nuri', 'Sahin', '1988-09-05');
INSERT INTO person (id, first_name, last_name, birth_date) VALUES (103, 'Sven', 'Bender', '1989-04-27');
INSERT INTO person (id, first_name, last_name, birth_date) VALUES (104, 'Jakub', 'Blaszczykowski', '1985-12-14');
INSERT INTO address (id, person_id, is_main_address, postal_code, state, city, street, number) VALUES (1000, 100, false, '03034-070', 'SAO_PAULO', 'São Paulo', 'R. Comendador Nestor Pereira', '33');
INSERT INTO address (id, person_id, is_main_address, postal_code, state, city, street, number) VALUES (1001, 100, true, '21870-102', 'RIO_DE_JANEIRO', 'Rio de Janeiro', 'R. Sul América', '950');
INSERT INTO address (id, person_id, is_main_address, postal_code, state, city, street, number) VALUES (1002, 100, false, '89801-561', 'SANTA_CATARINA', 'Chapecó', 'R. Clevelândia', '656e');