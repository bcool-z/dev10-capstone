drop database if exists yoga_studio_test;
create database yoga_studio_test;
use yoga_studio_test;


create table app_user (
	app_user_id int primary key auto_increment,
    
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    dob date null,
    phone_number varchar(15) null,
    email_address varchar(512) not null unique,
    user_type enum ('STUDENT','INSTRUCTOR'),
    password_hash varchar(1024) not null
);

create table location (

location_id int primary key auto_increment,
`name` varchar(50) not null,
size enum ('SMALL','MEDIUM','LARGE') null,
`description` varchar(1000) null
);

create table `session` (

session_id int primary key auto_increment,
start_time datetime not null,
end_time datetime not null,
capacity int,
instructor_id int null,
location_id int null,
constraint fk_session_instructor_user_id
        foreign key (instructor_id)
        references app_user(app_user_id),
constraint fk_session_location_id
	foreign key (location_id)
	references location (location_id)
);

create table reservation
(
reservation_id int primary key auto_increment,

session_id int not null,
student_id int not null,

constraint fk_reservation_session_id
		foreign key (session_id)
        references `session`(session_id),
constraint fk_reservation_guest_id        
		foreign key (student_id)
        references app_user(app_user_id)

);

delimiter //
create procedure set_known_good_state()
begin

	delete from reservation;
    alter table reservation auto_increment =1;

	delete from `session`; 
    alter table `session` auto_increment = 1;
    
    delete from app_user;
    alter table app_user auto_increment = 1;
    
    delete from location;
    alter table location auto_increment = 1;

INSERT INTO app_user (first_name, last_name, dob, phone_number, email_address, user_type, password_hash)
VALUES
('FirstName1', 'LastName1', '1991-01-01', '555-555-5551', 'fn.ln1@email.com', 'STUDENT','0'),
('FirstName2', 'LastName2', '1992-02-02', '555-555-5552', 'fn.ln2@email.com', 'INSTRUCTOR','0'),
('FirstName3', 'LastName3', '1993-03-03', '555-555-5553', 'fn.ln3@email.com', 'STUDENT','0'),
('FirstName4', 'LastName4', '1994-04-04', '555-555-5554', 'fn.ln4@email.com', 'INSTRUCTOR','0'),
('FirstName5', 'LastName5', '1995-05-05', '555-555-5555', 'fn.ln5@email.com', 'STUDENT','0'),
('FirstName6', 'LastName6', '1996-06-06', '555-555-5556', 'fn.ln6@email.com', 'INSTRUCTOR','0'),
('FirstName7', 'LastName7', '1997-07-07', '555-555-5557', 'fn.ln7@email.com', 'STUDENT','0');


insert into location (`name`, size, `description`)

values
('Location1','SMALL','Description1'),
('Location2','MEDIUM','Description2'),
('Location3','LARGE','Description3'),
('Location4','SMALL','Description4'),
('Location5','MEDIUM','Description5'),
('Location6','LARGE','Description6'),
('Location7','SMALL','Description7');


insert into `session` (start_time, end_time, capacity, instructor_id, location_id)
values
('3024-02-22 13:00:00','3024-02-22 14:00:00',3,2,1),
('3024-02-22 14:00:00','3024-02-22 15:00:00',10,2,2),
('3024-02-23 10:00:00','3024-02-23 11:00:00',12,4,3);

insert into reservation (session_id, student_id)
values 
(1,1),
(1,3),
(1,5);
 
end //

delimiter ;
