INSERT INTO tb_product (name, hex_code, min_quantity, balance) VALUES ('Product test 1', '000000000001', 1, 1);
INSERT INTO tb_product (name, hex_code, min_quantity, balance) VALUES ('Product test 2', '000000000002', 1, 1);
INSERT INTO tb_product (name, hex_code, min_quantity, balance) VALUES ('Product test 3', '000000000003', 1, 1);
INSERT INTO tb_product (name, hex_code, min_quantity, balance) VALUES ('Product test 4', '000000000004', 1, 1);
INSERT INTO tb_product (name, hex_code, min_quantity, balance) VALUES ('Product test 5', '000000000005', 1, 1);
INSERT INTO tb_product (name, hex_code, min_quantity, balance) VALUES ('Product test 6', '000000000006', 1, 1);

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_MANAGER');

INSERT INTO tb_user (name, email, password, role_id) VALUES ('João', 'joao@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);
INSERT INTO tb_user (name, email, password, role_id) VALUES ('Maria', 'maria@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 2);