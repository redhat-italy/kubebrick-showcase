-- 
-- Editor SQL for DB table books
-- Created by http://editor.datatables.net/generator
-- 

CREATE TABLE IF NOT EXISTS packagelog (
	id serial,
	color text,
	creationtimestamp timestamp,
	pieces numeric(9,2),
	PRIMARY KEY( id )
);