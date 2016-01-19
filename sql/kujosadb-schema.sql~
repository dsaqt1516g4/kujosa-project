drop database if exists kujosadb;
create database kujosadb;
 
use kujosadb;
 
create table users (

    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    image varchar(40),
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

	id                      BINARY(16) NOT NULL,
        userid                  BINARY(16) NOT NULL,
	eventid		 	BINARY(16) NOT NULL,
	content		 	varchar(200) not null,
	last_modified		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp	datetime not null default current_timestamp,
        foreign key (userid)    references users (id) on delete cascade,
	foreign key (eventid) 	references events (id) on delete cascade,
	PRIMARY KEY (id)
);

create table participantes (

        userid                  BINARY(16) NOT NULL,
	eventid			BINARY(16) NOT NULL,
	primary key (userid, eventid),
	foreign key (userid) 	references users(id) on delete cascade,
	foreign key (eventid)	references events (id) on delete cascade
);

create table news (

        id                  BINARY(16) NOT NULL,
        userid              BINARY(16) NOT NULL,
        headline           varchar(50) not null,
        body               varchar(500) not null,
	last_modified      timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creation_timestamp datetime not null default current_timestamp,
        FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
        PRIMARY KEY (id)
);

create table document (

        id                  BINARY(16) NOT NULL,
        userid              BINARY(16) NOT NULL,
        name                varchar(50) not null,
        description         varchar(200) not null,
        path                varchar(500) not null,
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
	creation_timestamp datetime not null default current_timestamp,
	foreign key (userid) references users(id) on delete cascade,
	foreign key (docid) 	references document (id) on delete cascade,
	primary key(id)
);
