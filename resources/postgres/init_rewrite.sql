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
CREATE TYPE public.t_type AS ENUM ('WITHDRAWAL', 'DEPOSIT', 'TRANSFER');
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

INSERT INTO public.users VALUES (4, 'adam', 'garden', 'adamgarden', 'letmein');
INSERT INTO public.users VALUES (5, 'eve', 'garden', 'evegarden', 'letmeout');
INSERT INTO public.users VALUES (6, 'joe', 'pesci', 'vinny', 'yutes');
INSERT INTO public.users VALUES (7, 'Elon', 'Musk', 'ElonMusk', 'T3sla');

INSERT INTO public.accounts VALUES (3, 4, 4, 'CHECKING', 777.7, false);
INSERT INTO public.accounts VALUES (6, 7, 7, 'CHECKING', 696969420, false);
INSERT INTO public.accounts VALUES (14, 7, 7, 'CHECKING', 888, true);
INSERT INTO public.accounts VALUES (5, 6, 6, 'CHECKING', 1099.99, true);
INSERT INTO public.accounts VALUES (4, 5, 5, 'CHECKING', 866.6, true);
INSERT INTO public.accounts VALUES (19, 7, 7, 'SAVINGS', 1500.72, false);
INSERT INTO public.accounts VALUES (7, 7, 7, 'SAVINGS', 400, true);

INSERT INTO public.employees VALUES (6, true);

INSERT INTO public.transactions VALUES (2, 'TRANSFER', 4, 5, 0.77, '2022-06-14 13:50:19.97', 'APPROVED');
INSERT INTO public.transactions VALUES (3, 'DEPOSIT', 7, 7, 2, '2022-06-15 13:48:02.759', 'APPROVED');
INSERT INTO public.transactions VALUES (4, 'DEPOSIT', 7, 7, 5, '2022-06-15 13:49:27.482', 'APPROVED');
INSERT INTO public.transactions VALUES (5, 'DEPOSIT', 7, 7, 5, '2022-06-15 13:53:54.849', 'APPROVED');
INSERT INTO public.transactions VALUES (6, 'DEPOSIT', 7, 7, 5, '2022-06-15 13:56:58.447', 'APPROVED');
INSERT INTO public.transactions VALUES (7, 'DEPOSIT', 7, 7, 5, '2022-06-15 13:58:40.658', 'APPROVED');
INSERT INTO public.transactions VALUES (8, 'DEPOSIT', 7, 7, 5, '2022-06-15 14:10:01.554', 'APPROVED');
INSERT INTO public.transactions VALUES (9, 'DEPOSIT', 7, 7, 5, '2022-06-15 14:11:51.557', 'APPROVED');
INSERT INTO public.transactions VALUES (10, 'DEPOSIT', 7, 7, 10, '2022-06-15 14:21:02.825', 'APPROVED');
INSERT INTO public.transactions VALUES (11, 'DEPOSIT', 7, 7, 10, '2022-06-15 14:50:45.337', 'APPROVED');
INSERT INTO public.transactions VALUES (13, 'WITHDRAWAL', 7, 7, 50, '2022-06-15 15:00:34.753', 'APPROVED');
INSERT INTO public.transactions VALUES (14, 'TRANSFER', 7, 5, 100, '2022-06-15 15:02:34.446', 'APPROVED');
INSERT INTO public.transactions VALUES (15, 'TRANSFER', 7, 4, 200, '2022-06-15 15:35:27.974', 'DENIED');
INSERT INTO public.transactions VALUES (16, 'TRANSFER', 7, 4, 200, '2022-06-15 15:36:45.477', 'APPROVED');
INSERT INTO public.transactions VALUES (17, 'TRANSFER', 7, 14, 50, '2022-06-17 16:12:16.59', 'PENDING');

COMMIT;