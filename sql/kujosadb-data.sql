source kujosadb-schema.sql;


insert into users values(UNHEX('1'),'alicia', UNHEX(MD5('alicia')), 'alicia@acme.com','Alicia tiene malicia',null);
insert into user_roles values (UNHEX('1'), 'registered');

insert into users values(UNHEX('2'),'blas', UNHEX(MD5('blas')), 'blas@acme.com','Blas te nas',null);
insert into user_roles values (UNHEX('2'), 'registered');

insert into events (id, userid, titol,text, lat, lon, start_date, end_date, ratio) values (UNHEX('1'), UNHEX('1'),'TITOL1', 'Fundació de lAESTEC', '0', '0', '2015-12-5 09:00:00', '2015-12-5 11:00:00', 5);

insert into events (id, userid, titol,text, lat, lon, start_date, end_date, ratio) values (UNHEX('2'), UNHEX('2'), 'TITOL2', 'Sortida al supercomputador', '42','2', '2015-12-21 10:00:00', '2015-12-2 11:00:00', 3);

insert into events (id, userid, titol,text, lat, lon, start_date, end_date, ratio) values (UNHEX('3'), UNHEX('2'), 'TITOL3', 'Exposició i demostració de drons', '0', '0', '2016-1-25 17:00:00', '2016-1-25 19:30:00',1);


insert into news values (UNHEX('1'),UNHEX('2'), 'Franco inaugura la AESTEC','El caudillo de España recibió a los estudiantes de la EETAC para crear esa asociación afiliada a las juventades falangistas. ¡Arriba España!', '2015-1-25 17:00:00', '2015-1-25 19:30:00');
insert into news values (UNHEX('2'), UNHEX('2'), 'AESTEC dóna la benvinguda a la nova República', 'AESTEC es posa al costat de la Generalitat per donar suport a la creació de la República Catalana, i està a la seva disposició per al què necessiti.', '2016-1-01 17:00:00', '2016-1-07 19:30:00');

