CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(65) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `vacancy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position` varchar(65) CHARACTER SET utf8 NOT NULL,
  `estimated_salary` decimal(10,2) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `requirements` varchar(1024) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `vacancy_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);
