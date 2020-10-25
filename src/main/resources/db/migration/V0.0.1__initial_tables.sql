# https://dev.mysql.com/doc/refman/8.0/en/create-table.html
create table truck
(
    created_at timestamp       not null default current_timestamp,
    id         bigint unsigned not null auto_increment primary key,
    name       varchar(255)    not null,
    updated    timestamp       not null default current_timestamp
);

create table customer
(
    created_at timestamp       not null default current_timestamp,
    id         bigint unsigned not null auto_increment primary key,
    phone      varchar(50)     not null unique,
    updated    timestamp       not null default current_timestamp
);

create table `order`
(
    created_at  timestamp       not null default current_timestamp,
    customer_id bigint unsigned not null,
    id          bigint unsigned not null auto_increment primary key,
    truck_id    bigint unsigned not null,
    updated     timestamp       not null default current_timestamp,
    foreign key (customer_id) references customer (id),
    foreign key (truck_id) references truck (id)
);

create table preparation_type
(
    created_at timestamp       not null default current_timestamp,
    id         bigint unsigned not null auto_increment primary key,
    name       varchar(255)    not null unique,
    price      decimal(13, 2)  not null,
    updated    timestamp       not null default current_timestamp
);

create table bean_type
(
    created_at       timestamp       not null default current_timestamp,
    id               bigint unsigned not null auto_increment primary key,
    name             varchar(255)    not null unique,
    price_multiplier decimal(13, 2)  not null,
    updated          timestamp       not null default current_timestamp
);

create table status
(
    created_at timestamp       not null default current_timestamp,
    id         bigint unsigned not null auto_increment primary key,
    name       varchar(50)     not null,
    updated    timestamp       not null default current_timestamp
);

create table item
(
    created_at          timestamp       not null default current_timestamp,
    bean_type_id        bigint unsigned not null,
    id                  bigint unsigned not null auto_increment primary key,
    order_id            bigint unsigned not null,
    preparation_type_id bigint unsigned not null,
    status_id           bigint unsigned not null,
    updated             timestamp       not null default current_timestamp,
    foreign key (order_id) references `order` (id),
    foreign key (bean_type_id) references bean_type (id),
    foreign key (preparation_type_id) references preparation_type (id),
    foreign key (status_id) references status (id)
);