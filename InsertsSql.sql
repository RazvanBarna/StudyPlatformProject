
INSERT INTO Rol (Rol_id, Rol_nume) VALUES 
(0, 'Super-Admin'),
(1, 'Admin'), 
(2, 'Profesor'), 
(3, 'Student');

insert into Materii(id_materii,nume_materii) values 
(1, 'Algebra liniara'),
(2, 'Matematici speciale in inginerie'),
(3, 'Baze de date'),
(4, 'Programare orientata pe obiecte'),
(5, 'Arhitectura calculatoarelor'),
(6,'Retele de Calculatoare'),
(7,'Proiectarea Sistemelor Numerice'),
(8,'Tehnici de programare');

-- insert studenti
INSERT INTO Utilizatori (CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, Rol_id,parola)
VALUES ('5041006325698', 'Popa', 'Radu', '0741234567', 'Strada Grivita, Nr. 10, Agnita', 'RO49AAAA1B31007593840000', 'radupopa@yahoo.com', 20,3,'mate20'),
       ('5031013325598', 'Popescu', 'Razvan', '0742523456', 'Strada Semaforului, Nr. 25, Sibiu', 'RO50BCAB1B31007526840000', 'popescu_razvan@gmail.com', 32, 3, '12samsung'),
       ('5020923428698', 'Stoica', 'Ianis', '0734234587', 'Strada Mihai Viteazu, Nr. 187, Cluj-Napoca', 'RO29AABC1A31008873840000', 'ianis.stoica23@outlook.com', 54 ,3, 'fotbal123*'),
       ('5010529426678', 'Cristea', 'Andrei', '0703234299', 'Strada Nicolae Iorga, Nr. 26, Arad', 'RO49BACD1B81008693840000', 'andrei_cristea2001@gmail.com', 80 ,3 ,'	!'),
       ('5050616565795', 'Munteanu', 'George', '0752233597', 'Calea Cisnadiei, Nr. 10, Timisoara', 'RO56ABCA1B31008573910000', 'george.munteanu@yahoo.com', 100, 3, 'temesvar'),
       ('6041025334610', 'Florea', 'Andreea', '0742344567', 'Strada Observatorului, Nr. 250, Cluj-Napoca', 'RO18ABCA1B31887587840000', 'florea_andreea@gmail.com', 39, 3, 'masina_21'),
       ('6030202346688', 'Toma', 'Cristiana', '0746784627', 'Strada Hipodromului, Nr. 15, Sibiu', 'RO78AAAA1C31006491240000', 'cristiana.toma83@outlook.com', 100, 3, 'flori54'),
       ('6010903425798', 'Rusu', 'Amalia', '0751238855', 'Strada Republicii, Nr. 52, Cluj-Napoca', 'RO52ABBA1B31007893740000', 'amaliarusu@yahoo.com', 147 ,3 , 'cezifrumoasa'),
       ('6020101478198', 'Lazar', 'Irina', '0721235662', 'Strada Abrud, Nr. 158, Timisoara', 'RO06ABBA1B31027595842200', 'irina_lazar@gmail.com', 248, 3, 'matematica67'),
       ('6041006325698', 'Dinu', 'Liliana', '0756238592', 'Strada Arenei, Nr. 17, Oradea', 'RO80AADD1B75008493740000', 'liliana.dinu@gmail.com', 7, 3, 'koka_kola'),
	   ('5041016326698', 'Ionescu', 'Bogdan', '0762234567', 'Bulevardul Muncii, Nr. 100, Cluj-Napoca', 'RO49AAAA1B31007584840000', 'ionescu.bogdan@gmail.com', 160,3,'hop-asa'),
	   ('5040907326689', 'Slavescu', 'Andrei', '0777234567', 'Calea Manastur, Nr. 56, Cluj-Napoca', 'RO49AAAA1B41007584850000', 'andrei_slavescu@gmail.com', 194,3,'bijuterie'),
	   ('5040102327699', 'Badea', 'Mihaela', '0784243557', 'Strada Bicazului, Nr. 90, Cluj-Napoca', 'RO50ABBC1B41107584850000', 'badea.mihaela@yahoo.com', 294,3,'koka-kola'),
	   ('5040620897313', 'Dinescu', 'Robert', '0794243687', 'Aleea Biruintei, Nr. 6, Sibiu', 'RO60ADBC1B41117524860000', 'robert_dinescu@outlook.com', 31,3,'actorie'),
	   ('5040906897420', 'Negoita', 'Teodora', '0712263627', 'Piata Rahovei, Nr. 25, Sibiu', 'RO24BDBC1B41117522860029', 'teodora.negoita@gmail.com', 210,3,'starwars'),
	   ('5041224897420', 'Tomescu', 'Andreea', '0713263569', 'Strada Cibinului, Nr. 86, Sibiu', 'RO34BACC1B42227522862029', 'tomescu_andreea@gmail.com', 478,3,'boss'),
	   ('5041101897042', 'Voinea', 'Cristina', '0731236596', 'Aleea Armoniei, Nr. 290, Satu Mare', 'RO43BAAA1B42272522862092', 'cristina.voinea@outlook.com', 555,3,'satu-mare'),
	   ('5040404827021', 'Ursu', 'Marian', '0745346526', 'Piata 1 Mai, Nr. 12, Satu Mare', 'RO55BCAD1B42282532562992', 'marian.ursu@gmail.com', 667,3,'adu_telefonu'),
	   ('5040506837012', 'Petrescu', 'Dan', '0726356529', 'Bulevardul Unirii, Nr. 232, Bucuresti', 'RO55BCDA1B42282537562722', 'dan.petrescu@yahoo.com', 2,3,'cfr_cluj'),
	   ('5030202837025', 'Sumudica', 'Marius', '0722375517', 'Strada Libertatii, Nr. 20, Bucuresti', 'RO23BCDA1B44582537562724', 'marius.sumudica@gmail.com', 63,3,'astra.giurgiu');

