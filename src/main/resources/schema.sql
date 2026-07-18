-- DROP TABLE IF EXISTS recensioni;
-- DROP TABLE IF EXISTS completed;
-- DROP TABLE IF EXISTS user_details;
-- DROP TABLE IF EXISTS authorities;
-- DROP TABLE IF EXISTS personal_programs;
-- DROP TABLE IF EXISTS exercise;
-- DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(50) NOT NULL UNIQUE,
   password VARCHAR(100) NOT NULL,
   enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     username VARCHAR(50) NOT NULL,
     authority VARCHAR(50) NOT NULL,
     CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE
);

-- User infos table
CREATE TABLE IF NOT EXISTS user_details (
    username VARCHAR(50) PRIMARY KEY, -- not an id as primary for joins
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    data_iscrizione DATE NOT NULL,
    CONSTRAINT fk_details_users FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE
    );

-- Log table for completed excercises
CREATE TABLE IF NOT EXISTS completed (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_utente BIGINT NOT NULL,
    id_programma BIGINT NOT NULL,
    data DATE NOT NULL,
    CONSTRAINT fk_completed_user FOREIGN KEY(id_utente) REFERENCES users(id) ON DELETE CASCADE
);

-- review tab
CREATE TABLE IF NOT EXISTS recensioni (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    testo VARCHAR(1000),
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_recensioni_users FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
    );