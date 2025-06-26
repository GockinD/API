CREATE TABLE human (
	id INT PRIMARY KEY,
	name TEXT NOT NULL,
	age INTEGER CHECK (age >0),
	driversLicense BOOLEAN)

CREATE TABLE car (
	id INT PRIMARY KEY,
	brand TEXT NOT NULL,
	model TEXT NOT NULL,
	cost INT CHECK (cost > 0))

alter table human add car INT references car(id)