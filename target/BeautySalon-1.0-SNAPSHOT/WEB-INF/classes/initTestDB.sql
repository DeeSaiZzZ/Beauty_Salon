DROP TABLE orders;
DROP TABLE employee;
DROP TABLE users;

CREATE TABLE employee
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    email         VARCHAR(55) UNIQUE NOT NULL,
    password      VARCHAR(30)        NOT NULL,
    firstName     VARCHAR(30)        NOT NULL,
    lastName      VARCHAR(30)        NOT NULL,
    rate          DOUBLE DEFAULT 0,
    role_id       INT    DEFAULT 2,
    speciality_id INT
);

CREATE TABLE users
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    email     VARCHAR(55) UNIQUE NOT NULL,
    password  VARCHAR(30)        NOT NULL,
    firstName VARCHAR(30)        NOT NULL,
    lastName  VARCHAR(30)        NOT NULL,
    role_id   INT DEFAULT 3
);

CREATE TABLE orders
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    employ_id     INT,
    service_id    INT,
    user_id       INT,
    date          DATE,
    time          TIME,
    status        CHAR(30) DEFAULT 'Free',
    complete_date DATE
);

ALTER TABLE orders
    ADD CONSTRAINT orders_employee_id_fk FOREIGN KEY (employ_id) REFERENCES employee (id);
ALTER TABLE orders
    ADD CONSTRAINT orders_service_id_fk FOREIGN KEY (service_id) REFERENCES testdb.servicelist (id);
ALTER TABLE orders
    ADD CONSTRAINT orders_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id);

/*Insert data in table employee*/
INSERT INTO employee (email, password, firstName, lastName, rate, speciality_id)
VALUES ('someUser1@bfactor.com', '1', 'test1', 'testF1', 5, 1);
INSERT INTO employee (email, password, firstName, lastName, rate, speciality_id)
VALUES ('someUser2@bfactor.com', '2', 'test2', 'testF2', 2.5, 2);
INSERT INTO employee (email, password, firstName, lastName, rate, speciality_id)
VALUES ('someUser3@bfactor.com', '3', 'test3', 'testF3', 0, 3);

/*Insert data in table users*/
INSERT INTO users (email, password, firstName, lastName)
VALUES ('forarizonaforum12345@gmail.com', '123321', 'Dojo', 'Lala la');
INSERT INTO users (email, password, firstName, lastName)
VALUES ('someUser@mail.com', '555', 'Test', 'Test');
INSERT INTO users (email, password, firstName, lastName)
VALUES ('test@gmail.com', '123321', 'Test1', 'Test1');
INSERT INTO users (email, password, firstName, lastName)
VALUES ('someUser1@mail.com', 'jonson222', 'Jonson', 'Kane');

/*Insert data in table orders*/
INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (1, 1, NULL, '2022-04-15', '13:00:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (3, 4, NULL, '2022-04-15', '17:00:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (1, 4, NULL, '2022-04-15', '17:00:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (3, 3, NULL, '2022-04-15', '17:00:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (2, 2, NULL, '2022-04-15', '19:30:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (1, 1, 3, '2022-04-15', '18:30:00', 'Complete', '2022-04-09');

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (1, 1, 4, '2022-04-15', '19:30:00', 'Complete', '2022-04-06');

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (2, 3, NULL, '2022-04-15', '19:30:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (2, 2, NULL, '2022-04-15', '21:00:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (2, 3, NULL, '2022-04-15', '21:00:00', 'Free', NULL);

INSERT INTO orders (employ_id, service_id, user_id, date, time, status, complete_date)
VALUES (3, 9, NULL, '2022-04-16', '17:00:00', 'Free', NULL);