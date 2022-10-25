create sequence hibernate_sequence start 1 increment 1;

    create table MyEntity (
       id int8 not null,
        field varchar(255),
        primary key (id)
    );
