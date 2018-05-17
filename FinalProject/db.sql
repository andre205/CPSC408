create DATABASE bretboard;

USE bretboard;

CREATE TABLE users (
  userid INT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  pass VARCHAR(100) NOT NULL ,
  firstName VARCHAR(50) DEFAULT NULL ,
  lastName VARCHAR(50) DEFAULT NULL ,
  dateCreated DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admins (
  userid int(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  FOREIGN KEY (userID) REFERENCES bretboard.users (userID),
  adminLevel INT(8) NOT NULL DEFAULT 1
);

CREATE TABLE posts (
  postid int(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  authorid int(20) NOT NULL,
  FOREIGN KEY (authorid) REFERENCES bretboard.users (userID),
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  title varchar(20) NOT NULL,
  body varchar(500) NOT NULL
);

CREATE INDEX post_index ON posts (postid, authorid, created, title, body);
CREATE INDEX user_index ON users (userid, username, pass, firstName, lastName, dateCreated);
CREATE INDEX admin_index ON admins (userid, adminLevel);

CREATE VIEW all_data AS
SELECT postid,authorid,u.username,u.firstName,u.lastName,a.adminLevel,created,title,body from posts p
  JOIN users u ON p.authorid = u.userid
  LEFT JOIN admins a ON a.userid = u.userid;

INSERT INTO users(userid, username, pass, firstName, lastName) VALUES (1,'admin','pbkdf2:sha256:50000$kWhkvjfh$6d80b446582cd51b2040ac0e515468ef9b86fdc58ba32859ff122e41b9df8ba2','admin','admin');
INSERT INTO admins(userid, adminLevel) VALUES (1,3);
INSERT INTO posts(postid, authorid, title, body) VALUES (1,1,'first post','first post');
