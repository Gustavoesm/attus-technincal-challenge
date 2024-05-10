INSERT INTO person (id, first_name, last_name, birth_date) VALUES (100, 'Neven', 'Subotic', '1988-12-10');
INSERT INTO person (id, first_name, last_name, birth_date) VALUES (101, 'Marcel', 'Schmelzer', '1988-01-22');
INSERT INTO person (id, first_name, last_name, birth_date) VALUES (102, 'Ivan', 'Perisic', '1989-02-02');
INSERT INTO address (id, person_id, is_main_address, postal_code, state, city, street, number) VALUES (1000, 100, false, '31275-000', 'MINAS_GERAIS', 'Belo Horizonte', 'Av. Antônio Abrahão Caram', '1001');
INSERT INTO address (id, person_id, is_main_address, postal_code, state, city, street, number) VALUES (1001, 100, false, '40050-565', 'BAHIA', 'Salvador', 'Ladeira da Fonte das Pedras', 's/n');
INSERT INTO address (id, person_id, is_main_address, postal_code, state, city, street, number) VALUES (1002, 100, true, '90810-240', 'RIO_GRANDE_DO_SUL', 'Porto Alegre', 'Av. Padre Cacique', '891');
INSERT INTO address (id, person_id, is_main_address, postal_code, state, city, street, number) VALUES (1003, 101, true, '20271-150', 'RIO_DE_JANEIRO', 'Rio de Janeiro', 'R. Prof. Eurico Rabelo', 's/N');
