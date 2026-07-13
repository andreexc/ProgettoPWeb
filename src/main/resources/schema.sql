-- DROP TABLE IF EXISTS reviews;
-- DROP TABLE IF EXISTS personal_programs;
-- DROP TABLE IF EXISTS exercise;
-- DROP TABLE IF EXISTS user_details;
-- DROP TABLE IF EXISTS authorities;
-- DROP TABLE IF EXISTS users;

-- Standard Spring-Security tables
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
     CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);


-- User infos table
CREATE TABLE IF NOT EXISTS user_details (
    username VARCHAR(50) PRIMARY KEY, -- not an id as primary for joins
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    data_iscrizione DATE NOT NULL,
    CONSTRAINT fk_details_users FOREIGN KEY(username) REFERENCES users(username)
    );


CREATE TABLE IF NOT EXISTS personal_programs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_utente BIGINT NOT NULL,
    id_programma BIGINT NOT NULL UNIQUE,
    nome_allenamento VARCHAR(100) NOT NULL,
    CONSTRAINT fk_programs_users FOREIGN KEY(id_utente) REFERENCES users(id)
);

-- Log table for completed excercises
CREATE TABLE IF NOT EXISTS completed (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_utente BIGINT NOT NULL,
    id_programma BIGINT NOT NULL,
    data DATE NOT NULL,
    CONSTRAINT fk_completed_user FOREIGN KEY(id_utente) REFERENCES users(id)
);

-- Review table
CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_user BIGINT NOT NULL,
    testo_recensione TEXT NOT NULL,
    data_recensione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_users FOREIGN KEY(id_user) REFERENCES users(id)
);