CREATE TABLE message (
    id bigint NOT NULL auto_increment,
    tag varchar(255) DEFAULT NULL,
    text varchar(255) DEFAULT NULL,
    user_id bigint DEFAULT NULL,
    created_date_time datetime(6) DEFAULT NULL,
    updated_date_time datetime(6) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT message_FK_1 FOREIGN KEY(user_id) REFERENCES usr(id) ON DELETE CASCADE
) ENGINE=InnoDB CHARSET=latin1

GO