INSERT INTO studenti (student_id_user, an_de_studiu, numar_ore_sustinute) 
VALUES (1, 1, 20),
       (2, 1, 20),
       (3, 1, 20),
       (4, 2, 25),
       (5, 2, 25),
	   (6, 2, 25),
       (7, 3, 22),
       (8, 3, 22),
       (9, 3, 22),
       (10, 4, 15),
	   (11, 1, 20),
	   (12, 1, 20),
	   (13, 1, 20),
	   (14, 2, 25),
	   (15, 2, 25),
	   (16, 2, 25),
	   (17, 3, 22),
	   (18, 3, 22),
	   (19, 3, 22),
	   (20, 4, 15);

-- profesori
INSERT INTO Utilizatori (CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, Rol_id, parola)
VALUES
    ('1960123456789', 'Popescu', 'Ion', '0723456789', 'Str. Progresu, nr. 10, București', 'RO49AAAA1B31007593840000', 'ion.popescu@email.com', 123456, 2, 'parola123'),
    ('2971234567891', 'Ionescu', 'Maria', '0734567890', 'Str. Libertății, nr. 20, Cluj-Napoca', 'RO12BBBB1C31007654320000', 'maria.ionescu@gmail.com', 654321, 2, 'lala'),
    ('1861123456785', 'Georgescu', 'Adrian', '0745678901', 'Str. Mihai Eminescu, nr. 15, Timișoara', 'RO30CCCC1D31007765410000', 'adrian.georgescu@gmail.com', 987654, 2, 'maremunte'),
    ('2990987654321', 'Dumitrescu', 'Elena', '0756789012', 'Str. Păcii, nr. 25, Iași', 'RO78DDDD1E31007890120000', 'elena.dumitrescu@gmail.com', 112233, 2, 'lapte'),
    ('1931230987654', 'Vasilescu', 'Andrei', '0767890123', 'Str. Victoriei, nr. 30, Constanța', 'RO45EEEE1F31007987650000', 'andrei.vasilescu@email.com', 445566, 2, 'clujBucuresti'),
    ('2881234567890', 'Marinescu', 'Cristina', '0778901234', 'Str. Unirii, nr. 40, Brașov', 'RO90FFFF1G31008012340000', 'cristina.marinescu@email.com', 778899, 2, 'judec'),
    ('1781207278127', 'Pop', 'Vasile', '0722234597', 'Strada Zorilor, Nr. 48, Cluj-Napoca', 'RO49AABB1B31005493730000', 'vasile.pop@yahoo.com', 156, 2, 'algebra_culegere'),
    ('1781203129143', 'Rus', 'Tudor', '0712434527', 'Calea Dorobantilor, Nr. 120, Cluj-Napoca', 'RO56AABB1B24006493730000', 'tudor.rus@gmail.com', 186, 2, 'pisica782'),
	('1810204126368', 'Baciu', 'Marian', '0716434539', 'Strada Alesd, Nr. 201, Cluj-Napoca', 'RO86AABB1B24007498940000', 'baciu.marian@yahoo.com', 368, 2, 'c++'),
	('1660414120413', 'Suciu', 'Ioan', '0721229587', 'Strada Actorului, Nr. 26, Cluj-Napoca', 'RO98AABB1B31005473830100', 'ioan_suciu@outlook.com', 1000, 2, '12tranzistor21'),
	('1721017123273', 'Albu', 'Dorian', '0736784516', 'Piata Muzeului, Nr. 21, Cluj-Napoca', 'RO99AABB1B31075492730099', 'albu.dorian@gmail.com', 196, 2, 'hatz'),
	('2750604128284', 'Sava', 'Maria', '0722634797', 'Strada Alunului, Nr. 75, Cluj-Napoca', 'RO49AABB1B31805493830000', 'maria_sava@yahoo.com', 72, 2, 'baze_de_date'),
	('2800713127196', 'Chiriac', 'Adela', '0735244897', 'Strada Avram Iancu, Nr. 50, Cluj-Napoca', 'RO25AABB1B31005494730200', 'adela.chiriac@yahoo.com', 37, 2, 'floricele'),
	('2770203127169', 'Nita', 'Cristiana', '0722784512', 'Piata Cipariu, Nr. 88, Cluj-Napoca', 'RO68AABC1B51005497330000', 'nita_cristiana@gmail.com', 246, 2, '86campie78!');
    
    
