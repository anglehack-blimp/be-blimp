CREATE TABLE user
(
    username VARCHAR(20)                   NOT NULL,
    password VARCHAR(100)                  NOT NULL,
    status   ENUM ('ACTIVE', 'NON_ACTIVE') NOT NULL,
    PRIMARY KEY (username)
) ENGINE = InnoDB;

CREATE TABLE products
(
    id          BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    image       VARCHAR(255) NOT NULL,
    video       VARCHAR(255) NOT NULL,
    price       BIGINT       NOT NULL,
    quantity    INT          NOT NULL,
    username    VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_products_user FOREIGN KEY (username) REFERENCES user (username)
) ENGINE = InnoDB;

CREATE TABLE carts
(
    id         BIGINT                                   NOT NULL,
    quantity   INT                                      NOT NULL,
    created_at TIMESTAMP                                NOT NULL,
    status     ENUM ('PENDING', 'ON_PROCESS', 'FINISH') NOT NULL,
    product_id BIGINT                                   NOT NULL,
    username   VARCHAR(20)                              NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_carts_user FOREIGN KEY (username) REFERENCES user (username)
) ENGINE = InnoDB;


CREATE TABLE products_carts
(
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (cart_id, product_id),
    CONSTRAINT fk_products_carts_to_carts FOREIGN KEY (cart_id) REFERENCES carts (id),
    CONSTRAINT fk_products_carts_to_products FOREIGN KEY (product_id) REFERENCES products (id)
) ENGINE = InnoDb;