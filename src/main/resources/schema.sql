create table if not exists user_url (
  id  bigserial not null,
  date_created timestamp,
  long_url varchar(255),
  short_url varchar(255),
  primary key (id)
);
