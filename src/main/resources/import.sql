
-- 회원
insert into member (`email`, `password`, `name`) values ('super@super.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'super');
insert into member (`email`, `password`, `name`) values ('admin@admin.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin');
insert into member (`email`, `password`, `name`) values ('user@user.com', '$2a$10$qWRAdGb6fewmg1wFFxk9SuEwDw9/nzWLqXUTHMuPPw8rz0BGkeWaG', 'user');

-- 건강정보
insert into member_health (`member_id`) values (1);
insert into member_health (`member_id`) values (2);
insert into member_health (`member_id`) values (3);

-- 권한
insert into authority (`authority_name`) values ('ROLE_SUPER');
insert into authority (`authority_name`) values ('ROLE_ADMIN');
insert into authority (`authority_name`) values ('ROLE_USER');
insert into authority (`authority_name`) values ('ROLE_HOSPITAL');

-- 회원별 권한
insert into member_authority (`member_id`, `authority_id`) values (1, 1);
insert into member_authority (`member_id`, `authority_id`) values (1, 2);
insert into member_authority (`member_id`, `authority_id`) values (1, 3);
insert into member_authority (`member_id`, `authority_id`) values (2, 2);
insert into member_authority (`member_id`, `authority_id`) values (2, 3);
insert into member_authority (`member_id`, `authority_id`) values (3, 3);

-- 병원
insert into hospital (`hash_code`, `name`, `department`, `city`, `street`, `zip_code`, `phone_number`) values ('test00001', '아무안과', 'EYE', 'Seoul', 'Jong-ro', '12345', '021234567');
insert into hospital (`hash_code`, `name`, `department`, `city`, `street`, `zip_code`, `phone_number`) values ('test00002', '아무내과', 'INTERNAL', 'Seoul', 'Jong-ro', '12345', '021234567');
insert into hospital (`hash_code`, `name`, `department`, `city`, `street`, `zip_code`, `phone_number`) values ('test00003', '아무치과', 'DENTAL', 'Seoul', 'Jong-ro', '12345', '021234567');

-- 병원 연결
insert into member_hospital (`member_id`, `hospital_id`) values (1, 1);
insert into member_hospital (`member_id`, `hospital_id`) values (1, 2);
insert into member_hospital (`member_id`, `hospital_id`) values (2, 1);
insert into member_hospital (`member_id`, `hospital_id`) values (3, 1);

-- 혈당 측정정보
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 1, 'AUTOMATIC', 'LUNCH', 'FASTED', 123);
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 1, 'AUTOMATIC', 'LUNCH', 'AFTER_MEAL', 140);
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 1, 'MANUAL', 'DINNER', 'FASTED', 123);
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 1, 'MANUAL', 'DINNER', 'FASTED', 150);
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 2, 'MANUAL', 'DINNER', 'FASTED', 123);
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 2, 'MANUAL', 'DINNER', 'FASTED', 150);
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 3, 'AUTOMATIC', 'LUNCH', 'FASTED', 123);
insert into measurement (`dtype`, `member_id`, `manual`, `glucose_meal`, `glucose_fasted`, `glucose_value`) values ('GLU', 3, 'MANUAL', 'LUNCH', 'AFTER_MEAL', 140);

-- 혈압 측정정보
insert into measurement (`dtype`, `member_id`, `manual`, `pressure_dias`, `pressure_sys`) values ('PRE', 1, 'AUTOMATIC', 100, 123);
insert into measurement (`dtype`, `member_id`, `manual`, `pressure_dias`, `pressure_sys`) values ('PRE', 1, 'AUTOMATIC', 100, 123);
insert into measurement (`dtype`, `member_id`, `manual`, `pressure_dias`, `pressure_sys`) values ('PRE', 1, 'MANUAL', 100, 123);
insert into measurement (`dtype`, `member_id`, `manual`, `pressure_dias`, `pressure_sys`) values ('PRE', 2, 'AUTOMATIC', 100, 123);
insert into measurement (`dtype`, `member_id`, `manual`, `pressure_dias`, `pressure_sys`) values ('PRE', 2, 'MANUAL', 100, 123);
insert into measurement (`dtype`, `member_id`, `manual`, `pressure_dias`, `pressure_sys`) values ('PRE', 3, 'AUTOMATIC', 100, 123);
insert into measurement (`dtype`, `member_id`, `manual`, `pressure_dias`, `pressure_sys`) values ('PRE', 3, 'MANUAL', 100, 123);