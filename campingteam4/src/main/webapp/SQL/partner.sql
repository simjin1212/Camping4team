select * from tab;
select * from partner;
select * from seq;
select * from PB_JOIN;
select * from member;
select * from review;
select * from pb_join where par_no = 3;
select * from partner_comment;


update partner set now_num = 1;
delete from PB_JOIN where par_no = 23;
update partner set now_num = 1 where par_no = 3;

		update partner set subject='abb',
		content='adda', start_date='2022/03/09',
		end_date='2022/03/09', camp_area='서울', camp_type='캠핑',
		id = 'test',
		want_num=3 where par_no=3;

drop table pb_join;
create table pb_Join(
	pkey number primary key,
	par_no number,
	join_id varchar2(50)
);

select * from pb_join where join_id = 'pikachu' and par_no = 23;

drop table pb_Join;
select partner_seq from dual;
delete from partner;
delete from pb_Join;

drop sequence pkey_seq;


update partner set now_num = 1 + (select count(*) from pb_join where par_no = 23) where par_no = 23;
select count(*) from pb_join where par_no = 23; 

alter table partner rename column del to chk;
create sequence pc_seq
	increment by 1
	start with 1
	maxvalue 9999
	minvalue 1;


alter sequence partner_seq increament by -1; 
alter table partner modify camp_area varchar(30);
insert into partner values(partner_seq.nextval,'bb캠핑','차박가실분',
	0,sysdate,'2022-05-08','2022-05-09','서울','차박',4,1,'raichu',0);