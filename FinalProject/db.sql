-- auto-generated definition
CREATE TABLE `admins` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `adminLevel` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `Admins_userID_uindex` (`userID`),
  CONSTRAINT `Admins_Users_userID_fk` FOREIGN KEY (`userID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(20) NOT NULL,
  `body` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `users` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `dateCreated` date DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `Users_userID_uindex` (`userID`),
  UNIQUE KEY `Users_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE INDEX Posts_Users_userID_fk
  ON Posts (userID);
