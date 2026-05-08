CREATE TABLE training_catalog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_programma VARCHAR(50) NOT NULL,
    nome_esercizio VARCHAR(100) NOT NULL,
    serie INT NOT NULL,
    ripetizioni INT NOT NULL,
    kcal INT NOT NULL
);

INSERT INTO training_catalog (tipo_programma, nome_esercizio, serie, ripetizioni, kcal) VALUES
    ('Full Body', 'Squat', 3, 12, 150),
    ('Push/Pull/Legs', 'Panca Piana', 4, 8, 120),
    ('Cardio', 'Corsa sul posto', 1, 20, 300),
    ('Strength', 'Stacco da terra', 5, 5, 200);