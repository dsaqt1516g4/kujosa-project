drop database if exists kujosadb;
create database kujosadb;
 
use kujosadb;
 
create table users (
<<<<<<< HEAD

    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    image varchar(20)
    PRIMARY KEY (id)

);

create table user_roles (

    userid BINARY(16) NOT NULL,
    role ENUM ('registered'),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (userid, role)
    
);

CREATE TABLE auth_tokens (

    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (token)
    
);
 
create table social (

    id                  BINARY(16) NOT NULL,
    userid              BINARY(16) NOT NULL,
    subject             VARCHAR(100) NOT NULL,
    content             VARCHAR(500) NOT NULL,
    last_modified       TIMESTAMP NOT NULL,
    creation_timestamp  datetime not null default current_timestamp,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (id)
=======
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
>>>>>>> 7f7ac7c9a77122dd2179cf694985ce5dc716d29d
);

create table events (

    id	                BINARY(16) NOT NULL,
    userid              BINARY(16) NOT NULL,
    titol		varchar(100) not null,
    text		varchar(600) not null,
    lat                 long not null,
    lon                 long not null,
    start_date	 	datetime not null,
    end_date	 	datetime not null,
    last_modified	timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
    ratio		int not null,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (id)
);

create table commentsevent (
<<<<<<< HEAD

	id                      BINARY(16) NOT NULL,
        userid                  BINARY(16) NOT NULL,
	eventid		 	BINARY(16) NOT NULL,
=======
	commentid	 	int not null auto_increment primary key,
	userid   	 	binary(16) not null,
	eventid		 	int not null,
>>>>>>> 7f7ac7c9a77122dd2179cf694985ce5dc716d29d
	content		 	varchar(200) not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp	datetime not null default current_timestamp,
	foreign key (userid)    references users (id) on delete cascade,
	foreign key (eventid) 	references events (id) on delete cascade,
	image                   varchar(20) not null,
	ratio                   int not null,
	FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
	PRIMARY KEY (id)
);

create table participantes (
<<<<<<< HEAD

        userid                  BINARY(16) NOT NULL,
	eventid			BINARY(16) NOT NULL,
=======
        userid                  binary(16) not null,
	eventid			int not null,
>>>>>>> 7f7ac7c9a77122dd2179cf694985ce5dc716d29d
	primary key (userid, eventid),
	foreign key (userid) 	references users(id) on delete cascade,
	foreign key (eventid)	references events (id) on delete cascade
);

create table news (
<<<<<<< HEAD

        id                  BINARY(16) NOT NULL,
        userid              BINARY(16) NOT NULL,
=======
        userid             binary(16) not null,
>>>>>>> 7f7ac7c9a77122dd2179cf694985ce5dc716d29d
        headline           varchar(50) not null primary key,
        body               varchar(500) not null,
	last_modified      timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp datetime not null default current_timestamp,
        foreign key (userid) 	references users(username) on delete cascade,
        FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
        PRIMARY KEY (id)
        
);

create table document (
<<<<<<< HEAD

        id                  BINARY(16) NOT NULL,
        userid              BINARY(16) NOT NULL,
        name                varchar(50) not null,
        description         varchar(200) not null,
        last_modified       timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp  datetime not null default current_timestamp,
	foreign key (userid) references users(id) on delete cascade,
	PRIMARY KEY(id)
	
);

create table commentsdoc (

        id	 	    BINARY(16) NOT NULL,
	userid              BINARY(16) NOT NULL,
	docid		    BINARY(16) NOT NULL,
	content		    varchar(200) not null,
	last_modified	    timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
=======
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
>>>>>>> 7f7ac7c9a77122dd2179cf694985ce5dc716d29d
	creation_timestamp	datetime not null default current_timestamp,
	foreign key (userid)  references users (id) on delete cascade,
	foreign key (docid) references document (id) on delete cascade,
	PRIMARY KEY(id)
	
);
