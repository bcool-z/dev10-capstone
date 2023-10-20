drop database if exists yoga_studio;
create database yoga_studio;
use yoga_studio;



create table app_user (

user_id int primary key auto_increment,
first_name varchar(25) not null,
last_name varchar(25) not null,
dob date not null,
phone_number varchar(25) not null,
email_address varchar(512) not null unique,
user_type varchar(50) not null,
password_hash varchar(1024) not null

);



create table app_user (
	app_user_id int primary key auto_increment,
    username varchar(256) not null unique,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    dob date null,
    phone varchar(15) null,
    password_hash varchar(1024) not null
);

create table app_authority (
	app_authority_id int primary key auto_increment,
    `name` varchar(25) not null
);

create table app_user_authority (
	app_user_id int not null,
    app_authority_id int not null,
    constraint pk_app_user_authority
		primary key (app_user_id, app_authority_id),
	constraint fk_app_user_authority_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id),
	constraint fk_app_user_authority_app_authority_id
		foreign key (app_authority_id)
		references app_authority(app_authority_id)
);



create table location (

location_id int primary key auto_increment,
`name` varchar(50) not null,
size enum ('SMALL','MEDIUM','LARGE'),
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