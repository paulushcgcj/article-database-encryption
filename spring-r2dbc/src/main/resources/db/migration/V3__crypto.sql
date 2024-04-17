-- Enable extension if is not already enabled
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Remove constraints to reuse on new table
ALTER TABLE secure.person DROP CONSTRAINT person_pk;
ALTER TABLE secure.person DROP CONSTRAINT person_email_key;

-- Create a new schema for the secure tables
create table if not exists secure.person_tmp (
    id SERIAL  NOT NULL,
    first_name BYTEA NOT NULL,
    last_name BYTEA NOT NULL,
    email BYTEA NOT NULL,
    gender BYTEA NOT NULL,
    CONSTRAINT person_email_key UNIQUE (email),
    CONSTRAINT person_pk PRIMARY KEY (id)
);

-- Migrate all the data
INSERT INTO secure.person_tmp (id, first_name, last_name, email, gender)
SELECT
	id,
	pgp_sym_encrypt(first_name::text, current_setting('cryptic.key'))::bytea AS first_name,
	pgp_sym_encrypt(last_name::text, current_setting('cryptic.key'))::bytea AS last_name,
	pgp_sym_encrypt(email::text, current_setting('cryptic.key'))::bytea AS email,
	pgp_sym_encrypt(gender::text, current_setting('cryptic.key'))::bytea AS gender
FROM
	secure.person
ON CONFLICT (ID) DO NOTHING;

-- Function to check the migration
CREATE OR REPLACE FUNCTION compare_and_replace_table()
RETURNS VOID AS
$$
DECLARE
	all_columns_match BOOLEAN;
BEGIN

	SELECT
		CASE
			WHEN
				(p.first_name = pgp_sym_decrypt(pe.first_name, current_setting('cryptic.key'))::text) AND
				(p.last_name = pgp_sym_decrypt(pe.last_name, current_setting('cryptic.key'))::text) AND
				(p.email = pgp_sym_decrypt(pe.email, current_setting('cryptic.key'))::text) AND
				(p.gender = pgp_sym_decrypt(pe.gender, current_setting('cryptic.key'))::text)
			THEN
				TRUE
			ELSE
				FALSE
		END AS all_columns_match
	INTO
		all_columns_match
	FROM
		secure.person p
	JOIN
		secure.person_tmp pe ON p.id = pe.id
	limit 2;

	 IF all_columns_match THEN
		DROP TABLE secure.person;
		ALTER TABLE secure.person_tmp RENAME TO person;
	END IF;
END;
$$
LANGUAGE plpgsql;

-- Execute the migration function
select compare_and_replace_table();

-- Drop the function as we don't need it anymore
DROP FUNCTION compare_and_replace_table();
