-- noinspection SqlNoDataSourceInspectionForFile

BEGIN;

--CREATE SCHEMA public;
CREATE TABLE public.users(
                      user_id serial PRIMARY KEY,
                      first_name varchar(30) NOT NULL CHECK(LENGTH(first_name) > 0),
                      last_name varchar(30) NOT NULL CHECK(LENGTH(last_name) > 0),
                      username varchar(30) NOT NULL UNIQUE CHECK(LENGTH(username) > 0),
                      password varchar(30)  NOT NULL CHECK(LENGTH(password) > 0)
);
CREATE TYPE public.a_type AS ENUM ('CHECKING', 'SAVINGS');
CREATE TABLE public.accounts(
                         account_id serial PRIMARY KEY,
                         primary_owner_id int REFERENCES users(user_id),
                         joint_owner_id int REFERENCES users(user_id),
                         account_type a_type NOT NULL,
                         balance numeric NOT NULL,
                         approved boolean DEFAULT FALSE
);
CREATE TABLE public.employees(
                          fk_user_id int PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
                          authorized boolean NOT NULL
);
CREATE TYPE public.t_type AS ENUM ('WTIHDRAWAL', 'DEPOSIT', 'TRANSFER');
CREATE TYPE public.t_status AS ENUM ('PENDING', 'APPROVED', 'DENIED');
create table public.transactions(
                             transaction_id serial PRIMARY KEY,
                             transaction_type t_type NOT NULL,
                             origin_account_id int NOT NULL references accounts(account_id),
                             destination_account_id int NOT NULL references accounts(account_id),
                             transaction_amount numeric NOT NULL,
                             transaction_time timestamp NOT NULL DEFAULT current_timestamp,
                             status t_status NOT NULL
);
COMMIT;