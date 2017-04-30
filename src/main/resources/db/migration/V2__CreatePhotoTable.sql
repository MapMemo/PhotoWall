create table PHOTO (
    ID varchar(255) not null,
    POSTER varchar(255) not null,
    TIMESTAMP bigint not null,
    LATITUDE double,
    LONGITUDE double,
    NAME varchar(1024),
    primary key(ID)
);

create index IDX_PHOTO_LATITUDE on PHOTO(LATITUDE);
create index IDX_PHOTO_LONGITUDE on PHOTO(LONGITUDE);
