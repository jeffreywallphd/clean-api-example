CREATE TABLE University(
	universityId INT(15) NOT NULL AUTO_INCREMENT,
	name VARCHAR(150) NOT NULL,
	CONSTRAINT universityPK PRIMARY KEY(universityId)
);

CREATE TABLE Person(
	personId INT(15) NOT NULL AUTO_INCREMENT,
	firstName VARCHAR(30) NOT NULL,
	lastName VARCHAR(40) NOT NULL,
	universityId INT(15) NOT NULL,
	CONSTRAINT personPK PRIMARY KEY(personId),
	CONSTRAINT universityIdFK FOREIGN KEY(universityId) REFERENCES University(universityId)
);