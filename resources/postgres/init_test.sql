BEGIN;
--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.7

-- Started on 2022-06-18 12:18:18

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- TOC entry 3902 (class 1262 OID 14301)
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3903 (class 0 OID 0)
-- Dependencies: 3902
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3904 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 633 (class 1247 OID 16901)
-- Name: a_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.a_type AS ENUM (
    'CHECKING',
    'SAVINGS'
);


ALTER TYPE public.a_type OWNER TO postgres;

--
-- TOC entry 647 (class 1247 OID 16946)
-- Name: t_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.t_status AS ENUM (
    'PENDING',
    'APPROVED',
    'DENIED'
);


ALTER TYPE public.t_status OWNER TO postgres;

--
-- TOC entry 644 (class 1247 OID 16938)
-- Name: t_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.t_type AS ENUM (
    'WITHDRAWAL',
    'DEPOSIT',
    'TRANSFER'
);


ALTER TYPE public.t_type OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 16907)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    account_id integer NOT NULL,
    primary_owner_id integer,
    joint_owner_id integer,
    account_type public.a_type NOT NULL,
    balance numeric NOT NULL,
    approved boolean DEFAULT false
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16905)
-- Name: accounts_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.accounts_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.accounts_account_id_seq OWNER TO postgres;

--
-- TOC entry 3906 (class 0 OID 0)
-- Dependencies: 202
-- Name: accounts_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.accounts_account_id_seq OWNED BY public.accounts.account_id;


--
-- TOC entry 204 (class 1259 OID 16927)
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    fk_user_id integer NOT NULL,
    authorized boolean NOT NULL
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16955)
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transactions (
    transaction_id integer NOT NULL,
    transaction_type public.t_type NOT NULL,
    origin_account_id integer NOT NULL,
    destination_account_id integer NOT NULL,
    transaction_amount numeric NOT NULL,
    transaction_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status public.t_status NOT NULL
);


ALTER TABLE public.transactions OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16953)
-- Name: transactions_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transactions_transaction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transactions_transaction_id_seq OWNER TO postgres;

--
-- TOC entry 3907 (class 0 OID 0)
-- Dependencies: 205
-- Name: transactions_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transactions_transaction_id_seq OWNED BY public.transactions.transaction_id;


--
-- TOC entry 201 (class 1259 OID 16888)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    username character varying(30) NOT NULL,
    password character varying(30) NOT NULL,
    CONSTRAINT users_first_name_check CHECK ((length((first_name)::text) > 0)),
    CONSTRAINT users_last_name_check CHECK ((length((last_name)::text) > 0)),
    CONSTRAINT users_password_check CHECK ((length((password)::text) > 0)),
    CONSTRAINT users_username_check CHECK ((length((username)::text) > 0))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16886)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3908 (class 0 OID 0)
-- Dependencies: 200
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 3741 (class 2604 OID 16910)
-- Name: accounts account_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts ALTER COLUMN account_id SET DEFAULT nextval('public.accounts_account_id_seq'::regclass);


--
-- TOC entry 3743 (class 2604 OID 16958)
-- Name: transactions transaction_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions ALTER COLUMN transaction_id SET DEFAULT nextval('public.transactions_transaction_id_seq'::regclass);


--
-- TOC entry 3736 (class 2604 OID 16891)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3893 (class 0 OID 16907)
-- Dependencies: 203
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.accounts VALUES (3, 4, 4, 'CHECKING', 777.7, false);
INSERT INTO public.accounts VALUES (6, 7, 7, 'CHECKING', 696969420, false);
INSERT INTO public.accounts VALUES (14, 7, 7, 'CHECKING', 888, true);
INSERT INTO public.accounts VALUES (5, 6, 6, 'CHECKING', 1099.99, true);
INSERT INTO public.accounts VALUES (4, 5, 5, 'CHECKING', 866.6, true);
INSERT INTO public.accounts VALUES (19, 7, 7, 'SAVINGS', 1500.72, false);
INSERT INTO public.accounts VALUES (7, 7, 7, 'SAVINGS', 400, true);


--
-- TOC entry 3894 (class 0 OID 16927)
-- Dependencies: 204
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.employees VALUES (6, true);


--
-- TOC entry 3896 (class 0 OID 16955)
-- Dependencies: 206
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: postgres
--

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


--
-- TOC entry 3891 (class 0 OID 16888)
-- Dependencies: 201
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (4, 'adam', 'garden', 'adamgarden', 'letmein');
INSERT INTO public.users VALUES (5, 'eve', 'garden', 'evegarden', 'letmeout');
INSERT INTO public.users VALUES (6, 'joe', 'pesci', 'vinny', 'yutes');
INSERT INTO public.users VALUES (7, 'Elon', 'Musk', 'ElonMusk', 'T3sla');


--
-- TOC entry 3909 (class 0 OID 0)
-- Dependencies: 202
-- Name: accounts_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accounts_account_id_seq', 19, true);


--
-- TOC entry 3910 (class 0 OID 0)
-- Dependencies: 205
-- Name: transactions_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transactions_transaction_id_seq', 17, true);


--
-- TOC entry 3911 (class 0 OID 0)
-- Dependencies: 200
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 7, true);


--
-- TOC entry 3750 (class 2606 OID 16916)
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (account_id);


--
-- TOC entry 3752 (class 2606 OID 16931)
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (fk_user_id);


--
-- TOC entry 3754 (class 2606 OID 16964)
-- Name: transactions transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (transaction_id);


--
-- TOC entry 3746 (class 2606 OID 16897)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3748 (class 2606 OID 16899)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 3756 (class 2606 OID 16922)
-- Name: accounts accounts_joint_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_joint_owner_id_fkey FOREIGN KEY (joint_owner_id) REFERENCES public.users(user_id);


--
-- TOC entry 3755 (class 2606 OID 16917)
-- Name: accounts accounts_primary_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_primary_owner_id_fkey FOREIGN KEY (primary_owner_id) REFERENCES public.users(user_id);


--
-- TOC entry 3757 (class 2606 OID 16932)
-- Name: employees employees_fk_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_fk_user_id_fkey FOREIGN KEY (fk_user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- TOC entry 3759 (class 2606 OID 16970)
-- Name: transactions transactions_destination_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_destination_account_id_fkey FOREIGN KEY (destination_account_id) REFERENCES public.accounts(account_id);


--
-- TOC entry 3758 (class 2606 OID 16965)
-- Name: transactions transactions_origin_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_origin_account_id_fkey FOREIGN KEY (origin_account_id) REFERENCES public.accounts(account_id);


--
-- TOC entry 3905 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM rdsadmin;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2022-06-18 12:18:21

--
-- PostgreSQL database dump complete
--
COMMIT;
