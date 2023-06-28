create table users
(
    id       bigserial    not null primary key,
    user_name  text      not null,
    password  text      not null
);
