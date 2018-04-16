# This is the DDL to create the normalized database I used for this assignment

# UserInfo(uid, username, first, last, telephone,  address, zip, dob)
# UserInventory(uid, gid, purchasedate)
# UserSpecs(uid, cpu, cpu_freq, graphics, manf, ramsize, ramtype, resolution )
# GameInfo(gid, name, releasedate, description)
# UserTickets(tid, uid, gid)
# TicketInfo(tid, level)

CREATE DATABASE A3;

USE A3;

CREATE TABLE UserInfo(uid INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, userName VARCHAR(40),firstName VARCHAR(40), lastName VARCHAR(40), telephone VARCHAR(40), address VARCHAR(40), zip VARCHAR(10), dob VARCHAR(40));

CREATE TABLE GameInfo(gid INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(40), releaseDate VARCHAR(40), description VARCHAR(200));

CREATE TABLE UserInventory(uid INTEGER NOT NULL, FOREIGN KEY (uid) REFERENCES UserInfo(uid), gid INTEGER NOT NULL, FOREIGN KEY (gid) REFERENCES GameInfo(gid), purchaseDate VARCHAR(40));

CREATE TABLE UserSpecs(uid INTEGER NOT NULL, FOREIGN KEY (uid) REFERENCES UserInfo(uid), cpu VARCHAR(40), cpuFreq  VARCHAR(40), graphicsCard VARCHAR(40), manufacturer VARCHAR(40), ramSize VARCHAR(40), ramType VARCHAR(40), resultion VARCHAR(40) );

CREATE TABLE UserTickets(tid INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, uid INTEGER NOT NULL, FOREIGN KEY (uid) REFERENCES UserInfo(uid), gid INTEGER NOT NULL, FOREIGN KEY (gid) REFERENCES GameInfo(gid));

CREATE TABLE TicketInfo(tid INTEGER NOT NULL, FOREIGN KEY (tid) REFERENCES UserTickets(tid), text VARCHAR(300), level VARCHAR(20) );
