-- Add this for test setup
INSERT INTO quiz_website_hibernate.User
(user_id, email, first_name, has_rated, is_admin, is_suspended, last_name, user_name, user_pw, salt_value)
VALUES(1, 'test@mail.com', 'Test', 0, 0, 0, 'LastName', 'test', 'vGYm+qeOMF8FEWagElbpSlE5IN0PDChMTqDN4kP5XSE=', 'C1ZcAb8+ioesnl8gqgSGTYL2JNQ=');

INSERT INTO quiz_website_hibernate.`User`
(user_id, email, first_name, has_rated, is_admin, is_suspended, last_name, user_name, user_pw, salt_value)
VALUES(2, 'admin@mail.com', 'Test', 0, 1, 0, 'admin', 'admin', 'XUr2xHH5/KRvTcm93D46iOXRLNrDivTgyN6KQbqv0cg=', 'wks88BmbvfBjxsRv5t/S/tti4kA=');
