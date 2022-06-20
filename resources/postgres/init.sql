BEGIN;
--DROP all current tables and types
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS a_type;
DROP TYPE IF EXISTS t_type;
DROP TYPE IF EXISTS t_status;

--CREATE new tables and types
CREATE TABLE users(
                      user_id serial PRIMARY KEY,
                      first_name varchar(30) NOT NULL CHECK(LENGTH(first_name) > 0),
                      last_name varchar(30) NOT NULL CHECK(LENGTH(last_name) > 0),
                      username varchar(30) NOT NULL UNIQUE CHECK(LENGTH(username) > 0),
                      password varchar(30)  NOT NULL CHECK(LENGTH(password) > 0)
);

CREATE TYPE a_type AS ENUM ('CHECKING', 'SAVINGS');
CREATE TABLE accounts(
                         account_id serial PRIMARY KEY,
                         primary_owner_id int REFERENCES users(user_id),
                         joint_owner_id int REFERENCES users(user_id),
                         account_type a_type NOT NULL,
                         balance numeric NOT NULL CHECK(balance >= 0), --Added CHECK
                         approved boolean DEFAULT FALSE
);

CREATE TABLE employees(
                          fk_user_id int PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
                          authorized boolean DEFAULT TRUE --Changed from NOT NULL to DEFAULT TRUE
);

CREATE TYPE t_type AS ENUM ('WITHDRAWAL', 'DEPOSIT', 'TRANSFER');
CREATE TYPE t_status AS ENUM ('PENDING', 'APPROVED', 'DENIED');
create table transactions(
                             transaction_id serial PRIMARY KEY,
                             transaction_type t_type NOT NULL,
                             origin_account_id int NOT NULL references accounts(account_id),
                             destination_account_id int NOT NULL references accounts(account_id),
                             transaction_amount numeric NOT NULL,
                             transaction_time timestamp NOT NULL DEFAULT current_timestamp,
                             status t_status NOT NULL
);

--INSERT dummy data
INSERT INTO users(first_name, last_name, username, "password")
VALUES	('Braylen', 'Strain', 'braylenstrain', 'tnnfebs'),
          ('Aaron', 'Schroeder', 'aaronschroeder', 'tnnfeas'),
          ('Jeremy', 'E', 'jeremye', 'tnnfeje'),
          ('Mike', 'Wazowski', 'oneeyedguy', 'imgreen'),
          ('Taylor', 'Smith', 'tsmith21', 'ytl87^I'),
          ('Sarah', 'Smith', 'sarah597', 'Il0v3nature');

INSERT INTO employees(fk_user_id)
VALUES	(1),
          (2),
          (3);

INSERT INTO accounts(primary_owner_id, joint_owner_id, account_type, balance, approved)
VALUES 	(4, 4, 'CHECKING', 45.23, true),
          (4, 4, 'SAVINGS', 12.36, true),
          (5, 6, 'CHECKING', 450, true),
          (5, 6, 'SAVINGS', 2347.59, true),
          (5, 5, 'CHECKING', 500, false),
          (6, 6, 'CHECKING', 500, false);

INSERT INTO transactions(transaction_type, origin_account_id, destination_account_id, transaction_amount, status)
VALUES 	('DEPOSIT', 1, 1, 40, 'APPROVED'),
          ('DEPOSIT', 4, 4, 200, 'APPROVED'),
          ('WITHDRAWAL', 4, 4, 467.78, 'APPROVED'),
          ('TRANSFER', 4, 3, 2000, 'DENIED'),
          ('TRANSFER', 3, 1, 25.22, 'PENDING');

COMMIT;
