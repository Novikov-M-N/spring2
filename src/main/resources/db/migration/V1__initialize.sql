drop table if exists products cascade;
create table products (
    id                  bigserial,
    title               varchar(255),
    description         varchar(5000),
    price               numeric(8, 2),
    primary key(id)
);
insert into products
    (title, description, price) values
    ('Cheese', 'Fresh cheese', 320.0),
    ('Milk', 'Fresh milk', 80.0),
    ('Apples', 'Fresh apples', 80.0),
    ('Bread', 'Fresh bread', 30.0);

drop table if exists categories cascade;
create table categories (
    id                  bigserial,
    title               varchar(255),
    primary key(id)
);
insert into categories
    (title) values
    ('Food'),
    ('Devices');

drop table if exists products_categories cascade;
create table products_categories (
    product_id          bigint not null,
    category_id         bigint not null,
    primary key(product_id, category_id),
    foreign key (product_id) references products(id),
    foreign key (category_id) references categories(id)
);
insert into products_categories
    (product_id, category_id) values
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 2);

drop table if exists users;
create table users (
    id                  bigserial,
    phone               VARCHAR(30) not null UNIQUE,
    password            VARCHAR(80) not null,
    email               VARCHAR(50) UNIQUE,
    first_name          VARCHAR(50),
    last_name           VARCHAR(50),
    age                 INTEGER,
    balance             NUMERIC(8, 2),
    PRIMARY KEY (id)
);

drop table if exists roles;
create table roles (
    id                  serial,
    name                VARCHAR(50) not null,
    primary key (id)
);

drop table if exists users_roles;
create table users_roles (
    user_id             INT NOT NULL,
    role_id             INT NOT NULL,
    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

insert into roles
    (name) values
    ('CUSTOMER'),
    ('MANAGER'),
    ('ADMIN');

insert into users
    (phone, password, first_name, last_name, email, age, balance) values
    ('1111111111','$2y$12$rxg3cYnpaZsNwAVuaiKZXeDb69dR.h9foNdfARmOp/9UBRLymKV22','admin-fn','admin-ln','admin@gmail.com', 25, 500.25),
    ('2222222222','$2y$12$2znBKu1hJDfirEl11JkGJu.DmJ3NJt5E3Fwnne9ZnZ9V4y9jJRAnq','manager1-fn','manager1-ln','manager1@gmail.com', 24, 500),
    ('3333333333','$2y$12$crw8TU6zv55ZdKW6WSn2I.9/gS5zjeMCdBRPb6aF1PG9t4doQzPaa','manager2-fn','manager2-ln','manager2@gmail.com', 23, 500),
    ('4444444444','$2y$12$WL1UGrlik9ovTRD.easRUuyRE8M9LVnbrpOOe/bD0SHfWHgg7lSRa','customer1-fn','customer1-ln','customer1@gmail.com', 22, 500),
    ('5555555555','$2y$12$cpr8mi8rf2rivQVcqc6sbO.9z9wRmBRu83wP.Pst5X2uoW.xgxlRq','customer2-fn','customer2-ln','customer2@gmail.com', 21, 500),
    ('6666666666','$2y$12$LTAaKjdTc.uEYTpaW.gCs.AY0kADnXjP2d6aTwCrFHDsKdDhd7g36','customer3-fn','customer3-ln','customer3@gmail.com', 20, 500);

insert into users_roles
    (user_id, role_id)  values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 2),
    (3, 1),
    (3, 2),
    (4, 1),
    (5, 1),
    (6, 1);

drop table if exists orders cascade;
create table orders (
    id                  bigserial,
    user_id             bigint not null,
    price               numeric(8, 2) not null,
    status              varchar(32),
    address             varchar (255) not null,
    phone_number        varchar(30) not null,
    primary key(id),
    constraint fk_user_id foreign key (user_id) references users (id)
);

drop table if exists items cascade;
create table items (
    id                  bigserial,
    product_id          bigint not null,
    quantity            int,
    price               numeric(8, 2),
    primary key(id),
    foreign key (product_id) references products (id)
);

drop table if exists orders_items cascade;
create table orders_items (
    id                  bigserial,
    order_id            bigint not null,
    item_id             bigint not null,
    primary key(id),
    foreign key (order_id) references orders (id),
    foreign key (item_id) references items (id)
);

drop table if exists prices cascade;
create table prices (
    id                  bigserial,
    product_id          bigint,
    price               numeric(8, 2),
    from_date           timestamp,
    to_date             timestamp,
    primary key(id)
);
insert into prices
    (product_id, price, from_date, to_date) values
    (1, 300.0, '2020-08-25 00:00:00', '2020-09-25 00:00:00'),
    (1, 310.0, '2020-09-25 00:00:00', '2020-10-25 00:00:00'),
    (2, 70.0, '2020-08-26 00:00:00', '2020-09-26 00:00:00'),
    (2, 75.0, '2020-09-26 00:00:00', '2020-10-26 00:00:00'),
    (3, 65.0, '2020-08-27 00:00:00', '2020-09-27 00:00:00'),
    (3, 70.0, '2020-09-27 00:00:00', '2020-10-27 00:00:00'),
    (4, 25.0, '2020-08-28 00:00:00', '2020-09-28 00:00:00'),
    (4, 28.0, '2020-09-28 00:00:00', '2020-10-28 00:00:00');