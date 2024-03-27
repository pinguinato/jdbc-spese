USE spese;

CREATE TABLE IF NOT EXISTS `spesa` (
  `id_spesa` int(11) NOT NULL AUTO_INCREMENT,
  `titolo_spesa` varchar(50) NOT NULL,
  `descrizione_spesa` varchar(255) DEFAULT NULL,
  `autore_spesa` varchar(50) DEFAULT NULL,
  `ammontare_spesa` double(10,2) NOT NULL,
  PRIMARY KEY (`id_spesa`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `spesa`
ADD COLUMN `data_spesa` DATETIME DEFAULT NULL;

