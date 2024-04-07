create schema if not exists tasklist;

create table if not exists users(
    id bigserial primary key,
    name varchar(150) not null,
    username varchar(150) not null unique,
    password varchar(150) not null
);

create table if not exists tasks(
    id bigserial primary key,
    title varchar(150) not null,
    description varchar(150) null,
    status varchar(150) not null,
    expiration_date timestamp null
);

create table if not exists users_tasks(
    user_id int not null,
    task_id int not null,
    primary key(user_id, task_id),
    foreign key(user_id) references users(id) on delete cascade on update no action,
    foreign key(task_id) references tasks(id) on delete cascade on update no action
);

create table if not exists users_roles(
    user_id int not null,
    role varchar(100) not null,
    primary key(user_id, role),
    foreign key(user_id) references users(id) on delete cascade on update no action
);