drop user 'kujosa'@'localhost';
create user 'kujosa'@'localhost' identified by 'kujosa';
grant all privileges on kujosadb.* to 'kujosa'@'localhost';
flush privileges;
