CREATE SCHEMA bettingSystem;

CREATE TABLE login(
username 	VARCHAR(255) 	PRIMARY KEY NOT NULL,
password 	VARCHAR(255)	NOT NULL);

CREATE TABLE userinfo(
username 	VARCHAR(255) 	PRIMARY KEY REFERENCES login(username),
name 		VARCHAR(255)	NOT NULL,
balance 	DECIMAL(8,2)	DEFAULT 100.00 CHECK(balance >= 0),
dob 		DATE			NOT NULL,
email 		VARCHAR(255),
sex			VARCHAR(10));

CREATE TABLE match(
matchid 		SERIAL			PRIMARY KEY NOT NULL,	
team1 			VARCHAR(255),
team2 			VARCHAR(255),
coefficient1 	DECIMAL	(4,2)	NOT NULL,
coefficient2 	DECIMAL	(4,2)	NOT NULL,
coefficienttie 	DECIMAL	(4,2)	NOT NULL,
score1 			INTEGER			DEFAULT 0,
score2 			INTEGER			DEFAULT 0,
timestart 		TIMESTAMP		NOT NULL, 
type 			VARCHAR(255)	NOT NULL,
length			INTEGER);

CREATE TABLE ticket(
ticketid 	SERIAL 			PRIMARY KEY,
username 	VARCHAR(255)	REFERENCES login(username),
totalodd 	DECIMAL(8,2)	NOT NULL CHECK(totalodd > 0),
money 		DECIMAL(6,2),
status		VARCHAR(15) 	DEFAULT('Pending'),
timestart 	TIMESTAMP);

CREATE TABLE ticketinfo(
ticketid      integer	REFERENCES ticket(ticketid),
matchid       integer	REFERENCES match(matchid),
bet           CHARACTER VARYING (1),
coefficient   numeric (6, 2),
status        CHARACTER VARYING (7),
PRIMARY KEY (ticketid,matchid));


--TRIGER FUNCTION FOR INSERT ACTUAL DATE WHEN NEW TICKET IS ADDED

CREATE OR REPLACE FUNCTION set_Ticket_Date() RETURNS 
TRIGGER AS $$ 
BEGIN UPDATE ticket SET timestart = current_timestamp WHERE ticketid = new.ticketid;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER new_ticket
AFTER INSERT ON ticket FOR EACH ROW 
EXECUTE PROCEDURE set_Ticket_Date();