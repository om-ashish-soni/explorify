create table if not exists registration_records(
  id int PRIMARY KEY,
  event_id varchar(100) not null,
  attendee varchar(50)
);