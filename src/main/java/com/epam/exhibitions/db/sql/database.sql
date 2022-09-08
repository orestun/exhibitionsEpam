CREATE SCHEMA IF NOT EXISTS dbexhibitions;
#DROP SCHEMA dbexhibitions;

CREATE TABLE IF NOT EXISTS user(
    id_user_username VARCHAR(30) PRIMARY KEY UNIQUE,
    first_name VARCHAR(20),
    second_name VARCHAR(20),
    email VARCHAR(254),
    phone_number VARCHAR(12),
    country VARCHAR(100),
    password VARCHAR(30)
    );

CREATE TABLE IF NOT EXISTS exhibition(
    id_exhibition INT PRIMARY KEY UNIQUE auto_increment,
    name VARCHAR(200),
    theme VARCHAR(50),
    hall VARCHAR(100),
    date_from DATE,
    date_to DATE,
    working_time_from TIME,
    working_time_to TIME,
    price DOUBLE
    );

SELECT username FROM user WHERE user.username="svitusik";