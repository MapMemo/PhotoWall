create table ACCOUNT (
	ID varchar(255) not null,
	EMAIL varchar(255) not null,
	NICKNAME varchar(255) not null,
	PASSWORD varchar(512) not null,
	primary key(ID)
);

create index IDX_ACCOUNT_IDENTITY on ACCOUNT(EMAIL); 

create table AUTHENTICATION (
	ID varchar(255) not null,
	TOKEN varchar(64) not null,
	primary key(ID, TOKEN)
);