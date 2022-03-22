create or replace procedure rollback_message (desired_date timestamp)
as
$$
DECLARE
sel select_type;
    crs_message_history cursor for select *
                                   from message_history
                                   where time_change >= desired_date
                                   order by time_change desc;
begin
    open crs_message_history;

    loop
        fetch crs_message_history into sel;
                if not found then exit; end if;
        CASE(sel._operation)
            WHEN ('U') THEN
                update message
                set tag = sel._old_tag, text = sel._old_text
                where id = sel._id_message;
            WHEN ('D') THEN
                insert into message
                values (sel._id_message, sel._tag, sel._text, sel._id_user, sel._time_change, sel._time_change);
            WHEN ('I') THEN
                delete from message where id = sel._id_message;
        END case;
    end loop;
end;
$$ language plpgsql