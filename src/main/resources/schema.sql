-- Tabelle standard per Spring Security
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

-- Tabella per i dati anagrafici degli utenti
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

-- Tabella per programmi personalizzati (Solo ROLE_USER_PRO)
CREATE TABLE personal_programs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    nome_allenamento VARCHAR(100) NOT NULL,
    nome_esercizio VARCHAR(100) NOT NULL,
    numero_serie INT NOT NULL,
    numero_ripetizioni INT NOT NULL,
    kcal INT NOT NULL,
    completato_count INT DEFAULT 0,
    CONSTRAINT fk_programs_users FOREIGN KEY(username) REFERENCES users(username)
);

-- Tabella per le recensioni (AJAX)
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    testo_recensione TEXT NOT NULL,
    data_recensione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);