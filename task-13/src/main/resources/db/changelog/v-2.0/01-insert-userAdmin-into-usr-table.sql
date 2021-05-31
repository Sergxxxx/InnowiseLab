INSERT INTO usr (id, active, password, username, email, registration_date_time)
    VALUES ('1', b'1', '1', '1', '1@1.com', now())
GO

INSERT INTO user_role (user_id, roles) VALUES ('1', 'ADMIN')
GO

