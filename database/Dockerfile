FROM postgres:16.2-alpine3.19

# Creates the directory for the certificates
RUN mkdir -p /certs/

# Set the pg_hba.conf file and add the permissions
RUN echo "hostnossl   all             all            all                 reject" > /certs/pg_hba.conf
RUN echo "hostssl     all             all            all                 scram-sha-256" >> /certs/pg_hba.conf
RUN echo "local       all             all                                scram-sha-256" >> /certs/pg_hba.conf
RUN echo "hostssl     all             all            127.0.0.1/32        scram-sha-256" >> /certs/pg_hba.conf
RUN echo "hostssl     all             all            ::1/128             scram-sha-256" >> /certs/pg_hba.conf
RUN chown postgres:postgres /certs/pg_hba.conf
RUN chmod 0600 /certs/pg_hba.conf

# Copy the pg_hba.conf file to the container
RUN echo "cp /certs/pg_hba.conf /var/lib/postgresql/data/pg_hba.conf" > /docker-entrypoint-initdb.d/01-pg_hba.sh
RUN chmod +x /docker-entrypoint-initdb.d/01-pg_hba.sh

# Reload the configuration
RUN echo "ALTER SYSTEM SET ssl = 'on';" > /docker-entrypoint-initdb.d/02-reload.sql
RUN echo "SELECT pg_reload_conf();" >> /docker-entrypoint-initdb.d/02-reload.sql

# Enable the pgcrypto extension
RUN echo "CREATE EXTENSION IF NOT EXISTS pgcrypto;" > /docker-entrypoint-initdb.d/03-pgcrypto.sql

# Copy the certificates to the container
RUN echo "cp /certs/server.crt /var/lib/postgresql/data/server.crt" > /docker-entrypoint-initdb.d/04-certificate.sh
RUN echo "cp /certs/server.key /var/lib/postgresql/data/server.key" >> /docker-entrypoint-initdb.d/04-certificate.sh
RUN echo "chown postgres:postgres /var/lib/postgresql/data/server.crt" >> /docker-entrypoint-initdb.d/04-certificate.sh
RUN echo "chown postgres:postgres /var/lib/postgresql/data/server.key" >> /docker-entrypoint-initdb.d/04-certificate.sh
RUN chmod +x /docker-entrypoint-initdb.d/04-certificate.sh

# Basic health check
HEALTHCHECK --interval=35s --timeout=4s CMD pg_isready -d db_prod

# Non-privileged user
USER postgres
