-- auto-generated definition
CREATE TABLE Admins
(
  userID     INT AUTO_INCREMENT
    PRIMARY KEY,
  adminLevel INT DEFAULT '1' NOT NULL,
  CONSTRAINT Admins_userID_uindex
  UNIQUE (userID),
  CONSTRAINT Admins_Users_userID_fk
  FOREIGN KEY (userID) REFERENCES flaskdb.Users (userID)
)
  ENGINE = InnoDB;

  -- auto-generated definition
  CREATE TABLE Users
  (
    userID      INT AUTO_INCREMENT
      PRIMARY KEY,
    username    VARCHAR(20) NOT NULL,
    pass        VARCHAR(50) NOT NULL,
    firstName   VARCHAR(50) NULL,
    lastName    VARCHAR(50) NULL,
    dateCreated DATE        NULL,
    CONSTRAINT Users_userID_uindex
    UNIQUE (userID),
    CONSTRAINT Users_username_uindex
    UNIQUE (username)
  )
    ENGINE = InnoDB;



-- auto-generated definition
CREATE TABLE Posts
(
  postID    INT AUTO_INCREMENT
    PRIMARY KEY,
  userID    INT                                 NOT NULL,
  message   VARCHAR(1000)                       NULL,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT Posts_postID_uindex
  UNIQUE (postID),
  CONSTRAINT Posts_Users_userID_fk
  FOREIGN KEY (userID) REFERENCES flaskdb.Users (userID)
)
  ENGINE = InnoDB;

CREATE INDEX Posts_Users_userID_fk
  ON Posts (userID);
