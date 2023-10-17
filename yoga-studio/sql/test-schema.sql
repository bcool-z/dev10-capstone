drop database if exists yoga_studio_test;
create database yoga_studio_test;
use yoga_studio_test;


create table users (

user_id int primary key auto_increment,
first_name varchar(25) not null,
last_name varchar(25) not null,
dob date not null,
phone_number varchar(25) not null,
email_address varchar(512) not null unique,
`type` varchar(50) not null
);

delimiter //
create procedure set_known_good_state()
begin
	delete from users;

INSERT INTO users (first_name, last_name, dob, phone_number, email, user_type)
VALUES
('John', 'Doe', '1990-05-15', '555-555-5555', 'john.doe@email.com', 'Student'),
('Jane', 'Smith', '1985-02-20', '555-555-5556', 'jane.smith@email.com', 'Instructor'),
('Michael', 'Johnson', '1992-08-10', '555-555-5557', 'michael.johnson@email.com', 'Student'),
('Emily', 'Brown', '1988-11-30', '555-555-5558', 'emily.brown@email.com', 'Instructor'),
('David', 'Williams', '1995-04-05', '555-555-5559', 'david.williams@email.com', 'Student'),
('Sophia', 'Jones', '1993-07-25', '555-555-5560', 'sophia.jones@email.com', 'Instructor'),
('Daniel', 'Martinez', '1987-09-18', '555-555-5561', 'daniel.martinez@email.com', 'Student'),
('Olivia', 'Garcia', '1998-01-12', '555-555-5562', 'olivia.garcia@email.com', 'Instructor'),
('James', 'Rodriguez', '1991-03-21', '555-555-5563', 'james.rodriguez@email.com', 'Student'),
('Ava', 'Lopez', '1989-06-08', '555-555-5564', 'ava.lopez@email.com', 'Instructor'),
('Matthew', 'Hernandez', '1997-12-02', '555-555-5565', 'matthew.hernandez@email.com', 'Student'),
('Isabella', 'Perez', '1994-10-17', '555-555-5566', 'isabella.perez@email.com', 'Instructor'),
('Ethan', 'Smith', '1996-02-14', '555-555-5567', 'ethan.smith@email.com', 'Student'),
('Mia', 'Miller', '1986-07-01', '555-555-5568', 'mia.miller@email.com', 'Instructor'),
('Liam', 'Brown', '1990-08-23', '555-555-5569', 'liam.brown@email.com', 'Student'),
('Emma', 'Davis', '1992-09-27', '555-555-5570', 'emma.davis@email.com', 'Instructor'),
('Noah', 'Gonzalez', '1984-04-07', '555-555-5571', 'noah.gonzalez@email.com', 'Student'),
('Charlotte', 'Clark', '1993-06-19', '555-555-5572', 'charlotte.clark@email.com', 'Instructor'),
('Aiden', 'Hall', '1999-05-09', '555-555-5573', 'aiden.hall@email.com', 'Student'),
('Grace', 'Young', '1985-11-13', '555-555-5574', 'grace.young@email.com', 'Instructor'),
('Lucas', 'Lewis', '1997-07-16', '555-555-5575', 'lucas.lewis@email.com', 'Student'),
('Lily', 'Anderson', '1988-03-29', '555-555-5576', 'lily.anderson@email.com', 'Instructor'),
('Mason', 'Garcia', '1994-12-03', '555-555-5577', 'mason.garcia@email.com', 'Student'),
('Sophia', 'Hernandez', '1989-04-25', '555-555-5578', 'sophia.hernandez@email.com', 'Instructor'),
('Alexander', 'Flores', '1996-01-08', '555-555-5579', 'alexander.flores@email.com', 'Student'),
('Avery', 'Wilson', '1991-10-14', '555-555-5580', 'avery.wilson@email.com', 'Instructor'),
('Elijah', 'Thompson', '1998-03-04', '555-555-5581', 'elijah.thompson@email.com', 'Student'),
('Sofia', 'Martinez', '1987-09-29', '555-555-5582', 'sofia.martinez@email.com', 'Instructor'),
('Oliver', 'Smith', '1992-06-11', '555-555-5583', 'oliver.smith@email.com', 'Student'),
('Aria', 'Roberts', '1995-02-26', '555-555-5584', 'aria.roberts@email.com', 'Instructor'),
('Carter', 'Johnson', '1993-07-30', '555-555-5585', 'carter.johnson@email.com', 'Student'),
('Scarlett', 'Davis', '1990-12-22', '555-555-5586', 'scarlett.davis@email.com', 'Instructor'),
('Liam', 'White', '1988-05-24', '555-555-5587', 'liam.white@email.com', 'Student'),
('Zoe', 'Thomas', '1997-01-05', '555-555-5588', 'zoe.thomas@email.com', 'Instructor'),
('Logan', 'Robinson', '1994-10-29', '555-555-5589', 'logan.robinson@email.com', 'Student'),
('Abigail', 'Smith', '1991-08-15', '555-555-5590', 'abigail.smith@email.com', 'Instructor'),
('Mason', 'Wright', '1986-06-06', '555-555-5591', 'mason.wright@email.com', 'Student'),
('Ella', 'Brown', '1998-04-10', '555-555-5592', 'ella.brown@email.com', 'Instructor'),
('Jackson', 'Wilson', '1992-03-08', '555-555-5593', 'jackson.wilson@email.com', 'Student'),
('Avery', 'Anderson', '1990-09-28', '555-555-5594', 'avery.anderson@email.com', 'Instructor'),
('Noah', 'Davis', '1996-07-12', '555-555-5595', 'noah.davis@email.com', 'Student'),
('Lily', 'Harris', '1994-12-16', '555-555-5596', 'lily.harris@email.com', 'Instructor'),
('Benjamin', 'Gonzalez', '1989-10-01', '555-555-5597', 'benjamin.gonzalez@email.com', 'Student'),
('Mia', 'Johnson', '1995-08-11', '555-555-5598', 'mia.johnson@email.com', 'Instructor'),
('Ethan', 'Miller', '1991-06-19', '555-555-5599', 'ethan.miller@email.com', 'Student'),
('Ava', 'Thompson', '1987-02-28', '555-555-5600', 'ava.thompson@email.com', 'Instructor'),
('Lucas', 'Hernandez', '1999-01-14', '555-555-5601', 'lucas.hernandez@email.com', 'Student'),
('Isabella', 'Smith', '1998-04-25', '555-555-5602', 'isabella.smith@email.com', 'Instructor');

end //

delimiter ;
