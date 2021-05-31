CREATE TABLE user_role (
   user_id bigint NOT NULL,
   roles varchar(255) DEFAULT NULL,
   CONSTRAINT user_role_FK_1 FOREIGN KEY (user_id) REFERENCES usr (id) ON DELETE CASCADE
 ) ENGINE=InnoDB CHARSET=latin1

GO