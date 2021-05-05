create table public.t_owner(
		id bigint not null,
		first_name varchar(255),
		last_name varchar(255)


);
alter table public.t_owner add constraint public.constraint_1 primary key(id);
create table public.t_pet(
id bigint not null,
name varchar(255),
birth_date date,
owner_id bigint

);



alter table public.t_pet add constraint public.constraint_2 primary key(id);

alter table public.t_pet add constraint public.constraint_3  foreign key(owner_id) references public.t_owner(id);

create sequence public.petclinic_sequence start with 100;
