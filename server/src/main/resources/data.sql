INSERT INTO tb_product (name, hex_code, min_quantity, balance, current_balance, created_at) VALUES ('Smartphone Galaxy S20 Samsung', '0000000000001', 1, 1, 1, TIMESTAMP WITH TIME ZONE '2023-01-10T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, current_balance, created_at) VALUES ('SmartTV 50" 4K 50PUG7406/78 Philips', '0000000000002', 1, 1, 1, TIMESTAMP WITH TIME ZONE '2023-01-15T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, current_balance, created_at) VALUES ('Refrigerador 384L RT38K5530S8/AZ Samsung', '0000000000003', 1, 1, 1, TIMESTAMP WITH TIME ZONE '2023-02-05T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, current_balance, created_at) VALUES ('Sofa 3/2 Lugares Marrocos Master 2077 Primor', '0000000000004', 1, 1, 1, TIMESTAMP WITH TIME ZONE '2023-03-07T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, current_balance, created_at) VALUES ('Fogão 5B Supreme Glass 570 Dako', '0000000000005', 1, 1, 1, TIMESTAMP WITH TIME ZONE '2023-03-12T00:00:00.00000Z');
INSERT INTO tb_product (name, hex_code, min_quantity, balance, current_balance, created_at) VALUES ('Sofa 3/2 Lugares Marrocos Master 2077 Primor', '0000000000006', 1, 1, 1, TIMESTAMP WITH TIME ZONE '2023-03-17T00:00:00.00000Z');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_MANAGER');

INSERT INTO tb_user (name, email, password, role_id) VALUES ('Operador', 'operador@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);
INSERT INTO tb_user (name, email, password, role_id) VALUES ('Gerente', 'gerente@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 2);

INSERT INTO tb_type_movement (description, name, type, role_id) VALUES ('SALDO_INICIAL', 'Saldo Inicial', 'E', 2);
INSERT INTO tb_type_movement (description, name, type, role_id) VALUES ('ENTRADA', 'Entrada', 'E', 1);
INSERT INTO tb_type_movement (description, name, type, role_id) VALUES ('SAIDA', 'Saida', 'S', 1);
INSERT INTO tb_type_movement (description, name, type, role_id) VALUES ('AJUSTE_ENTRADA', 'Ajuste de Entrada', 'E', 2);
INSERT INTO tb_type_movement (description, name, type, role_id) VALUES ('AJUSTE_SAÍDA', 'Ajuste de Saida', 'S', 2);

INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, current_balance, situation) VALUES (1, 1, 2, TIMESTAMP WITH TIME ZONE '2023-01-10T03:00:00.00000Z', 'Teste', 999, 1, 1, 'Ok');
INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, current_balance, situation) VALUES (2, 1, 2, TIMESTAMP WITH TIME ZONE '2023-01-15T03:00:00.00000Z', 'Teste', 999, 1, 1, 'Ok');
INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, current_balance, situation) VALUES (1, 2, 2, TIMESTAMP WITH TIME ZONE '2023-02-04T03:00:00.00000Z', 'Entrada de nota', 100, 100, 101, 'Ok');
INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, current_balance, situation) VALUES (1, 3, 2, TIMESTAMP WITH TIME ZONE '2023-02-10T03:00:00.00000Z', 'Venda', 105, 5, 96, 'Ok');
INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, current_balance, situation) VALUES (2, 4, 2, TIMESTAMP WITH TIME ZONE '2023-03-11T03:00:00.00000Z', 'Iventario', 999, 23, 24, 'Ok');
INSERT INTO tb_movement (product_id, type_movement_id, user_id, date, reason, document, quantity, current_balance, situation) VALUES (2, 5, 2, TIMESTAMP WITH TIME ZONE '2023-03-11T03:00:00.00000Z', 'Defeito', 999, 2, 22, 'Ok');