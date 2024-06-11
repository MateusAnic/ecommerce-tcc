set search_path = "public";

create sequence perfume_id_seq start 14 increment 1;
create sequence users_id_seq start 2 increment 1;
create sequence order_item_seq start 12 increment 1;
create sequence orders_seq start 6 increment 1;
create sequence review_seq start 1 increment 1;

    create table order_item
(
    id         numeric,
    id_order   numeric,
    quantity   numeric,
    id_perfume numeric
);

create table orders
(
    id           numeric,
    id_user      numeric,
    date         date,
    status       numeric,
    total_price  decimal(11,2),
    primary key (id)
);

create table perfume
(
    id_perfume              numeric not null,
    categories              varchar(255),
    price                   decimal(11,2),
    name                    varchar(255),
    description             varchar(255),
    perfume_rating          decimal(7,2),
    ativo                   numeric,
    foto                    bytea,

    primary key (id_perfume)
);

create table review
(
    id      numeric,
    id_perfume numeric,
    author  varchar(255),
    date    date,
    message varchar(255),
    rating  decimal(7,2),
    primary key (id)
);

CREATE TABLE CARGO (
    ID_CARGO NUMERIC NOT NULL,
    NOME varchar(512) UNIQUE NOT NULL,
    PRIMARY KEY(ID_CARGO)
);

CREATE SEQUENCE seq_cargo
 START WITH     3
 INCREMENT BY   1;

create table USERS
(
    id                  numeric    not null,
    ativo               varchar(1) not null,
    address             varchar(255),
    city                varchar(255),
    email               varchar(255),
    first_name          varchar(255),
    last_name           varchar(255),
    password            varchar(255),
    phone_number        varchar(255),
    post_index          varchar(255),
    foto                bytea,
    primary key (id)
);


CREATE TABLE USER_CARGO (
    ID_USER  NUMERIC NOT NULL,
    ID_CARGO NUMERIC NOT NULL,
    PRIMARY KEY(ID_USER, ID_CARGO),
    CONSTRAINT FK_USER_CARGO_CARGO FOREIGN KEY (ID_CARGO) REFERENCES CARGO (ID_CARGO),
  	CONSTRAINT FK_USER_CARGO_USER FOREIGN KEY (ID_USER) REFERENCES USERS (ID)
);


alter table if exists order_item add constraint FK_ORDER_ITEM_PERFUME foreign key (id_perfume) references perfume;
alter table if exists order_item add constraint FK_ORDER_ITEM_ORDER foreign key (id_order) references orders;
alter table if exists order_item add constraint PK_ORDER_ITEM primary key (id);
alter table if exists order_item add constraint UK_ORDER_ITEM_PERF_ORD unique (id_order, id_perfume);
alter table if exists orders add constraint FK_ORDERS_USER foreign key (id_user) references users (id);
alter table if exists review add constraint FK_REVIEW_PERFUME foreign key (id_perfume) references perfume (id_perfume);
