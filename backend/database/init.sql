
create table product (
    uuid varchar(36) not null,
    code varchar(255) not null,
    name varchar(255) not null,
    price decimal not null,
    
    primary key (uuid)
);
