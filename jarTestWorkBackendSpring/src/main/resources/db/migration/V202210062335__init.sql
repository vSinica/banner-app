create table category
(
    id       bigint       not null
        primary key,
    deleted  boolean      not null,
    name     varchar(255) not null,
    req_name varchar(255) not null
);

create table banners
(
    id       bigint       not null
        primary key,
    content  varchar(255) not null,
    deleted  boolean      not null,
    name     varchar(255) not null,
    price    bigint       not null,
    category bigint
        constraint banners_category_fkey
            references category
);

create table requests
(
    id         bigint not null
        primary key,
    datetime   timestamp,
    ip_address varchar(255),
    user_agent varchar(255),
    banner     bigint
        constraint requests_banner_fkey
            references banners
);