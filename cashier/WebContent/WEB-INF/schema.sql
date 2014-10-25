Drop table orders;
create table orders (id integer not null primary key, type string, cost string, additions string default '', payment string default '', carddetail string default '',
p_status string default 'no', c_status string default 'not prepared');