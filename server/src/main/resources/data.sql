INSERT INTO tb_product (name, hex_code, min_quantity, balance, created_at) VALUES ('Product test 1', '0000000000001', 1, 1, TIMESTAMP WITH TIME ZONE '2023-01-10T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, created_at) VALUES ('Product test 2', '0000000000002', 1, 1, TIMESTAMP WITH TIME ZONE '2023-01-15T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, created_at) VALUES ('Product test 3', '0000000000003', 1, 1, TIMESTAMP WITH TIME ZONE '2023-02-05T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, created_at) VALUES ('Product test 4', '0000000000004', 1, 1, TIMESTAMP WITH TIME ZONE '2023-02-08T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, created_at) VALUES ('Product test 5', '0000000000005', 1, 1, TIMESTAMP WITH TIME ZONE '2023-02-15T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, created_at) VALUES ('Product test 6', '0000000000006', 1, 1, TIMESTAMP WITH TIME ZONE '2023-02-21T00:00:00.00000Z');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_MANAGER');

INSERT INTO tb_user (name, email, password, role_id) VALUES ('Operador', 'operador@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);
INSERT INTO tb_user (name, email, password, role_id) VALUES ('Gerente', 'gerente@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 2);

INSERT INTO tb_type_movement (description, type, role_id) VALUES ('SALDO_INICIAL', 'E', 2);
INSERT INTO tb_type_movement (description, type, role_id) VALUES ('ENTRADA', 'E', 1);
INSERT INTO tb_type_movement (description, type, role_id) VALUES ('SAÍDA', 'S', 1);
INSERT INTO tb_type_movement (description, type, role_id) VALUES ('AJUSTE_ENTRADA', 'E', 2);
INSERT INTO tb_type_movement (description, type, role_id) VALUES ('AJUSTE_SAÍDA', 'S', 2);

INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, situation) VALUES (1, 1, 2, TIMESTAMP WITH TIME ZONE '2023-01-10T03:00:00.00000Z', 'Teste', 0, 1, 'Ok');
INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, situation) VALUES (2, 1, 2, TIMESTAMP WITH TIME ZONE '2023-01-15T03:00:00.00000Z', 'Teste', 0, 1, 'Ok');