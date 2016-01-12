drop database if exists kujosadb;
create database kujosadb;
 
use kujosadb;
 
create table users (
userid   binary(16) not null auto_increment primary key,
username varchar(20) not null,
userpass binary(16) not null,
name     varchar(70) not null,
email    varchar(255) not null,
image    varchar(20)
);

create table user_roles (
userid   binary(16) not null,
rolename varchar(20) not null,
foreign key(userid) references users(userid) on delete cascade,
primary key (userid, rolename)
);
 
create table social (
stingid                         int not null auto_increment primary key,
userid                          binary(16) not null,
subject                         varchar(100) not null,
content                         varchar(500) not null,
last_modified			timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
creation_timestamp		datetime not null default current_timestamp,
foreign key(userid) references users(userid)
);

create table events (
	eventid		 	int not null auto_increment primary key,
	titol			varchar(100) not null,
	text			varchar(600) not null,
	lat                     long not null,
	lon                     long not null,
        start_date	 	datetime not null,
	end_date	 	datetime not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	ratio			int,
	numVots			int
	
);

create table commentsevent (
	commentid	 	int not null auto_increment primary key,
	userid   	 	binary(16) not null,
	eventid		 	int not null,
	content		 	varchar(200) not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp	datetime not null default current_timestamp,
	foreign key (userid)    references users (userid) on delete cascade,
	foreign key (eventid) 	references events (eventid) on delete cascade,
	image                   varchar(20) not null,
	ratio                   int
);

create table participantes (
        userid                  binary(16) not null,
	eventid			int not null,
	primary key (userid, eventid),
	foreign key (userid) 	references users(userid) on delete cascade,
	foreign key (eventid)	references events (eventid) on delete cascade
);

create table news (
        userid             binary(16) not null,
        headline           varchar(50) not null primary key,
        body               varchar(500) not null,
	last_modified      timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp datetime not null default current_timestamp,
        foreign key (userid) 	references users(userid) on delete cascade
);

create table document (
        userid      binary(16) not null,
        docid       int not null,
        name        varchar(50) not null,
        description varchar(200) not null,
        last_modified      timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp datetime not null default current_timestamp,
	foreign key (userid) references users(userid) on delete cascade
);

create table commentsdoc (
        commentid	 	int not null auto_increment primary key,
	eventid		 	int not null ,
	userid	 	        binary(16) not null,
	docid		 	int not null,
	content		 	varchar(200) not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp	datetime not null default current_timestamp,
	foreign key (userid)  references users (userid) on delete cascade,
	foreign key (eventid) 	references events (eventid) on delete cascade
);
