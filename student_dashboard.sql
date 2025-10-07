CREATE TABLE `Student` (
  `student_id` int PRIMARY KEY AUTO_INCREMENT,
  `full_name` varchar(100),
  `roll_number` varchar(20) UNIQUE,
  `gender` varchar(10),
  `dob` date,
  `email` varchar(100) UNIQUE,
  `contact_no` varchar(20),
  `class_year` varchar(50),
  `department` varchar(100),
  `program` varchar(100),
  `skills` text,
  `last_sem_cgpa` decimal(3,2),
  `password` varchar(100)
);

CREATE TABLE `Faculty` (
  `faculty_id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100),
  `email` varchar(100) UNIQUE,
  `password` varchar(100),
  `department` varchar(100),
  `contact_no` varchar(20)
);

CREATE TABLE `Parent` (
  `parent_id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100),
  `email` varchar(100) UNIQUE,
  `password` varchar(100),
  `contact_no` varchar(20),
  `student_id` int
);

CREATE TABLE `Course` (
  `course_id` int PRIMARY KEY AUTO_INCREMENT,
  `course_name` varchar(100),
  `credits` int,
  `department` varchar(100),
  `faculty_id` int
);

CREATE TABLE `Enrollment` (
  `enrollment_id` int PRIMARY KEY AUTO_INCREMENT,
  `student_id` int,
  `course_id` int,
  `semester` varchar(20)
);

CREATE TABLE `Attendance` (
  `attendance_id` int PRIMARY KEY AUTO_INCREMENT,
  `student_id` int,
  `course_id` int,
  `date` date,
  `status` varchar(10)
);

CREATE TABLE `Test` (
  `test_id` int PRIMARY KEY AUTO_INCREMENT,
  `course_id` int,
  `faculty_id` int,
  `test_date` date,
  `total_marks` int
);

CREATE TABLE `Result` (
  `result_id` int PRIMARY KEY AUTO_INCREMENT,
  `student_id` int,
  `test_id` int,
  `marks_obtained` int,
  `grade` varchar(5)
);

ALTER TABLE `Parent` ADD FOREIGN KEY (`student_id`) REFERENCES `Student` (`student_id`);

ALTER TABLE `Course` ADD FOREIGN KEY (`faculty_id`) REFERENCES `Faculty` (`faculty_id`);

ALTER TABLE `Enrollment` ADD FOREIGN KEY (`student_id`) REFERENCES `Student` (`student_id`);

ALTER TABLE `Enrollment` ADD FOREIGN KEY (`course_id`) REFERENCES `Course` (`course_id`);

ALTER TABLE `Attendance` ADD FOREIGN KEY (`student_id`) REFERENCES `Student` (`student_id`);

ALTER TABLE `Attendance` ADD FOREIGN KEY (`course_id`) REFERENCES `Course` (`course_id`);

ALTER TABLE `Test` ADD FOREIGN KEY (`course_id`) REFERENCES `Course` (`course_id`);

ALTER TABLE `Test` ADD FOREIGN KEY (`faculty_id`) REFERENCES `Faculty` (`faculty_id`);

ALTER TABLE `Result` ADD FOREIGN KEY (`student_id`) REFERENCES `Student` (`student_id`);

ALTER TABLE `Result` ADD FOREIGN KEY (`test_id`) REFERENCES `Test` (`test_id`);
