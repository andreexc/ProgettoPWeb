-- Standard Spring-Security tables
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

-- User infos table
CREATE TABLE user_details (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    data_iscrizione DATE NOT NULL,
    piano_allenamento VARCHAR(20) NOT NULL,
    allenamenti_completati INT DEFAULT 0,
    CONSTRAINT fk_details_users FOREIGN KEY(username) REFERENCES users(username)
);

-- Exercise programs tables
CREATE TABLE exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_esercizio VARCHAR(100) NOT NULL,
    numero_serie INT NOT NULL,
    numero_ripetizioni INT NOT NULL,
    kcal INT NOT NULL
);

CREATE TABLE personal_programs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    nome_allenamento VARCHAR(100) NOT NULL,
    id_esercizio INT NOT NULL,
    kcal_totali INT NOT NULL,
    completato_count INT DEFAULT 0,
    CONSTRAINT fk_programs_users FOREIGN KEY(username) REFERENCES users(username),
    CONSTRAINT fk_programs_exercise FOREIGN KEY(id_esercizio) REFERENCES exercise(id)
);

-- Review table
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    testo_recensione TEXT NOT NULL,
    data_recensione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);