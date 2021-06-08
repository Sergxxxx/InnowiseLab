CREATE TABLE message_history(
                                id                bigserial NOT NULL constraint message_history_pkey primary key,
                                id_message        bigint    NOT NULL,
                                id_user           bigint    NOT NULL,
                                operation         char(1)   NOT NULL,
                                changed_by        text      NOT NULL,
                                time_change       timestamp NOT NULL,
                                tag               text      NOT NULL,
                                text              text      NOT NULL,
                                old_tag           text              ,
                                old_text          text
);

CREATE OR REPLACE FUNCTION update_message_history() RETURNS TRIGGER
AS
$$
BEGIN
        INSERT INTO message_history(id_message,
                                    id_user,
                                    operation,
                                    changed_by,
                                    time_change,
                                    tag,
                                    text,
                                    old_tag,
                                    old_text)
        VALUES((SELECT n.id FROM new_table n),
               (SELECT n.user_id FROM new_table n),
               'U',
               user,
               now(),
               (SELECT n.tag FROM new_table n),
               (SELECT n.text FROM new_table n),
               (SELECT o.tag FROM old_table o),
               (SELECT o.text FROM old_table o)
               );
RETURN NULL;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER message_history_update
    AFTER UPDATE ON message
    REFERENCING OLD TABLE AS old_table NEW TABLE AS new_table
    FOR EACH STATEMENT EXECUTE PROCEDURE update_message_history();

CREATE OR REPLACE FUNCTION insert_message_history() RETURNS TRIGGER
AS
$$
BEGIN
        INSERT INTO message_history(id_message,
                                    id_user,
                                    operation,
                                    changed_by,
                                    time_change,
                                    tag,
                                    text,
                                    old_tag,
                                    old_text)
SELECT n.id, n.user_id,'I', user, now(), n.tag, n.text, n.tag, n.text FROM new_table n;
RETURN NULL;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER message_history_insert
    AFTER INSERT ON message
    REFERENCING NEW TABLE AS new_table
    FOR EACH STATEMENT EXECUTE PROCEDURE insert_message_history();

CREATE OR REPLACE FUNCTION delete_message_history() RETURNS TRIGGER
AS
$$
BEGIN
        INSERT INTO message_history(id_message,
                                    id_user,
                                    operation,
                                    changed_by,
                                    time_change,
                                    tag,
                                    text,
                                    old_tag,
                                    old_text)
SELECT o.id, o.user_id,'D', user, now(), o.tag, o.text, o.tag, o.text FROM old_table o;
RETURN NULL;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER message_history_delete
    AFTER DELETE ON message
    REFERENCING OLD TABLE AS old_table
    FOR EACH STATEMENT EXECUTE PROCEDURE delete_message_history();