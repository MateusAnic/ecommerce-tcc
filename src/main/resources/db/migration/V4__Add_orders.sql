INSERT INTO orders (id, id_user, date, total_price, status)
    VALUES (1, 1, '2021-04-07', 840, 1);
INSERT INTO orders (id, id_user, date, total_price, status)
    VALUES (2, 1, '2021-04-07', 240, 1);

INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (1, 2, 1, 1);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (3, 2, 1, 3);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (4, 1, 1, 4);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (5, 1, 1, 5);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (6, 1, 1, 6);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (7, 1, 1, 7);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (8, 2, 1, 8);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (10, 2, 1, 10);
INSERT INTO order_item (id, id_order, quantity, id_perfume) VALUES (11, 2, 1, 12);
