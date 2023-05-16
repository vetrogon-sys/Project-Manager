create table if not exists USERS (
    id bigserial not null,
    email varchar(255) not null,
    password varchar(512) not null,
    first_name varchar(30),
    last_name varchar(30),
    primary key (id)
);

create table if not exists PROJECTS (
    id bigserial not null,
    description varchar(255),
    name varchar(255),
    creator_id bigserial not null,
    primary key (id),
    constraint fk_creator_id foreign key (creator_id) references USERS(id)
);

create table if not exists DESKS (
    id bigserial not null,
    name varchar(255),
    project_id bigserial not null,
    primary key (id),
    constraint fk_project_id foreign key (project_id) references PROJECTS(id)
);

create table if not exists TASKS (
    id bigserial not null,
    creation_date date,
    description varchar(255),
    req_resolution_date date,
    title varchar(255),
    assigned_id int8 references users(id),
    desk_id bigserial not null,
    primary key (id),
    constraint fk_desk_id foreign key (desk_id) references DESKS(id)
);

create table USER_PROJECTS (
    project_id int8 not null,
    user_id int8 not null,
    constraint fk_project_id foreign key (project_id) references PROJECTS(id),
    constraint fk_user_id foreign key (user_id) references USERS(id)
);

create table AUTHORITIES (
    id bigserial not null,
    signature varchar(255),
    related_project_id int8 not null,
    assigned_user_id int8 not null,
    primary key(id),
    constraint fk_related_project_id foreign key (related_project_id) references PROJECTS(id),
    constraint fk_assign_user_id foreign key (assigned_user_id) references USERS(id)
);

create table SECURITY_OPPORTUNITIES (
    opportunity varchar(255) not null,
    primary key (opportunity)
);

create table AUTHORITY_OPPORTUNITIES (
    authority_id int8 not null,
    opportunity_id varchar not null,
    constraint fk_authority_id foreign key (authority_id) references AUTHORITIES(id),
    constraint fk_opportunity_id foreign key (opportunity_id) references SECURITY_OPPORTUNITIES(opportunity)
);

create sequence user_id_sequence start 1;
create sequence project_id_sequence start 1;
create sequence desk_id_sequence start 1;
create sequence task_id_sequence start 1;
create sequence authority_id_sequence start 1;