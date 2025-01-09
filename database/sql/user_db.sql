-- Dumping structure for table user_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `facebook_link` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `turn_num` int DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.user: ~2 rows (approximately)
INSERT INTO `user` (`id`, `avatar`, `created_at`, `dob`, `email`, `facebook_link`, `full_name`, `gender`, `password`, `phone_number`, `role`, `status`, `turn_num`, `username`) VALUES
    (3, NULL, '2025-01-03 10:40:28.919000', '2003-03-01 06:00:00.000000', 'phuonghieuto@gmail.com', NULL, 'To Phuong Hieu', 'MALE', '$2a$10$glpJ1SsXNJQIG/4TD5mNFO2757IQA3e98MQKTfRC06wMDCG1QetNO', '0123456789', 'ADMIN', 'ACTIVE', '0', 'phuonghieuto');
INSERT INTO `user` (`id`, `avatar`, `created_at`, `dob`, `email`, `facebook_link`, `full_name`, `gender`, `password`, `phone_number`, `role`, `status`, `turn_num`, `username`) VALUES
                                                                                                                                                                                    (4, 'https://example.com/avatars/user1.jpg', '2025-01-02 09:00:00.000000', '1995-06-15 00:00:00.000000', 'john.doe@example.com', 'https://facebook.com/john.doe', 'John Doe', 'Male', '$2a$10$abc123examplepasswordhash', '0987654321', 'USER', 'ACTIVE', 15, 'johndoe'),
                                                                                                                                                                                    (5, 'https://example.com/avatars/user2.jpg', '2025-01-01 14:30:00.000000', '1998-03-12 00:00:00.000000', 'jane.smith@example.com', 'https://facebook.com/jane.smith', 'Jane Smith', 'Female', '$2a$10$xyz456examplepasswordhash', '0912345678', 'USER', 'INACTIVE', 0, 'janesmith'),
                                                                                                                                                                                    (6, 'https://example.com/avatars/user3.jpg', '2025-01-03 11:00:00.000000', '2000-08-20 00:00:00.000000', 'mike.jones@example.com', NULL, 'Mike Jones', 'Male', '$2a$10$def789examplepasswordhash', '0956789123', 'BRAND', 'BANNED', 0, 'mikejones'),
                                                                                                                                                                                    (7, NULL, '2025-01-03 08:45:00.000000', '1987-11-05 00:00:00.000000', 'anna.brown@example.com', NULL, 'Anna Brown', 'Female', '$2a$10$ghi012examplepasswordhash', '0976543210', 'ADMIN', 'ACTIVE', 25, 'annabrown'),
                                                                                                                                                                                    (8, 'https://example.com/avatars/user4.jpg', '2025-01-03 09:15:00.000000', '1990-04-18 00:00:00.000000', 'peter.parker@example.com', 'https://facebook.com/peter.parker', 'Peter Parker', 'Male', '$2a$10$jkl345examplepasswordhash', '0945678910', 'USER', 'BANNED', 0, 'peterparker');