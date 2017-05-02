create database 1412573_hibernate;

use 1412573_hibernate;

# --------------------------------------------------------------------------- #
# --------------------------------------------------------------------------- #
# Tables

# Subject name table
create table subject_name (
	id int not null auto_increment,
    name nvarchar(50) not null,
    primary key (id)
);
# Subject table
create table subject (
	id varchar(7) not null,
    name_id int not null,
    classroom varchar(5),
    start_date datetime,
    end_date datetime,
    number_students int,
    is_expired bit,
    percent_attendance double,
    primary key (id, name_id),
    foreign key (name_id) references subject_name(id)
);
# Student table
create table student (
	id varchar(7) not null,
    fullname varchar(50) not null,
    schoolyear int,
    faculty varchar(50),
    birthday date,
    primary key (id)
);
# Student attendance information table
create table attendance (
	id int not null,
    checked_date date not null,
    primary key (id, checked_date)
);
# Subject, student and attendance information table
create table subject_student (
	subject_id varchar(7) not null,
    subject_name_id int not null,
    student_id varchar(7) not null,
    attendance_id int not null,
    primary key (subject_id, student_id),
    foreign key (subject_id) references subject(id),
    foreign key (subject_name_id) references subject(name_id),
    foreign key (student_id) references student(id)
);
# GiaoVu account table
create table giaovu_login (
	user_id varchar(7) not null,
    password varchar(512) not null,
    primary key (user_id)
);
# Student account table
create table student_login (
	user_id varchar(7) not null,
    password varchar(512) not null,
    primary key (user_id)
);
# Draft table for auto_increment
create table generate_id (
	id int auto_increment not null,
    primary key (id)
);
# --------------------------------------------------------------------------- #
# --------------------------------------------------------------------------- #
# Attendance procedures and functions

# Insert values for SubjectStudent table (with auto generated attendance_id)
DELIMITER $$
create procedure insert_subject_student (IN subject_id varchar(7), IN subject_name_id int,
										 IN student_id varchar(7))
begin
	declare auto_id long;
	set auto_id = (select AUTO_INCREMENT from information_schema.tables
			where table_schema= "1412573_hibernate" and table_name= "generate_id");
    insert into generate_id values(); # increase id
    delete from generate_id where id = auto_id;
	insert into subject_student values(subject_id, subject_name_id, student_id, auto_id);
end $$
DELIMITER ;
# Check if we can insert attendance information at the called time.
DELIMITER $$
create function is_can_insert_attendance(start_time long, time_after_30_minutes long)
returns bit
begin
	declare cur_time long default UNIX_TIMESTAMP();
    set cur_time = UNIX_TIMESTAMP();
    if ((start_time <= cur_time) && (cur_time <= time_after_30_minutes)) then
		return 1;
	end if;
    return 0;
end $$
DELIMITER ;
# Update attendance percent on Subject table
# Just for future, so we should require subject_id parameter
DELIMITER $$
create procedure update_attendance_percent (IN current_week int, IN subject_id varchar(7),
											IN subject_name_id int)
begin
	declare nStudent long;
    declare nAttendance long;
    declare percent double;
    
    set nStudent = (select count(*) from subject_student ss where ss.subject_id = subject_id and
					ss.subject_name_id = subject_name_id);
    set nAttendance = (select count(*) from attendance a where a.id = ANY (
						select ss.attendance_id from subject_student ss where 
                        ss.subject_id = subject_id and ss.subject_name_id = subject_name_id));
	set percent = (nAttendance * 100.0) / (nStudent * current_week);
    update subject s set s.percent_attendance = percent
		where s.id = subject_id and s.name_id = subject_name_id;
end $$
DELIMITER ;
# --------------------------------------------------------------------------- #
# --------------------------------------------------------------------------- #
# GiaoVu account procedures and functions

# Check if password matchs with username
DELIMITER $$
create function match_giaovu_acc (username varchar(7), pass varchar(50))
returns bit
begin
	declare hashed_pass varchar(512);
	set hashed_pass = (select l.password from giaovu_login l where l.user_id = username);
    
    if hashed_pass is not null then
		if length(pass) < 10 then
			return hashed_pass = sha2(pass, 384);
		else
			return hashed_pass = sha2(pass, 512);
		end if;
    end if;
    
    return 0;
end $$
DELIMITER ;
# --------------------------------------------------------------------------- #
# Insert student account to DB
DELIMITER $$
create procedure insert_giaovu_acc (IN username varchar(7), IN pass varchar(50))
begin
	if (select exists(select 1 from giaovu_login l where l.user_id = username) = 0) then
		if length(pass) < 10 then
			insert into giaovu_login values(username, sha2(pass, 384));
		else
			insert into giaovu_login values(username, sha2(pass, 512));
		end if;
    end if;
end $$
DELIMITER ;
# --------------------------------------------------------------------------- #
# Update GiaoVu account
DELIMITER $$
create function update_giaovu_acc (username varchar(7), old_pass varchar(50), new_pass varchar(50))
returns bit
begin
	declare hashed_pass varchar(512);
	
	if match_giaovu_acc(username, old_pass) then
		set hashed_pass = (select l.password from giaovu_login l where l.user_id = username);
        
		if length(new_pass) < 10 then
			update giaovu_login set password = sha2(new_pass, 384) where user_id = username;
		else
			update giaovu_login set password = sha2(new_pass, 512) where user_id = username;
		end if;
        
		return 1;
	end if;
    
    return 0;
end $$
DELIMITER ;
# --------------------------------------------------------------------------- #
# --------------------------------------------------------------------------- #
# Student account procedures and functions

# Check if password matchs with username
DELIMITER $$
create function match_student_acc (username varchar(7), pass varchar(50))
returns bit
begin
	declare hashed_pass varchar(512);
	set hashed_pass = (select l.password from student_login l where l.user_id = username);
    
    if hashed_pass is not null then
		if length(pass) < 10 then
			return hashed_pass = sha2(pass, 384);
		else
			return hashed_pass = sha2(pass, 512);
		end if;
    end if;
    
    return 0;
end $$
DELIMITER ;
# --------------------------------------------------------------------------- #
# Insert student account to DB
DELIMITER $$
create procedure insert_student_acc (IN username varchar(7), IN pass varchar(50))
begin
	if (select exists(select 1 from student_login l where l.user_id = username) = 0) then
		if length(pass) < 10 then
			insert into student_login values(username, sha2(pass, 384));
		else
			insert into student_login values(username, sha2(pass, 512));
		end if;
    end if;
end $$
DELIMITER ;
# --------------------------------------------------------------------------- #
# Update student account
DELIMITER $$
create function update_student_acc (username varchar(7), old_pass varchar(50), new_pass varchar(50))
returns bit
begin
	declare hashed_pass varchar(512);
	
	if match_student_acc(username, old_pass) then
		set hashed_pass = (select l.password from student_login l where l.user_id = username);
        
		if length(new_pass) < 10 then
			update student_login set password = sha2(new_pass, 384) where user_id = username;
		else
			update student_login set password = sha2(new_pass, 512) where user_id = username;
		end if;
        
		return 1;
	end if;
    
    return 0;
end $$
DELIMITER ;

# Create default GiaoVu with username: 1412573, password: daominhtri
call insert_giaovu_acc('1412573', 'daominhtri');
