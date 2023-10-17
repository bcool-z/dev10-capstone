drop database if exists yoga_studio;
create database yoga_studio;
use yoga_studio;


create table users (

user_id int primary key auto_increment,
first_name varchar(25) not null,
last_name varchar(25) not null,
dob date not null,
phone_number varchar(25) not null,
email_address varchar(512) not null unique,
`type` varchar(50) not null
);

create table location (

location_id int primary key auto_increment,
`name` varchar(50) not null,
description varchar(1000) null
);

create table `session` (

start_time date not null,
end_time date not null,
 constraint fk_session_instructor_user_id
        foreign key (instructor_id)
        references `user`(user_id)    

);

create table reservation
(
reservation_id int primary key auto_increment,

constraint fk_reservation_session_id
		foreign key (session_id)
        references `session`(session_id),
constraint fk_reservation_guest_id        
		foreign key (guest_id)
        references `user`(user_id)

)