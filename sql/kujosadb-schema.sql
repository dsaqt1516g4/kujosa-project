drop database if exists kujosadb;
create database kujosadb;
 
use kujosadb;
 
create table users (
username varchar(20) not null primary key,
userpass char(32) not null,
name varchar(70) not null,
email varchar(255) not null,
image varchar(20) not null
);
 
create table user_roles (
username varchar(20) not null,
rolename varchar(20) not null,
foreign key(username) references users(username) on delete cascade,
primary key (username, rolename)
);
 
create table stings (
stingid int not null auto_increment primary key,
username varchar(20) not null,
subject varchar(100) not null,
content varchar(500) not null,
last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
creation_timestamp		datetime not null default current_timestamp,
foreign key(username) references users(username)
);

create table events (
	eventid		 	int not null auto_increment primary key,
	username		varchar(20) not null,
	name			varchar(100) not null,
	coordinates             varchar(20) not null,
	start_date	 	datetime not null,
	end_date	 	datetime not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	foreign key (username) 	references users (username) on delete cascade
	
);

create table comments (
	commentid	 	int not null auto_increment primary key,
	username	 	varchar(30) not null,
	eventid		 	int not null,
	content		 	varchar(200) not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp	datetime not null default current_timestamp,
	foreign key (username)  references users (username) on delete cascade,
	foreign key (eventid) 	references events (eventid) on delete cascade
	image                   varchar(10) not null,
	ratio                   int not null
);

create table state (
	username 		varchar(30) not null,
	eventid			int not null,
	primary key (username, eventid),
	foreign key (username) 	references users(username) on delete cascade,
	foreign key (eventid)	references events (eventid) on delete cascade
);

create table news (
        username varchar(30) not null,
        headline varchar(50) not null,
        body var(500) not null,
        last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp	datetime not null default current_timestamp
);

create table document (
        username varchar(30) not null,
        docid int not null,
        name varchar(50) not null,
        description varchar(200) not null,
        path varchar(20) not null
);

create table commentsdoc (
        commentid	 	int not null auto_increment primary key,
	username	 	varchar(30) not null,
	docid		 	int not null,
	content		 	varchar(200) not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp	datetime not null default current_timestamp,
	foreign key (username)  references users (username) on delete cascade,
	foreign key (eventid) 	references events (eventid) on delete cascade
);