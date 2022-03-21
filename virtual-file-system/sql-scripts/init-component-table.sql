DROP SCHEMA IF EXISTS `virtual-file-system`;

CREATE SCHEMA `virtual-file-system`;

use `virtual-file-system`;

DROP TABLE IF EXISTS `component`;

CREATE TABLE `component` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `create_at` bigint DEFAULT NULL,
  `data` varchar(400) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `component_type` varchar(10),
  
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_PARENT` FOREIGN KEY (`parent_id`) REFERENCES `component` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `component` VALUES 
	(1,'root',0,null,null,0,'folder');
