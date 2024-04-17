insert into secure.person (first_name, last_name, email, gender) values ('Ulrich', 'Setterfield', 'usetterfield0@scientificamerican.com', 'Male') on conflict (email) do nothing;

insert into secure.person (first_name, last_name, email, gender) values ('Kira', 'Bagnal', 'kbagnal1@opensource.org', 'Female') on conflict (email) do nothing;

insert into secure.person (first_name, last_name, email, gender) values ('Jesus', 'Sabate', 'jsabate2@loc.gov', 'Male') on conflict (email) do nothing;

insert into secure.person (first_name, last_name, email, gender) values ('Tyrone', 'Wragge', 'twragge3@aboutads.info', 'Male') on conflict (email) do nothing;

insert into secure.person (first_name, last_name, email, gender) values ('Imogene', 'Simmill', 'isimmill4@e-recht24.de', 'Female') on conflict (email) do nothing;