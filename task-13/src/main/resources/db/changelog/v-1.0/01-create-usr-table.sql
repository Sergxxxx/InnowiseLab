CREATE TABLE usr (
   id bigint NOT NULL auto_increment,
   active bit(1) NOT NULL,
   password varchar(255) DEFAULT NULL,
   username varchar(255) DEFAULT NULL,
   email varchar(255) DEFAULT NULL,
   registration_date_time datetime(6) NOT NULL,
   PRIMARY KEY (id)
 ) ENGINE=InnoDB CHARSET=latin1

GO