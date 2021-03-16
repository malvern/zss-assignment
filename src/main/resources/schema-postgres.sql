DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS category;

CREATE TABLE book
(
    id          serial PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    categoryId  INTEGER,
    price       Numeric
);
CREATE TABLE category
(
    id    serial PRIMARY KEY,
    title VARCHAR(255)
);