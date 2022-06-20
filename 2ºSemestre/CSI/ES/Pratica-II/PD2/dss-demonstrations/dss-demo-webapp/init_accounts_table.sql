CREATE TABLE `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(60) NOT NULL,
  `phoneNumber` varchar(14) NOT NULL, # para incluir o indicativo com espaço opcional "+351 "
  PRIMARY KEY (`id`)
);