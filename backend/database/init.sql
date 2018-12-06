
create table product (
    uuid uuid not null,
    code varchar(255) not null,
    name varchar(255) not null,
    price decimal not null,
    
    primary key (uuid),
    unique (code)
);
