CREATE TABLE IF NOT EXISTS `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` nvarchar(65) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `vacancy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position` nvarchar(65) NOT NULL,
  `estimated_salary` decimal(10,2) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `requirements` nvarchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_vacancy_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`));
