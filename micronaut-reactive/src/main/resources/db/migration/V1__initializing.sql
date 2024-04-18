create schema if not exists secure;

create table if not exists secure.person (
	id SERIAL NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	gender VARCHAR(50) NOT NULL,
	CONSTRAINT person_email_key UNIQUE (email),
  CONSTRAINT person_pk PRIMARY KEY (id)
);