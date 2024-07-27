ALTER TABLE products_carts RENAME product_cart;

ALTER TABLE carts DROP COLUMN quantity;

ALTER TABLE product_cart ADD COLUMN quantity INT NOT NULL AFTER product_id;

