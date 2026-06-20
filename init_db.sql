CREATE DATABASE IF NOT EXISTS salle_sport_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE salle_sport_db;

CREATE TABLE IF NOT EXISTS membre (
                                      id               INT          NOT NULL AUTO_INCREMENT,
                                      nom              VARCHAR(50)  NOT NULL,
    prenom           VARCHAR(50)  NOT NULL,
    telephone        VARCHAR(20)  NOT NULL,
    email            VARCHAR(100) NOT NULL UNIQUE,
    date_inscription DATE         NOT NULL DEFAULT (CURRENT_DATE),
    etat             ENUM('Actif','Inactif') NOT NULL DEFAULT 'Actif',
    CONSTRAINT pk_membre PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS abonnement (
                                          id               INT          NOT NULL AUTO_INCREMENT,
                                          membre_id        INT          NOT NULL,
                                          type_abonnement  ENUM('Mensuel','Trimestriel','Annuel') NOT NULL,
    date_debut       DATE         NOT NULL,
    date_fin         DATE         NOT NULL,
    statut           ENUM('Actif','Expiré','Suspendu') NOT NULL DEFAULT 'Actif',
    prix             DECIMAL(8,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT pk_abonnement PRIMARY KEY (id),
    CONSTRAINT fk_membre FOREIGN KEY (membre_id)
    REFERENCES membre(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT chk_dates CHECK (date_fin > date_debut)
    ) ENGINE=InnoDB;

INSERT INTO membre (nom, prenom, telephone, email, date_inscription, etat) VALUES
                                                                               ('El Amrani',  'Youssef', '0661234567', 'youssef.elamrani@gmail.com', '2024-01-10', 'Actif'),
                                                                               ('Benali',     'Fatima',  '0672345678', 'fatima.benali@gmail.com',    '2024-02-15', 'Actif'),
                                                                               ('Cherkaoui',  'Omar',    '0683456789', 'omar.cherkaoui@gmail.com',   '2024-03-01', 'Actif'),
                                                                               ('Idrissi',    'Sanaa',   '0694567890', 'sanaa.idrissi@gmail.com',    '2024-03-20', 'Actif'),
                                                                               ('Moussaoui',  'Karim',   '0605678901', 'karim.moussaoui@gmail.com',  '2024-04-05', 'Inactif'),
                                                                               ('Lahlou',     'Nadia',   '0616789012', 'nadia.lahlou@gmail.com',     '2024-04-18', 'Actif'),
                                                                               ('Tazi',       'Amine',   '0627890123', 'amine.tazi@gmail.com',       '2024-05-02', 'Actif'),
                                                                               ('Bakkali',    'Houda',   '0638901234', 'houda.bakkali@gmail.com',    '2024-05-25', 'Actif'),
                                                                               ('Ouali',      'Rachid',  '0649012345', 'rachid.ouali@gmail.com',     '2024-06-10', 'Actif'),
                                                                               ('Hajji',      'Meriem',  '0650123456', 'meriem.hajji@gmail.com',     '2024-07-01', 'Inactif');

INSERT INTO abonnement (membre_id, type_abonnement, date_debut, date_fin, statut, prix) VALUES
                                                                                            (1,  'Mensuel',     '2025-06-01', '2025-06-30', 'Actif',    150.00),
                                                                                            (2,  'Annuel',      '2025-01-01', '2025-12-31', 'Actif',    1200.00),
                                                                                            (3,  'Trimestriel', '2024-09-01', '2024-11-30', 'Expiré',   400.00),
                                                                                            (4,  'Mensuel',     '2025-06-01', '2025-06-30', 'Actif',    150.00),
                                                                                            (5,  'Annuel',      '2025-03-01', '2026-02-28', 'Suspendu', 1200.00),
                                                                                            (6,  'Trimestriel', '2025-04-01', '2025-06-30', 'Actif',    400.00),
                                                                                            (7,  'Mensuel',     '2025-04-01', '2025-04-30', 'Expiré',   150.00),
                                                                                            (7,  'Mensuel',     '2025-06-01', '2025-06-30', 'Actif',    150.00),
                                                                                            (8,  'Annuel',      '2025-02-01', '2026-01-31', 'Actif',    1200.00),
                                                                                            (9,  'Trimestriel', '2025-05-01', '2025-07-31', 'Actif',    400.00),
                                                                                            (10, 'Mensuel',     '2025-05-01', '2025-05-31', 'Suspendu', 150.00);