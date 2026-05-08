-- Inserimento Utenti Obbligatori
INSERT INTO users (username, password, enabled) VALUES
    ('admin#team_20', '{noop}adm_id_20', true),
    ('basic#team_20', '{noop}bsc_id_20', true),
    ('pro#team_20', '{noop}pro_id_20', true),
    ('prova#1#team_20', '{noop}prv_id_20', true);

-- Assegnazione Ruoli
INSERT INTO authorities (username, authority) VALUES
    ('admin#team_20', 'ROLE_ADMIN'),
    ('basic#team_20', 'ROLE_USER_BASIC'),
    ('pro#team_20', 'ROLE_USER_PRO'),
    ('prova#1#team_20', 'ROLE_USER_PROVA');


-- @TODO criptare le password con BCrypt