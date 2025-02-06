-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema proiect
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema proiect
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `proiect` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `proiect` ;

-- -----------------------------------------------------
-- Table `proiect`.`materii`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`materii` (
  `id_materii` INT NOT NULL,
  `nume_materii` VARCHAR(60) NULL DEFAULT NULL,
  PRIMARY KEY (`id_materii`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`rol` (
  `Rol_id` INT NOT NULL,
  `Rol_nume` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`Rol_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`utilizatori`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`utilizatori` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `CNP` VARCHAR(13) NULL DEFAULT NULL,
  `nume` VARCHAR(30) NULL DEFAULT NULL,
  `prenume` VARCHAR(30) NULL DEFAULT NULL,
  `NumarTelefon` CHAR(10) NULL DEFAULT NULL,
  `Adresa` VARCHAR(100) NULL DEFAULT NULL,
  `IBAN` CHAR(24) NULL DEFAULT NULL,
  `email` VARCHAR(30) NULL DEFAULT NULL,
  `Nr_contract` INT NULL DEFAULT NULL,
  `Rol_id` INT NULL DEFAULT NULL,
  `parola` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_rol` (`Rol_id` ASC) VISIBLE,
  CONSTRAINT `fk_rol`
    FOREIGN KEY (`Rol_id`)
    REFERENCES `proiect`.`rol` (`Rol_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 40
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`profesori`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`profesori` (
  `profesor_id_user` INT NULL DEFAULT NULL,
  `nr_max_ore` INT NULL DEFAULT NULL,
  `nr_min_ore` INT NULL DEFAULT NULL,
  `departament` VARCHAR(50) NULL DEFAULT NULL,
  `nr_max_studenti` INT NULL DEFAULT NULL,
  `id_profesor_materie` INT NULL DEFAULT NULL,
  `Numar_curent_studenti` INT NULL DEFAULT NULL,
  `nr_curent_ore` INT NULL DEFAULT NULL,
  `profesor_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`profesor_id`),
  UNIQUE INDEX `profesor_id_user` (`profesor_id_user` ASC) VISIBLE,
  INDEX `fk_id_materii_profesori` (`id_profesor_materie` ASC) VISIBLE,
  CONSTRAINT `fk_id_materii_profesori`
    FOREIGN KEY (`id_profesor_materie`)
    REFERENCES `proiect`.`materii` (`id_materii`),
  CONSTRAINT `fk_profesori`
    FOREIGN KEY (`profesor_id_user`)
    REFERENCES `proiect`.`utilizatori` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`activitati`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`activitati` (
  `id_activitati` INT NOT NULL AUTO_INCREMENT,
  `id_materii_activitati` INT NULL DEFAULT NULL,
  `Nr_participanti_activitate` INT NULL DEFAULT NULL,
  `procent` DOUBLE NULL DEFAULT NULL,
  `descriere` VARCHAR(30) NULL DEFAULT NULL,
  `ziua` VARCHAR(10) NULL DEFAULT NULL,
  `ora` TIME NULL DEFAULT NULL,
  `nr_max_participanti` INT NULL DEFAULT NULL,
  `id_profesor_activitate` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_activitati`),
  INDEX `fk_id_materii` (`id_materii_activitati` ASC) VISIBLE,
  INDEX `fk_activitate_profesor` (`id_profesor_activitate` ASC) VISIBLE,
  CONSTRAINT `fk_activitate_profesor`
    FOREIGN KEY (`id_profesor_activitate`)
    REFERENCES `proiect`.`profesori` (`profesor_id`),
  CONSTRAINT `fk_id_materii`
    FOREIGN KEY (`id_materii_activitati`)
    REFERENCES `proiect`.`materii` (`id_materii`))
ENGINE = InnoDB
AUTO_INCREMENT = 73
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`admins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`admins` (
  `admin_id_user` INT NULL DEFAULT NULL,
  `admin_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`admin_id`),
  UNIQUE INDEX `admin_id_user` (`admin_id_user` ASC) VISIBLE,
  CONSTRAINT `fk_admin`
    FOREIGN KEY (`admin_id_user`)
    REFERENCES `proiect`.`utilizatori` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`studenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`studenti` (
  `student_id_user` INT NULL DEFAULT NULL,
  `an_de_studiu` INT NULL DEFAULT NULL,
  `numar_ore_sustinute` INT NULL DEFAULT NULL,
  `student_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`student_id`),
  UNIQUE INDEX `student_id_user` (`student_id_user` ASC) VISIBLE,
  CONSTRAINT `fk_studenti`
    FOREIGN KEY (`student_id_user`)
    REFERENCES `proiect`.`utilizatori` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`calendar` (
  `activitate_id_calendar` INT NOT NULL,
  `student_id_calendar` INT NOT NULL,
  `nota_activitate` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`activitate_id_calendar`, `student_id_calendar`),
  INDEX `fk_calendar_student` (`student_id_calendar` ASC) VISIBLE,
  CONSTRAINT `calendar_ibfk_1`
    FOREIGN KEY (`activitate_id_calendar`)
    REFERENCES `proiect`.`activitati` (`id_activitati`),
  CONSTRAINT `fk_calendar_student`
    FOREIGN KEY (`student_id_calendar`)
    REFERENCES `proiect`.`studenti` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`catalog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`catalog` (
  `id_materie_catalog` INT NOT NULL,
  `id_student_catalog` INT NOT NULL,
  `nota` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_materie_catalog`, `id_student_catalog`),
  INDEX `id_student_catalog` (`id_student_catalog` ASC) VISIBLE,
  CONSTRAINT `catalog_ibfk_1`
    FOREIGN KEY (`id_materie_catalog`)
    REFERENCES `proiect`.`materii` (`id_materii`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`grup_studiu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`grup_studiu` (
  `id_grup` INT NOT NULL AUTO_INCREMENT,
  `id_materie` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_grup`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`grup_activitati`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`grup_activitati` (
  `id_activitate_grup` INT NOT NULL AUTO_INCREMENT,
  `id_grup` INT NULL DEFAULT NULL,
  `nr_curent_participanti` INT NULL DEFAULT NULL,
  `nr_min_participanti` INT NULL DEFAULT NULL,
  `ziua_calendaristica` DATE NULL DEFAULT NULL,
  `ora` TIME NULL DEFAULT NULL,
  `descriere` VARCHAR(50) NULL DEFAULT NULL,
  `durata` INT NULL DEFAULT NULL,
  `timp_expirare` INT NULL DEFAULT NULL,
  `status_activitate` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id_activitate_grup`),
  INDEX `fk_grup_activitati` (`id_grup` ASC) VISIBLE,
  CONSTRAINT `fk_grup_activitati`
    FOREIGN KEY (`id_grup`)
    REFERENCES `proiect`.`grup_studiu` (`id_grup`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`grup_activitati_studenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`grup_activitati_studenti` (
  `id_activitate_grup` INT NOT NULL,
  `id_student` INT NOT NULL,
  PRIMARY KEY (`id_activitate_grup`, `id_student`),
  INDEX `fk_grup_activitate_student` (`id_student` ASC) VISIBLE,
  CONSTRAINT `fk_grup_activitate`
    FOREIGN KEY (`id_activitate_grup`)
    REFERENCES `proiect`.`grup_activitati` (`id_activitate_grup`),
  CONSTRAINT `fk_grup_activitate_student`
    FOREIGN KEY (`id_student`)
    REFERENCES `proiect`.`studenti` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`grup_studiu_studenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`grup_studiu_studenti` (
  `id_grup` INT NOT NULL,
  `id_student` INT NOT NULL,
  PRIMARY KEY (`id_grup`, `id_student`),
  INDEX `fk_student` (`id_student` ASC) VISIBLE,
  CONSTRAINT `fk_grup_studiu_studenti`
    FOREIGN KEY (`id_grup`)
    REFERENCES `proiect`.`grup_studiu` (`id_grup`),
  CONSTRAINT `fk_student`
    FOREIGN KEY (`id_student`)
    REFERENCES `proiect`.`studenti` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`mesaje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`mesaje` (
  `id_mesaj` INT NOT NULL AUTO_INCREMENT,
  `id_grup_mesaj` INT NULL DEFAULT NULL,
  `student_id_mesaj` INT NULL DEFAULT NULL,
  `mesaj` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_mesaj`),
  INDEX `fk_mesaj_grup` (`id_grup_mesaj` ASC) VISIBLE,
  INDEX `fk_mesaj_grup_stud_studenti` (`student_id_mesaj` ASC) VISIBLE,
  CONSTRAINT `fk_mesaj_grup`
    FOREIGN KEY (`id_grup_mesaj`)
    REFERENCES `proiect`.`grup_studiu` (`id_grup`),
  CONSTRAINT `fk_mesaj_grup_stud_studenti`
    FOREIGN KEY (`student_id_mesaj`)
    REFERENCES `proiect`.`studenti` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `proiect`.`studenti_profesori`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`studenti_profesori` (
  `id_student` INT NOT NULL,
  `id_profesor` INT NOT NULL,
  PRIMARY KEY (`id_student`, `id_profesor`),
  INDEX `fk_catre_profesori` (`id_profesor` ASC) VISIBLE,
  CONSTRAINT `fk_catre_profesori`
    FOREIGN KEY (`id_profesor`)
    REFERENCES `proiect`.`profesori` (`profesor_id`),
  CONSTRAINT `fk_catre_studenti`
    FOREIGN KEY (`id_student`)
    REFERENCES `proiect`.`studenti` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `proiect` ;

-- -----------------------------------------------------
-- Placeholder table for view `proiect`.`view_profesor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`view_profesor` (`user_id` INT, `id_profesor_materie` INT, `CNP` INT, `nume` INT, `prenume` INT, `NumarTelefon` INT, `Adresa` INT, `IBAN` INT, `email` INT, `Nr_contract` INT, `profesor_id` INT, `nr_max_ore` INT, `nr_min_ore` INT, `departament` INT, `nr_max_studenti` INT);

-- -----------------------------------------------------
-- Placeholder table for view `proiect`.`view_studenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`view_studenti` (`user_id` INT, `student_id` INT, `CNP` INT, `nume` INT, `prenume` INT, `NumarTelefon` INT, `adresa` INT, `IBAN` INT, `email` INT, `Nr_contract` INT, `an_de_studiu` INT, `numar_ore_sustinute` INT);

-- -----------------------------------------------------
-- Placeholder table for view `proiect`.`view_studenti_note`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `proiect`.`view_studenti_note` (`student_id` INT, `nume` INT, `prenume` INT, `nota` INT);

-- -----------------------------------------------------
-- procedure AdaugaActivitateGrup
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AdaugaActivitateGrup`(
    IN grupID INT,
    IN nr_min_participanti INT,
    IN ziua_calendaristica DATE,
    IN ora TIME,
    IN descriere VARCHAR(50),
    IN durata INT,
    IN timp_expirare INT
)
BEGIN
    DECLARE activitateID INT;
 
    -- Verifică dacă activitatea este în viitor
    IF NOW() > TIMESTAMP(ziua_calendaristica, ora) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'O activitate poate fi programată doar în viitor!';
    ELSE
        -- 1. Inserează activitatea
        INSERT INTO grup_activitati(
            id_grup, nr_curent_participanti, nr_min_participanti,
            ziua_calendaristica, ora, descriere, durata, timp_expirare, status_activitate
        ) 
        VALUES (grupID, 0, nr_min_participanti, ziua_calendaristica, ora, descriere, durata, timp_expirare, 1);
 
        -- 2. Obține ID-ul activității
        SET activitateID = LAST_INSERT_ID();
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AdaugaAdmin
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AdaugaAdmin`(
	IN CNP VARCHAR(13), 
    IN nume VARCHAR(30), 
    IN prenume VARCHAR(30), 
    IN NumarTelefon CHAR(10), 
    IN Adresa VARCHAR(100), 
    IN IBAN CHAR(24), 
    IN email VARCHAR(30), 
    IN Nr_contract INT, 
    IN parola VARCHAR(30)
)
BEGIN
	DECLARE last_user_id INT;
    INSERT INTO utilizatori(CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, Rol_id, parola) VALUES
	(CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, 1, parola);
    SET last_user_id = LAST_INSERT_ID();
    INSERT INTO admins (admin_id_user) VALUES (last_user_id);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AdaugaProfesor
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AdaugaProfesor`(
	IN materie VARCHAR(60),
	IN CNP VARCHAR(13), 
    IN nume VARCHAR(30), 
    IN prenume VARCHAR(30), 
    IN NumarTelefon CHAR(10), 
    IN Adresa VARCHAR(100), 
    IN IBAN CHAR(24), 
    IN email VARCHAR(30), 
    IN Nr_contract INT, 
    IN parola VARCHAR(30),
	IN nr_max_ore INT,
	IN nr_min_ore INT,
    IN departament varchar(50),
    IN nr_max_studenti INT
)
BEGIN
	DECLARE last_user_id INT;
    DECLARE materieID INT;
    DECLARE exista_materia INT;
    SELECT COUNT(*) INTO exista_materia
    FROM materii
    WHERE nume_materii = materie;
    IF exista_materia > 0 THEN
		SELECT id_materii INTO materieID
		FROM materii
		WHERE nume_materii = materie;
 
		INSERT INTO utilizatori(CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, Rol_id, parola) VALUES
		(CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, 2, parola);
		SET last_user_id = LAST_INSERT_ID();
		INSERT INTO profesori(profesor_id_user, nr_max_ore, nr_min_ore, departament, nr_max_studenti, id_profesor_materie, 
		numar_curent_studenti, nr_curent_ore) VALUES
		(last_user_id, nr_max_ore, nr_min_ore, departament, nr_max_studenti, materieID, 0, 0);
	ELSE
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Nu exista aceasta materie!';
	END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AdaugaStudent
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AdaugaStudent`(
	IN CNP VARCHAR(13), 
    IN nume VARCHAR(30), 
    IN prenume VARCHAR(30), 
    IN NumarTelefon CHAR(10), 
    IN Adresa VARCHAR(100), 
    IN IBAN CHAR(24), 
    IN email VARCHAR(30), 
    IN Nr_contract INT, 
    IN parola VARCHAR(30),
    IN an_de_studiu INT, 
    IN numar_ore_sustinute INT
)
BEGIN
	DECLARE last_user_id INT;
 
	INSERT INTO utilizatori(CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, Rol_id, parola) VALUES
    (CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, 3, parola);
    SET last_user_id = LAST_INSERT_ID();
    INSERT INTO studenti (student_id_user, an_de_studiu, numar_ore_sustinute) VALUES
    (last_user_id, an_de_studiu, numar_ore_sustinute);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AfisareGrupuriDeStudiu
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AfisareGrupuriDeStudiu`(IN studentID int)
BEGIN
	SELECT gss.id_grup AS ID_grup_studiu, m.nume_materii AS Materie
    FROM grup_studiu_studenti gss
    JOIN grup_studiu gr ON gr.id_grup = gss.id_grup
    JOIN materii m ON gr.id_materie = m.id_materii
    WHERE gss.id_student = studentID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GetActivitatiStudent
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetActivitatiStudent`(
    IN ziua_saptamanii VARCHAR(10),
    IN id_student INT
)
BEGIN
    SELECT
		m.nume_materii AS Nume_Materie,
        a.descriere AS Descriere_Activitate,
        a.ora AS Ora_Activitatii
    FROM 
        calendar c
    JOIN 
        activitati a ON c.activitate_id_calendar = a.id_activitati
    JOIN 
        materii m ON a.id_materii_activitati = m.id_materii
    WHERE 
        c.student_id_calendar = id_student 
        AND a.ziua = ziua_saptamanii;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GetAdminData
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAdminData`(IN userId INT)
BEGIN
    SELECT 
        u.user_id AS id_user,
        u.nume AS Nume,
        u.prenume AS Prenume,
        u.CNP,
        u.NumarTelefon AS Numar_Telefon,
        u.adresa AS Adresa,
        u.IBAN,
        u.email AS Email,
        u.Nr_contract,
        r.Rol_nume AS Rol
    FROM utilizatori u
    JOIN rol r ON u.Rol_id = r.Rol_id
    WHERE u.user_id = userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GetMateriiProfesor
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetMateriiProfesor`(
    IN profesor_id INT
)
BEGIN
    -- Selectează materiile asociate profesorului
    SELECT m.id_materii
    FROM materii m
    JOIN activitati a ON m.id_materii = a.id_materii_activitati
    WHERE a.id_profesor_activitate = profesor_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GetProfesorData
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetProfesorData`(IN userId INT)
BEGIN
    SELECT 
        u.user_id AS id_user,
        u.nume AS Nume,
        u.prenume AS Prenume,
        u.CNP,
        u.NumarTelefon AS Numar_Telefon,
        u.adresa AS Adresa,
        u.IBAN,
        u.email AS Email,
        u.Nr_contract,
        m.nume_materii AS Materie_predata,
        p.departament AS Departament,
        p.nr_min_ore AS Nr_minim_ore,
        p.nr_max_ore AS Nr_maxim_ore,
        p.nr_curent_ore AS Nr_curent_ore,
        p.nr_max_studenti AS Nr_maxim_studenti,
        p.numar_curent_studenti AS Nr_curent_studenti
    FROM utilizatori u
    JOIN profesori p ON u.user_id = p.profesor_id_user
    JOIN materii m ON p.id_profesor_materie = m.id_materii
    WHERE u.user_id = userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GetStudentData
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetStudentData`(IN userId INT)
BEGIN
    SELECT 
        u.user_id,
        u.nume,
        u.prenume,
        u.CNP,
        u.NumarTelefon,
        u.adresa,
        u.IBAN,
        u.email,
        u.Nr_contract,
        s.an_de_studiu,
        s.student_id,
        s.numar_ore_sustinute
    FROM utilizatori u
    JOIN studenti s ON u.user_id = s.student_id_user
    WHERE u.user_id = userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GetStudentNote
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetStudentNote`(IN studentID INT)
BEGIN
    SELECT 
        m.nume_materii AS Materie,
        c.nota AS Nota
    FROM catalog c
    JOIN materii m ON c.id_materie_catalog = m.id_materii
    JOIN studenti s ON c.id_student_catalog = s.student_id
    WHERE s.student_id = studentID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure InscriereActivitate
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InscriereActivitate`(IN activitateID INT, IN studentID INT)
BEGIN
    DECLARE materieID INT;
    DECLARE activitateTip VARCHAR(30);
 
    -- Obține id-ul materiei și tipul activității curente
    SELECT id_materii_activitati, descriere
    INTO materieID, activitateTip
    FROM activitati
    WHERE id_activitati = activitateID;
 
    -- Verifică dacă studentul este deja înscris la același tip de activitate din aceeași materie
    IF EXISTS (
        SELECT 1
        FROM calendar c
        JOIN activitati a ON c.activitate_id_calendar = a.id_activitati
        WHERE c.student_id_calendar = studentID
          AND a.id_materii_activitati = materieID
          AND a.descriere = activitateTip
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Esti deja înscris la acest tip de activitate pentru aceasta materie.';
    ELSE
        -- Inscrie studentul în activitate
        INSERT INTO calendar (activitate_id_calendar, student_id_calendar)
        VALUES (activitateID, studentID);
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure InscriereActivitateGrup
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InscriereActivitateGrup`(IN studentID int, IN activitateID int)
BEGIN
    DECLARE zi_activitate DATE;
    DECLARE ora_activitate TIME;
    DECLARE status_activ BOOLEAN;
 
    -- Preia ziua calendaristică a activității
    SELECT ziua_calendaristica, ora, status_activitate INTO zi_activitate, ora_activitate, status_activ
    FROM grup_activitati 
    WHERE id_activitate_grup = activitateID;
 
    -- Verifică dacă `NOW()` este mai mic decât ziua activității
    IF NOW() < TIMESTAMP(zi_activitate, ora_activitate) AND status_activ = 1 THEN
        -- Inserare în tabel
        INSERT INTO grup_activitati_studenti(id_activitate_grup, id_student) 
        VALUES (activitateID, studentID);
    ELSE
        -- Aruncă o eroare
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Nu te poți înscrie la această activitate deoarece nu mai este valabila!';
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure InscriereGrupStudiu
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InscriereGrupStudiu`(IN studentID INT, IN materieID INT)
BEGIN
    DECLARE grupID INT;
 
    -- Obține id_grup asociat materiei
    SELECT id_grup
    INTO grupID
    FROM grup_studiu
    WHERE id_materie = materieID;
 
    -- Verifică dacă studentul este înscris la materia respectivă
    IF EXISTS (
        SELECT 1
        FROM catalog c
        WHERE c.id_student_catalog = studentID
          AND c.id_materie_catalog = materieID
    ) THEN
        -- Inscrie studentul în grupul de studiu
        INSERT INTO grup_studiu_studenti (id_grup, id_student)
        VALUES (grupID, studentID);
    ELSE
        -- Generează eroare dacă studentul nu este înscris la materie
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Nu esti inscris la materia asociata acestui grup de studiu!';
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Inscriere_Student_Activitate
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Inscriere_Student_Activitate`(
    IN p_student_id INT,         -- ID-ul studentului
    IN p_activitate_id INT       -- ID-ul activității
)
BEGIN
    DECLARE v_id_materie INT;
    DECLARE v_id_profesor INT;
    DECLARE v_student_inscris INT;
    DECLARE v_student_apartine_profesor INT;
    DECLARE v_nr_max_participanti INT;
    DECLARE v_nr_curent_participanti INT;

    -- 1. Obține ID-ul materiei asociate activității
    SELECT id_materii_activitati INTO v_id_materie
    FROM activitati
    WHERE id_activitati = p_activitate_id;

    IF v_id_materie IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Activitatea nu există!';
    END IF;

    -- 2. Verifică dacă studentul este înscris la materia respectivă
    SELECT COUNT(*) INTO v_student_inscris
    FROM catalog
    WHERE id_materie_catalog = v_id_materie AND id_student_catalog = p_student_id;

    IF v_student_inscris = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Studentul nu este înscris la materie!';
    END IF;

    -- 3. Obține ID-ul profesorului asociat activității
    SELECT id_profesor_activitate INTO v_id_profesor
    FROM activitati
    WHERE id_activitati = p_activitate_id;

    IF v_id_profesor IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Profesorul nu este asociat cu activitatea!';
    END IF;

    -- 4. Verifică dacă studentul aparține profesorului care susține activitatea
    SELECT COUNT(*) INTO v_student_apartine_profesor
    FROM studenti_profesori
    WHERE id_student = p_student_id AND id_profesor = v_id_profesor;

    IF v_student_apartine_profesor = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Studentul nu aparține profesorului care susține activitatea!';
    END IF;

    -- 5. Verifică numărul curent de participanți și numărul maxim permis
    SELECT Nr_participanti_activitate, nr_max_participanti
    INTO v_nr_curent_participanti, v_nr_max_participanti
    FROM activitati
    WHERE id_activitati = p_activitate_id;

    IF v_nr_curent_participanti >= v_nr_max_participanti THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Nu mai sunt locuri disponibile la această activitate!';
    END IF;

    -- 6. Actualizează numărul curent de participanți la activitate
    UPDATE activitati
    SET Nr_participanti_activitate = Nr_participanti_activitate + 1
    WHERE id_activitati = p_activitate_id;

    -- 7. Adaugă studentul în tabela calendar cu nota 0
    INSERT INTO calendar (student_id_calendar, activitate_id_calendar, nota_activitate)
    VALUES (p_student_id, p_activitate_id, 0);

    -- Mesaj final
    SELECT 'Studentul a fost înscris cu succes la activitate, iar nota inițială este 0!' AS rezultat;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProfesorProgramareActivitate
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ProfesorProgramareActivitate`(
	IN profID INT,
    IN var_descriere VARCHAR(30),
    IN var_ziua VARCHAR(10),
    IN var_ora TIME,
    IN var_nr_max_participanti INT
)
BEGIN
	DECLARE materieID int;
    DECLARE activitate_existenta INT DEFAULT 0;
	-- Selectăm materia asociată profesorului
	SELECT id_profesor_materie
    INTO materieID
	FROM profesori
	WHERE profesor_id = profID;
     -- Verificăm dacă există deja o activitate în aceeași zi și la aceeași oră pentru profesor
	SELECT COUNT(*)
    INTO activitate_existenta
    FROM activitati
    WHERE id_profesor_activitate = profID AND ziua = var_ziua AND ora = var_ora;
	-- Dacă există o astfel de activitate, generăm o eroare
    IF activitate_existenta > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Aveti deja o activitate programată la această zi și oră.';
    ELSE
        -- Dacă nu există conflicte, facem inserția
		INSERT INTO activitati (id_materii_activitati, nr_participanti_activitate, descriere, ziua, ora, nr_max_participanti, id_profesor_activitate, procent) VALUES
    (materieID, 0, var_descriere, var_ziua, var_ora, var_nr_max_participanti, profID, 0);
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SelectActivitatiProfesor
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelectActivitatiProfesor`(
    IN profID INT,
    IN materieID INT
)
BEGIN
    SELECT 
        a.id_activitati AS ActivitateID,
        a.descriere AS Descriere,
        a.ziua AS Ziua,
        a.ora AS Ora
    FROM activitati a
    WHERE a.id_profesor_activitate = profID
      AND a.id_materii_activitati = materieID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SelecteazaMesajeGrup
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelecteazaMesajeGrup`(
    IN grupID INT
)
BEGIN
    SELECT 
        u.nume AS NumeStudent,
        u.prenume AS PrenumeStudent,
        m.mesaj AS Mesaj
    FROM mesaje m
    JOIN studenti s ON m.student_id_mesaj = s.student_id
    JOIN utilizatori u ON s.student_id_user = u.user_id
    WHERE m.id_grup_mesaj = grupID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SelecteazaProfesor
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelecteazaProfesor`(
    IN materieID INT,
    IN studentID INT
)
BEGIN
    SELECT 
        p.profesor_id AS ProfesorID
    FROM studenti_profesori sp
    JOIN profesori p ON sp.id_profesor = p.profesor_id
    JOIN utilizatori u ON p.profesor_id_user = u.user_id
    WHERE sp.id_student = studentID
      AND p.id_profesor_materie = materieID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SelecteazaStudentiProfesor
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelecteazaStudentiProfesor`(
    IN profesor_id INT
)
BEGIN
    SELECT 
        s.student_id,
        u.nume AS Nume,
        u.prenume AS Prenume,
        MAX(CASE WHEN a.descriere = 'Seminar' THEN c.nota_activitate ELSE NULL END) AS Nota_Seminar,
        MAX(CASE WHEN a.descriere = 'Laborator' THEN c.nota_activitate ELSE NULL END) AS Nota_Laborator,
        MAX(CASE WHEN a.descriere = 'Curs' THEN c.nota_activitate ELSE NULL END) AS Nota_Curs
    FROM 
        studenti s
    JOIN 
        utilizatori u ON s.student_id_user = u.user_id
    JOIN 
        studenti_profesori sp ON s.student_id = sp.id_student
    JOIN 
        calendar c ON s.student_id = c.student_id_calendar
    JOIN 
        activitati a ON c.activitate_id_calendar = a.id_activitati
    WHERE 
        sp.id_profesor = profesor_id
        AND a.id_profesor_activitate = profesor_id -- Filtrare pentru activitățile profesorului
    GROUP BY 
        s.student_id, u.nume, u.prenume;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SetareNoteStudent
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SetareNoteStudent`(
    IN nota_seminar DOUBLE,
    IN nota_curs DOUBLE,
    IN nota_lab DOUBLE,
    IN profID INT,
    IN studentID INT,
    IN id_materie INT
)
BEGIN
    -- Actualizează notele activităților în tabela 'calendar'
    UPDATE calendar c
    JOIN activitati a ON c.activitate_id_calendar = a.id_activitati
    SET c.nota_activitate = CASE
        WHEN LOWER(a.descriere) LIKE '%seminar%' THEN nota_seminar
        WHEN LOWER(a.descriere) LIKE '%curs%' THEN nota_curs
        WHEN LOWER(a.descriere) LIKE '%laborator%' THEN nota_lab
        ELSE c.nota_activitate -- Păstrează valoarea existentă dacă nu există o descriere relevantă
    END
    WHERE c.student_id_calendar = studentID
    AND a.id_materii_activitati = id_materie
    AND a.id_profesor_activitate = profID;
 
    -- Calculează și actualizează nota finală în tabela 'catalog'
    UPDATE catalog c
    JOIN studenti s ON c.id_student_catalog = s.student_id
    SET c.nota = ROUND((
        SELECT SUM(cal.nota_activitate * (IFNULL(a.procent, 0) / 100))
        FROM calendar cal
        JOIN activitati a ON cal.activitate_id_calendar = a.id_activitati
        WHERE cal.student_id_calendar = studentID
        AND a.id_materii_activitati = id_materie
        AND a.id_profesor_activitate = profID
    ), 2)
    WHERE c.id_student_catalog = studentID
    AND c.id_materie_catalog = id_materie;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure SeteazaProcenteActivitatiProfesor
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SeteazaProcenteActivitatiProfesor`(
    IN p_profesor_id INT,
    IN p_procent_curs DOUBLE,
    IN p_procent_seminar DOUBLE,
    IN p_procent_laborator DOUBLE
)
BEGIN
    -- Setează procentul pentru toate cursurile profesorului
    UPDATE activitati
    SET procent = p_procent_curs
    WHERE id_profesor_activitate = p_profesor_id
      AND descriere = 'curs';

    -- Setează procentul pentru toate seminariile profesorului
    UPDATE activitati
    SET procent = p_procent_seminar
    WHERE id_profesor_activitate = p_profesor_id
      AND descriere = 'seminar';

    -- Setează procentul pentru toate laboratoarele profesorului
    UPDATE activitati
    SET procent = p_procent_laborator
    WHERE id_profesor_activitate = p_profesor_id
      AND descriere = 'laborator';
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure StudentInscriereMaterie
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `StudentInscriereMaterie`(IN studentID INT, IN materieID INT)
BEGIN
	DECLARE selected_professor INT;
	INSERT INTO catalog (id_materie_catalog, id_student_catalog) VALUES
    (materieID, studentID);
    SELECT profesor_id
    INTO selected_professor
    FROM Profesori
    WHERE id_profesor_materie = materieID
      AND numar_curent_studenti < nr_max_studenti
    ORDER BY numar_curent_studenti ASC
    LIMIT 1;
    -- Dacă a fost găsit un profesor disponibil, actualizează numărul curent de studenți
    IF selected_professor IS NOT NULL THEN
        UPDATE Profesori
        SET numar_curent_studenti = numar_curent_studenti + 1
        WHERE profesor_id = selected_professor;
	END IF;
    INSERT INTO studenti_profesori(id_student, id_profesor) VALUES
    (studentID, selected_professor);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure StudentRenuntareMaterie
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `StudentRenuntareMaterie`(IN studentID INT,IN materieID INT)
BEGIN

    DECLARE profesorID INT;
    -- 1. Șterge din catalog notele studentului pentru materia respectivă
    DELETE FROM catalog
    WHERE id_student_catalog = studentID
      AND id_materie_catalog = materieID;
    -- 2. Scade nr_participanti_activitate pentru activitățile asociate studentului
    UPDATE activitati a
    JOIN calendar c ON a.id_activitati = c.activitate_id_calendar
    SET a.nr_participanti_activitate = a.nr_participanti_activitate - 1
    WHERE c.student_id_calendar = studentID

      AND a.id_materii_activitati = materieID;
 
    -- 3. Șterge activitățile din calendar pentru student legate de materia respectivă

    DELETE c

    FROM calendar c

    JOIN activitati a ON c.activitate_id_calendar = a.id_activitati

    WHERE c.student_id_calendar = studentID

      AND a.id_materii_activitati = materieID;
 
    -- 4. Identifică profesorul asociat materiei și studentului

    -- Obține profesorID pentru student, legat de materia respectivă

	SELECT sp.id_profesor

	INTO profesorID

	FROM studenti_profesori sp

	JOIN profesori p ON sp.id_profesor = p.profesor_id

	WHERE sp.id_student = studentID AND p.id_profesor_materie = materieID

	LIMIT 1;
 
    -- 5. Scade numar_curent_studenti pentru profesorul respectiv

    UPDATE profesori

    SET numar_curent_studenti = numar_curent_studenti - 1

    WHERE profesor_id = profesorID;

    -- 6. Sterge din studenti_profesori

    DELETE FROM studenti_profesori

    WHERE id_student = studentID AND id_profesor = profesorID;

	-- 7. Șterge studentul din grup_studiu_studenti pentru grupurile asociate materiei

    DELETE gss

    FROM grup_studiu_studenti gss

    JOIN grup_studiu gs ON gss.id_grup = gs.id_grup

    WHERE gss.id_student = studentID

      AND gs.id_materie = materieID;
 
    -- 8. Actualizează numărul curent de participanți pentru activitățile asociate grupurilor

    UPDATE grup_activitati ga

    JOIN grup_activitati_studenti gas ON ga.id_activitate_grup = gas.id_activitate_grup

    JOIN grup_studiu gs ON ga.id_grup = gs.id_grup

    SET ga.nr_curent_participanti = ga.nr_curent_participanti - 1

    WHERE gas.id_student = studentID

      AND gs.id_materie = materieID;
 
    -- 9. Șterge studentul din grup_activitati_studenti pentru activitățile asociate materiei

    DELETE gas

    FROM grup_activitati_studenti gas

    JOIN grup_activitati ga ON gas.id_activitate_grup = ga.id_activitate_grup

    JOIN grup_studiu gs ON ga.id_grup = gs.id_grup

    WHERE gas.id_student = studentID

      AND gs.id_materie = materieID;
 
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure TrimiteMesaj
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `TrimiteMesaj`(
    IN studentID INT,
    IN mesaj VARCHAR(100),
    IN grupID INT
)
BEGIN
	INSERT INTO mesaje (id_grup_mesaj, student_id_mesaj, mesaj) VALUES
    (grupID, studentID, mesaj);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure VeziActivitatiGrup
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `VeziActivitatiGrup`(IN grupID int)
BEGIN
	SELECT
		grup_activitati.id_activitate_grup AS ID_activitate, 
        grup_activitati.id_grup AS ID_grup, 
        grup_activitati.nr_curent_participanti AS Nr_curent_participanti, 
        grup_activitati.nr_min_participanti AS Nr_min_participanti, 
        grup_activitati.ziua_calendaristica AS Ziua, 
        grup_activitati.ora AS Ora, 
        grup_activitati.descriere AS Descriere, 
        grup_activitati.durata AS Durata, 
		grup_activitati.timp_expirare AS Timp_expirare
    FROM grup_activitati
    JOIN grup_studiu ON grup_activitati.id_grup = grup_studiu.id_grup
    WHERE grup_activitati.id_grup = grupID AND grup_activitati.status_activitate=1 
			AND TIMESTAMP(grup_activitati.ziua_calendaristica, grup_activitati.ora) > NOW();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure VeziMembriGrup
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `VeziMembriGrup`(IN grupID int)
BEGIN
	SELECT 
    u.nume AS Nume,
    u.prenume AS Prenume
FROM 
    grup_studiu_studenti gss
JOIN 
    studenti s ON gss.id_student = s.student_id
JOIN 
    utilizatori u ON s.student_id_user = u.user_id
WHERE 
    gss.id_grup = grupID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure VeziProfesoriMaterie
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `VeziProfesoriMaterie`(IN materieID INT)
BEGIN
    SELECT 
        u.nume AS Nume,
        u.prenume AS Prenume,
        u.email AS Email
    FROM 
        profesori p
    JOIN 
        utilizatori u ON p.profesor_id_user = u.user_id
    JOIN 
        materii m ON p.id_profesor_materie = m.id_materii
    WHERE 
        m.id_materii = materieID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure VeziStudentiMaterie
-- -----------------------------------------------------

DELIMITER $$
USE `proiect`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `VeziStudentiMaterie`(IN materieID INT)
BEGIN
    SELECT 
        u.nume AS Nume,
        u.prenume AS Prenume,
        u.email AS Email
    FROM 
        studenti s
    JOIN 
        utilizatori u ON s.student_id_user = u.user_id
    JOIN 
        catalog c ON c.id_student_catalog = s.student_id
    WHERE 
       c.id_materie_catalog=materieID;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- View `proiect`.`view_profesor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `proiect`.`view_profesor`;
USE `proiect`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `proiect`.`view_profesor` AS select `u`.`user_id` AS `user_id`,`p`.`id_profesor_materie` AS `id_profesor_materie`,`u`.`CNP` AS `CNP`,`u`.`nume` AS `nume`,`u`.`prenume` AS `prenume`,`u`.`NumarTelefon` AS `NumarTelefon`,`u`.`Adresa` AS `Adresa`,`u`.`IBAN` AS `IBAN`,`u`.`email` AS `email`,`u`.`Nr_contract` AS `Nr_contract`,`p`.`profesor_id` AS `profesor_id`,`p`.`nr_max_ore` AS `nr_max_ore`,`p`.`nr_min_ore` AS `nr_min_ore`,`p`.`departament` AS `departament`,`p`.`nr_max_studenti` AS `nr_max_studenti` from (`proiect`.`utilizatori` `u` join `proiect`.`profesori` `p` on((`u`.`user_id` = `p`.`profesor_id_user`))) where (`u`.`Rol_id` = 2);

-- -----------------------------------------------------
-- View `proiect`.`view_studenti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `proiect`.`view_studenti`;
USE `proiect`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `proiect`.`view_studenti` AS select `u`.`user_id` AS `user_id`,`s`.`student_id` AS `student_id`,`u`.`CNP` AS `CNP`,`u`.`nume` AS `nume`,`u`.`prenume` AS `prenume`,`u`.`NumarTelefon` AS `NumarTelefon`,`u`.`Adresa` AS `adresa`,`u`.`IBAN` AS `IBAN`,`u`.`email` AS `email`,`u`.`Nr_contract` AS `Nr_contract`,`s`.`an_de_studiu` AS `an_de_studiu`,`s`.`numar_ore_sustinute` AS `numar_ore_sustinute` from (`proiect`.`utilizatori` `u` join `proiect`.`studenti` `s` on((`u`.`user_id` = `s`.`student_id_user`))) where (`u`.`Rol_id` = 3);

-- -----------------------------------------------------
-- View `proiect`.`view_studenti_note`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `proiect`.`view_studenti_note`;
USE `proiect`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `proiect`.`view_studenti_note` AS select `s`.`student_id` AS `student_id`,`u`.`nume` AS `nume`,`u`.`prenume` AS `prenume`,`c`.`nota` AS `nota` from ((`proiect`.`studenti` `s` join `proiect`.`utilizatori` `u` on((`u`.`user_id` = `s`.`student_id_user`))) join `proiect`.`catalog` `c` on((`c`.`id_student_catalog` = `s`.`student_id`)));
USE `proiect`;

DELIMITER $$
USE `proiect`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `proiect`.`after_insert_materii`
AFTER INSERT ON `proiect`.`materii`
FOR EACH ROW
BEGIN
    INSERT INTO grup_studiu(id_materie)
    VALUES (NEW.id_materii);
END$$

USE `proiect`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `proiect`.`TriggerRecalculeazaNoteFinala`
AFTER UPDATE ON `proiect`.`activitati`
FOR EACH ROW
BEGIN
    -- Verifică dacă procentul a fost modificat
    IF OLD.procent <> NEW.procent THEN
        -- Recalculează notele pentru toți studenții înscriși la activitatea afectată
        UPDATE catalog c
        SET c.nota = ROUND((
            SELECT SUM(cal.nota_activitate * (IFNULL(a.procent, 0) / 100))
            FROM calendar cal
            JOIN activitati a ON cal.activitate_id_calendar = a.id_activitati
            WHERE cal.student_id_calendar = c.id_student_catalog
            AND a.id_materii_activitati = NEW.id_materii_activitati
        ), 2)
        WHERE c.id_materie_catalog = NEW.id_materii_activitati;
    END IF;
END$$

USE `proiect`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `proiect`.`trg_before_insert_activitati`
BEFORE INSERT ON `proiect`.`activitati`
FOR EACH ROW
BEGIN
  DECLARE var_nr_curent_ore INT;
  DECLARE var_nr_max_ore INT;
  SELECT nr_curent_ore
  INTO var_nr_curent_ore
  FROM profesori
  WHERE profesor_id = NEW.id_profesor_activitate;
 
  SELECT nr_max_ore
  INTO var_nr_max_ore
  FROM profesori
  WHERE profesor_id = NEW.id_profesor_activitate;
 
  IF var_nr_curent_ore = var_nr_max_ore THEN
	 SIGNAL SQLSTATE '45000'
	 SET MESSAGE_TEXT = 'Profesorul nu mai are suficiente ore disponibile.';
  ELSE
	  UPDATE profesori
	  SET nr_curent_ore = nr_curent_ore + 2
	  WHERE profesor_id = NEW.id_profesor_activitate;
  END IF;
END$$

USE `proiect`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `proiect`.`check_activity_enrollment`
BEFORE INSERT ON `proiect`.`calendar`
FOR EACH ROW
BEGIN
    DECLARE activity_time TIME;
    DECLARE activity_day VARCHAR(10);
    DECLARE current_participants INT;
    DECLARE max_participants INT;
    DECLARE student_conflict INT;
 
    -- Obține ora și ziua activității la care studentul încearcă să se înscrie
    SELECT ora, ziua
    INTO activity_time, activity_day
    FROM activitati
    WHERE id_activitati = NEW.activitate_id_calendar;
    -- Verifică dacă există locuri disponibile pentru activitatea respectivă
    SELECT Nr_participanti_activitate, nr_max_participanti
    INTO current_participants, max_participants
    FROM activitati
    WHERE id_activitati = NEW.activitate_id_calendar;
 
    IF current_participants >= max_participants THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Nu sunt locuri disponibile pentru această activitate.';
    ELSE
        -- Verifică dacă studentul are alte activități care se suprapun
        SELECT COUNT(*) INTO student_conflict
        FROM calendar c
        JOIN activitati a ON c.activitate_id_calendar = a.id_activitati
        WHERE c.student_id_calendar = NEW.student_id_calendar
        AND a.ziua = activity_day
        AND a.ora = activity_time;
 
        IF student_conflict > 0 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Conflict de orar cu o altă activitate!';
        ELSE
            -- Dacă nu există conflicte, actualizează numărul de participanți în activitate
            UPDATE activitati
            SET Nr_participanti_activitate = Nr_participanti_activitate + 1
            WHERE id_activitati = NEW.activitate_id_calendar;
        END IF;
    END IF;
END$$

USE `proiect`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `proiect`.`trg_insert_activitate_student`
BEFORE INSERT ON `proiect`.`grup_activitati_studenti`
FOR EACH ROW
BEGIN
    DECLARE nr_participanti_curenti INT;
    DECLARE grup_associat_id INT;
 
    -- Obținem id-ul grupului asociat activității
    SELECT id_grup INTO grup_associat_id
    FROM grup_activitati
    WHERE id_activitate_grup = NEW.id_activitate_grup;
 
    -- Verificăm dacă studentul face parte din grupul respectiv
    IF NOT EXISTS (
        SELECT 1
        FROM grup_studiu_studenti
        WHERE id_student = NEW.id_student
        AND id_grup = grup_associat_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Studentul nu face parte din acest grup de studiu!';
    END IF;
 
    -- Creștem numărul curent de participanți pentru activitate
    SELECT nr_curent_participanti
    INTO nr_participanti_curenti
    FROM grup_activitati
    WHERE id_activitate_grup = NEW.id_activitate_grup;
 
    -- Actualizăm numărul curent de participanți (doar dacă studentul este validat)
    UPDATE grup_activitati
    SET nr_curent_participanti = nr_participanti_curenti + 1
    WHERE id_activitate_grup = NEW.id_activitate_grup;
 
END$$

USE `proiect`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `proiect`.`before_grup_studiu_student_insert`
BEFORE INSERT ON `proiect`.`grup_studiu_studenti`
FOR EACH ROW
BEGIN
    DECLARE materie_id INT; -- Variabilă pentru a reține materia asociată grupului
    DECLARE student_este_inscris BOOLEAN; -- Variabilă pentru a verifica înscrierea studentului
    -- Obținem materia asociată grupului
    SELECT id_materie INTO materie_id
    FROM grup_studiu
    WHERE id_grup = NEW.id_grup;
    -- Dacă grupul nu există, aruncăm o eroare
    IF materie_id IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Grupul specificat nu există.';
    END IF;
    -- Verificăm dacă studentul este înscris la materia grupului
    SELECT COUNT(*) > 0 INTO student_este_inscris
    FROM catalog
    WHERE id_student_catalog = NEW.id_student
      AND id_materie_catalog = materie_id;
    -- Dacă studentul nu este înscris, aruncăm o eroare
    IF NOT student_este_inscris THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Studentul nu este înscris la materia asociată grupului.';
    END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
