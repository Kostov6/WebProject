use project;

create table users (
	user varchar(16) not null primary key,
	type varchar(8) not null ,
    password varchar(32),
    git_user varchar (32),
    git_pic varchar(128),
    last_access_token varchar(128),
    git_hash varchar(64) 
);

create table user_roles (
  user varchar(16) not null,
  role_name varchar(15) not null,
  primary key (user, role_name)
);