create table credential
(
    id       bigint       not null
        primary key,
    enabled  bit          not null,
    password varchar(255) not null,
    username varchar(255) not null,
    constraint UK_cy9bwr22tkmr9hl2iecjqcwvg
        unique (username)
);

create table authority
(
    role       varchar(255) not null,
    credential bigint       not null,
    primary key (role, credential),
    constraint FKo0u5mld90q178ppkufw0kaom5
        foreign key (credential) references credential (id)
            on delete cascade
);

create table hibernate_sequence
(
    next_val bigint null
);

create table organization
(
    name                    varchar(255) not null
        primary key,
    managedBy_credential_id bigint       null
);

create table property
(
    id        bigint       not null
        primary key,
    address   varchar(255) not null,
    city      varchar(255) not null,
    state     varchar(2)   not null,
    zipcode   varchar(5)   not null,
    managedBy varchar(255) null,
    constraint FK3bgk41tua3q9x9us5k3p18goy
        foreign key (managedBy) references organization (name)
);

create table memo
(
    content     varchar(8000) null,
    subject     varchar(180)  null,
    timestamp   datetime      null,
    property_id bigint        not null
        primary key,
    constraint FKl33kmngy9b9t4dbbea6c5t59j
        foreign key (property_id) references property (id)
            on delete cascade
);

create table user
(
    email         varchar(255) not null,
    fName         varchar(25)  not null,
    lName         varchar(25)  not null,
    credential_id bigint       not null
        primary key,
    property      bigint       null,
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email),
    constraint FKtb18n2gwnlou4lgltqn1ganlg
        foreign key (credential_id) references credential (id)
            on delete cascade,
    constraint FKw7k69i84gibi9tanhdxwamwv
        foreign key (property) references property (id)
);

alter table organization
    add constraint FKjf9ghj5809vb8f3h287f6228o
        foreign key (managedBy_credential_id) references user (credential_id);

create table ticket
(
    id          bigint       not null
        primary key,
    description varchar(280) not null,
    status      varchar(255) null,
    timestamp   datetime     null,
    user        bigint       null,
    property    bigint       null,
    constraint FKhn41wdlfiqv7s8o7svb27852u
        foreign key (property) references property (id),
    constraint FKkh64g43tjdln3narjpjnxcxcf
        foreign key (user) references user (credential_id)
);