CREATE DATABASE IF NOT EXISTS attendance_db;
USE attendance_db;

CREATE TABLE students (
    student_id    INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    enrollment_no VARCHAR(20) UNIQUE NOT NULL,
    password      VARCHAR(50) NOT NULL
);

CREATE TABLE subjects (
    subject_id     INT AUTO_INCREMENT PRIMARY KEY,
    subject_name   VARCHAR(100) NOT NULL,
    total_lectures INT NOT NULL
);

CREATE TABLE enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id    INT,
    subject_id    INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

CREATE TABLE attendance_records (
    record_id     INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT,
    date          DATE NOT NULL,
    status        ENUM('PRESENT','ABSENT') NOT NULL,
    FOREIGN KEY (enrollment_id) REFERENCES enrollments(enrollment_id)
);

CREATE TABLE planned_holidays (
    holiday_id   INT AUTO_INCREMENT PRIMARY KEY,
    student_id   INT,
    subject_id   INT,
    holiday_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

-- Data for testing
INSERT INTO subjects VALUES (1,'Mathematics',60),(2,'Data Structures',50),(3,'DBMS',45);
INSERT INTO students VALUES (1,'Test Student','EN001','1234');
INSERT INTO enrollments VALUES (1,1,1),(2,1,2),(3,1,3);