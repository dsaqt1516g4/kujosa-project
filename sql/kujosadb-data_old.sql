source kujosadb-schema.sql;
insert into users values('alicia', MD5('alicia'), 'Alicia', 'alicia@acme.com',null);
insert into user_roles values ('alicia', 'registered');

insert into users values('blas', MD5('blas'), 'Blas', 'blas@acme.com',null);
insert into user_roles values ('blas', 'registered');

insert into stings (username, subject, content) values ('alicia', 'Mensaje absurdo#0', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('blas', 'Mensaje absurdo#1', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('alicia', 'Mensaje absurdo#2', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('blas', 'Mensaje absurdo#3', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('alicia', 'Mensaje absurdo#4', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('blas', 'Mensaje absurdo#5', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('blas', 'Mensaje absurdo#6', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('alicia', 'Mensaje absurdo#7', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('blas', 'Mensaje absurdo#8', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('blas', 'Mensaje absurdo#9', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('alicia', 'Mensaje absurdo#10', 'Fermín Cacho parece buen muchacho.');
select sleep(1);insert into stings (username, subject, content) values ('alicia', 'Mensaje absurdo#11', 'Fermín Cacho parece buen muchacho.');
select sleep(1);

insert into events (userid, name, lat, lon, start_date, end_date) values ('1', 'Fundació de lAESTEC', '0', '0', '2015-12-5 09:00:00', '2015-12-5 11:00:00');
select sleep(1);
insert into events (userid, name, lat, lon, start_date, end_date) values ('2', 'Sortida al supercomputador', '2015-12-21 10:00:00', '2015-12-2 11:00:00');
select sleep(1);
insert into events (userid, name, lat, lon, start_date, end_date) values ('1', 'Exposició i demostració de drons', '0', '0', '2016-1-25 17:00:00', '2016-1-25 19:30:00');
select sleep(1);


insert into news values ('1','Franco inaugura la AESTEC','El caudillo de España recibió a los estudiantes de la EETAC para crear esa asociación afiliada a las juventades falangistas. ¡Arriba España!');
insert into news values ('2', 'AESTEC dóna la benvinguda a la nova República', 'AESTEC es posa al costat de la Generalitat per donar suport a la creació de la República Catalana, i està a la seva disposició per al què necessiti.');
