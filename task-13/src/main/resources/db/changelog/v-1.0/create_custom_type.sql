create type select_type as
    (
    _id          bigint,
    _id_message  bigint,
    _id_user     bigint,
    _operation   char,
    _changed_by  text,
    _time_change timestamp,
    _tag         text,
    _text        text,
    _old_tag     text,
    _old_text    text
    );