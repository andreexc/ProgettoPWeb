CREATE TABLE program (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_programma INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE exercise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    esercizio VARCHAR(50),
    kcal INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE training_exercise (
    id_esercizio BIGINT NOT NULL,
    id_programma BIGINT NOT NULL,
    n_serie INT,
    n_ripetizioni INT,
    PRIMARY KEY (id_esercizio, id_programma),
    FOREIGN KEY (id_esercizio) REFERENCES exercise(id),
    FOREIGN KEY (id_programma) REFERENCES program(id)
) ENGINE=InnoDB;