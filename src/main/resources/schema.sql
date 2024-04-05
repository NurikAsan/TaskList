create database if not exists tasklist;

create table if not exists users(
    id int auto_increment,
    name varchar(150) not null,
    username varchar(150) not null unique,
    password varchar(150) not null,
    primary key(id)
);

create table if not exists tasks(
    id int auto_increment,
    title varchar(150) not null,
    description varchar(150) null,
    status varchar(150) not null,
    expiration_date timestamp null,
    primary key(id)
);

create table if not exists users_tasks(
    user_id int not null,
    task_id int not null,
    primary key(user_id, task_id),
    foreign key(user_id) references users(id) on delete cascade on update no action,
    foreign key(task_id) references tasks(id) on delete cascade on update no action
);

create table if not exists users_role(
    user_id int not null,
    role varchar(100) not null,
    primary key(user_id, role),
    foreign key(user_id) references users(id) on delete cascade on update no action
);