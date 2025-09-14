DROP DATABASE IF EXISTS driving_school;
CREATE DATABASE IF NOT EXISTS driving_school;
USE driving_school;


CREATE TABLE instructor (
instructor_id   VARCHAR(10) PRIMARY KEY,
first_name      VARCHAR(200),
last_name       VARCHAR(200),
email           VARCHAR(100) UNIQUE,
phone           VARCHAR(15),
specialization  VARCHAR(100),
availability_schedule VARCHAR(100)
);

-- Student
CREATE TABLE student (
 student_id        VARCHAR(10) PRIMARY KEY,
first_name        VARCHAR(200),
last_name         VARCHAR(200),
email             VARCHAR(100) UNIQUE,
phone             VARCHAR(15),
registration_date DATE NOT NULL
);

-- Course
CREATE TABLE course (
course_id     VARCHAR(10) PRIMARY KEY,
course_name   VARCHAR(200),
duration      VARCHAR(100),
fee           DECIMAL(10,2),
description   VARCHAR(200),
instructor_id VARCHAR(10),
FOREIGN KEY (instructor_id) REFERENCES instructor(instructor_id)
);

-- Student-Course (Many-to-Many)
CREATE TABLE student_course (
student_course_id VARCHAR(10) PRIMARY KEY,
enrollment_date DATE NOT NULL,
status VARCHAR(50),
grade VARCHAR(10),
student_id VARCHAR(10),
course_id  VARCHAR(10),
FOREIGN KEY (student_id) REFERENCES student(student_id),
FOREIGN KEY (course_id) REFERENCES course(course_id),
UNIQUE(student_id, course_id) -- prevent duplicate enrollments
);

-- Lessons
CREATE TABLE lessons (
lesson_id     VARCHAR(10) PRIMARY KEY,
lesson_date   DATE NOT NULL,
start_time    TIME NOT NULL,
end_time      TIME NOT NULL,
status        VARCHAR(50),
student_id    VARCHAR(10),
course_id     VARCHAR(10),
instructor_id VARCHAR(10),
FOREIGN KEY (student_id) REFERENCES student(student_id),
FOREIGN KEY (course_id)  REFERENCES course(course_id),
FOREIGN KEY (instructor_id) REFERENCES instructor(instructor_id)
);

-- Payment
CREATE TABLE payment (
payment_id     VARCHAR(10) PRIMARY KEY,
payment_date   DATE NOT NULL,
amount         DECIMAL(10,2),
payment_method VARCHAR(50),
status         VARCHAR(50),
student_id     VARCHAR(10),
FOREIGN KEY (student_id) REFERENCES student(student_id)
);

-- User (Admin / Receptionist)
CREATE TABLE user (
user_id   VARCHAR(10) PRIMARY KEY,
user_name VARCHAR(200) UNIQUE,
password  VARCHAR(200),
role      VARCHAR(50), -- ADMIN / RECEPTIONIST
email     VARCHAR(100) UNIQUE,
status    VARCHAR(50)
);