INSERT INTO Profesori (profesor_id_user, nr_max_ore, nr_min_ore, departament, nr_max_studenti, id_profesor_materie, Numar_curent_studenti) VALUES 
(21, 30, 6, 'Hardware', 60, 6, 0), -- RC
(22, 12, 6, 'Matematica', 55, 1, 0), -- al
(23, 10, 6, 'Hardware', 70, 5, 0), -- ac
(24, 20, 6, 'Hardware', 80, 5, 0), -- ac
(25, 24, 6, 'Hardware', 40, 7, 0), -- psn
(26, 16, 6, 'Hardware', 63, 7, 0), -- psn
(27, 32, 6, 'Matematica', 120, 1, 0), -- al
(28, 22, 6, 'Software', 60, 8, 0), -- tp 
(29, 10, 6, 'Software', 55, 4, 0), -- poo
(30, 8, 6, 'Software', 49,4, 0), -- poo
(31, 28, 6, 'Software', 40, 3, 0), -- bd
(32, 18, 6, 'Matematica', 30, 1, 0), -- al	
(33, 26, 6, 'Software', 50, 8, 0), -- tp
(34, 20, 6, 'Matematica', 99, 2, 0); -- msi


-- adminii

INSERT INTO Utilizatori (CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, Rol_id,parola) VALUES 
('1910921123456', 'Marin', 'Cristina', '0723987654', 'Calea Victoriei, Nr. 99', 'RO23ACBA1C31006794840088', 'cristina.marin@email.com', 52, 1, 'adminull'),
('1800322123467', 'Ilie', 'Mihai', '0723654321', 'Strada Universitatii, Nr. 21', 'RO56ACBA1D31006894840077', 'mihai.ilie@email.com', 76, 1, 'secureadmin2'),
('1800823125229', 'Stanciu', 'Nicolae', '0723355676', 'Strada Aviatorilor, Nr. 12', 'RO88ACBA1B31006594840099', 'nicolae_stanciu@yahoo.com', 19, 1,'admin12'),
('2890606124564', 'Tanase', 'Adriana', '0723765687', 'Bulevardul Eroilor, Nr. 54', 'RO19ACBA1B31006694841299', 'tanase_adriana', 28, 1,'404admin404'),
('1700210329979', 'Bucur', 'Alexandru', '0723455777', 'Strada Berariei, Nr. 62', 'RO78ACBA1D31006594850099', 'alex.bucur@gmail.com', 105, 0,'adminu_shef');

INSERT INTO admins (admin_id_user) VALUES
(35),
(36),
(37),
(38),
(39);






