-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.33 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.4.0.6659
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for vou
CREATE DATABASE IF NOT EXISTS `vou` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `vou`;

-- Dumping structure for table vou.branch
CREATE TABLE IF NOT EXISTS `branch` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `location` point NOT NULL /*!80003 SRID 4326 */,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfcqv80m9yureoqml45ryy2yee` (`brand_id`),
  CONSTRAINT `FKfcqv80m9yureoqml45ryy2yee` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.branch: ~0 rows (approximately)

-- Dumping structure for table vou.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `field` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.brand: ~1 rows (approximately)
INSERT INTO `brand` (`id`, `created_at`, `enabled`, `field`, `name`, `status`, `user_id`) VALUES
	(1, '2024-12-28 14:32:32.000000', b'1', 'Fashion', 'Gucci', 'ACTIVE', 2);

INSERT INTO `brand` (`id`, `created_at`, `enabled`, `field`, `name`, `status`, `user_id`) VALUES
    (2, '2024-12-29 10:15:00.000000', b'1', 'Technology', 'Apple', 'ACTIVE', 3),
    (3, '2024-12-30 09:00:00.000000', b'0', 'Fashion', 'H&M', 'INACTIVE', 4),
    (4, '2024-12-30 11:45:00.000000', b'1', 'Automotive', 'Tesla', 'ACTIVE', 5),
    (5, '2024-12-31 08:20:00.000000', b'0', 'Retail', 'Walmart', 'BANNED', 6),
    (6, '2024-12-31 15:30:00.000000', b'1', 'Entertainment', 'Netflix', 'ACTIVE', 7),
    (7, '2025-01-01 13:00:00.000000', b'1', 'Food & Beverage', 'Starbucks', 'INACTIVE', 8),
    (8, '2025-01-02 09:50:00.000000', b'0', 'Technology', 'Microsoft', 'BANNED', 9),
    (9, '2025-01-02 17:10:00.000000', b'1', 'Fashion', 'Louis Vuitton', 'ACTIVE', 10),
    (10, '2025-01-03 10:05:00.000000', b'1', 'Healthcare', 'Pfizer', 'INACTIVE', 11);

-- Dumping structure for table vou.campaign
CREATE TABLE IF NOT EXISTS `campaign` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `filed_id` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `field` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.campaign: ~2 rows (approximately)
INSERT INTO `campaign` (`id`, `created_at`, `end_date`, `filed_id`, `image`, `name`, `start_date`, `status`, `brand_id`, `field`) VALUES
(3, '2024-12-20 09:00:00.000000', '2024-12-31 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/52e9d44a4f52ad14f1dc8460962e33791c3ad6e04e507441722a72dd914fc7_640.jpg', 'Holiday Season Deals', '2024-12-22 00:00:00.000000', 'PENDING', 2, 'Retail'),
(4, '2024-12-10 10:00:00.000000', '2024-12-25 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/5fe7dd404255b10ff3d8992cc12c30771037dbf85254794174267fdd9e44_640.jpg', 'Winter Clearance Sale', '2024-12-12 00:00:00.000000', 'ACTIVE', 3, 'Fashion'),
(5, '2025-01-01 08:00:00.000000', '2025-01-10 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/50e2d1414852b10ff3d8992cc12c30771037dbf852547940772c7ed59f4f_640.jpg', 'New Year Promotions', '2025-01-01 00:00:00.000000', 'ENDED', 1, 'Electronics'),
(6, '2024-11-01 15:00:00.000000', '2024-11-15 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/54e3dc4b4250b10ff3d8992cc12c30771037dbf85254784b722673dc9148_640.jpg', 'Black Friday Discounts', '2024-11-05 00:00:00.000000', 'REJECTED', 4, 'Technology'),
(7, '2025-01-02 12:00:00.000000', '2025-01-15 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/53e2d7464f57b10ff3d8992cc12c30771037dbf85254784973267cd49344_640.jpg', 'Back to School Offers', '2025-01-03 00:00:00.000000', 'ACTIVE', 5, 'Education Supplies');


-- Dumping structure for table vou.favourite_campaign_user
CREATE TABLE IF NOT EXISTS `favourite_campaign_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `add_to_time` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `campaign_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp15m86nq945fl7ylxlhs6coct` (`campaign_id`),
  CONSTRAINT `FKp15m86nq945fl7ylxlhs6coct` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.favourite_campaign_user: ~0 rows (approximately)

-- Dumping structure for table vou.game_campaign
CREATE TABLE IF NOT EXISTS `game_campaign` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `campaign_id` bigint DEFAULT NULL,
  `game_id` bigint DEFAULT NULL,
  `game_info_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK45809dorpmrru0j91f2a9dsux` (`game_info_id`),
  CONSTRAINT `FK45809dorpmrru0j91f2a9dsux` FOREIGN KEY (`game_info_id`) REFERENCES `game_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.game_campaign: ~2 rows (approximately)
INSERT INTO `game_campaign` (`id`, `campaign_id`, `game_id`, `game_info_id`) VALUES
	(1, 1, 1, 1),
	(2, 1, 1, 2);

-- Dumping structure for table vou.game_info
CREATE TABLE IF NOT EXISTS `game_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enable` bit(1) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `manual` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `game_type` enum('QUIZZ','SHAKE_GAME') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.game_info: ~2 rows (approximately)
INSERT INTO `game_info` (`id`, `enable`, `image`, `manual`, `name`, `game_type`) VALUES
	(1, b'1', '', 'manual', 'Quizz Game', 'QUIZZ'),
	(2, b'0', 'test_image1.png', 'Test manual update', 'Shake Gane', 'SHAKE_GAME');

-- Dumping structure for table vou.item
CREATE TABLE IF NOT EXISTS `item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `position` int DEFAULT NULL,
  `remaining_num` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `puzzle_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbckpr9o921do7tbft554j9qbx` (`puzzle_id`),
  CONSTRAINT `FKbckpr9o921do7tbft554j9qbx` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.item: ~2 rows (approximately)
INSERT INTO `item` (`id`, `description`, `position`, `remaining_num`, `total`, `puzzle_id`) VALUES
	(1, NULL, 1, 14, 20, 1),
	(2, NULL, 2, 12, 15, 1);

-- Dumping structure for table vou.notification_user
CREATE TABLE IF NOT EXISTS `notification_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.notification_user: ~8 rows (approximately)
INSERT INTO `notification_user` (`id`, `content`, `created_at`, `is_deleted`, `is_read`, `user_id`) VALUES
	(1, 'Test Notification', '2024-12-20 20:37:33.236000', b'1', b'0', 1),
	(2, 'Test Notification1', '2024-12-20 20:40:27.923000', b'0', b'0', 1),
	(3, 'Test Notification2', '2024-12-20 20:40:32.538000', b'0', b'0', 1),
	(11, 'You have completed the puzzle Puzzle Hat. You have received a voucher.', '2024-12-28 16:06:54.869000', b'0', b'0', 1),
	(12, 'You have completed the puzzle Puzzle Hat. You have received a voucher.', '2024-12-28 16:12:44.084000', b'0', b'0', 2),
	(13, 'You have completed the puzzle Puzzle Hat. You have received a voucher.', '2024-12-29 18:13:29.358000', b'0', b'0', 2),
	(14, 'You have completed the puzzle Puzzle Hat. You have received a voucher.', '2024-12-29 18:13:37.444000', b'0', b'0', 2),
	(15, 'You have completed the puzzle Puzzle Hat. You have received a voucher.', '2024-12-29 20:26:28.967000', b'0', b'0', 2);

-- Dumping structure for table vou.puzzle
CREATE TABLE IF NOT EXISTS `puzzle` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `campaign_game_id` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `item_num` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.puzzle: ~1 rows (approximately)
INSERT INTO `puzzle` (`id`, `campaign_game_id`, `description`, `image`, `item_num`, `name`) VALUES
	(1, 1, NULL, NULL, 2, 'Puzzle Hat');

-- Dumping structure for table vou.question
CREATE TABLE IF NOT EXISTS `question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `explaination` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `option1` varchar(255) DEFAULT NULL,
  `option2` varchar(255) DEFAULT NULL,
  `option3` varchar(255) DEFAULT NULL,
  `option4` varchar(255) DEFAULT NULL,
  `question_name` varchar(255) DEFAULT NULL,
  `quizz_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhr4c9mew80p18e985m58llxov` (`quizz_id`),
  CONSTRAINT `FKhr4c9mew80p18e985m58llxov` FOREIGN KEY (`quizz_id`) REFERENCES `quizz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.question: ~2 rows (approximately)
INSERT INTO `question` (`id`, `answer`, `explaination`, `image`, `option1`, `option2`, `option3`, `option4`, `question_name`, `quizz_id`) VALUES
	(1, 'B', '', NULL, 'A. 1', 'B. 2', 'C. 3', 'D.4', '1 + 1 = ?', 1),
	(2, 'C', NULL, NULL, 'A.1', 'B. 2', 'C. 3', 'D. 6', '1 + 2 = ?', 1);

-- Dumping structure for table vou.quizz
CREATE TABLE IF NOT EXISTS `quizz` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `campaign_game_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `second_per_question` int DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.quizz: ~1 rows (approximately)
INSERT INTO `quizz` (`id`, `campaign_game_id`, `created_at`, `description`, `name`, `second_per_question`, `start_time`) VALUES
	(1, 1, '2024-12-28 17:58:36.020000', 'This is a sample quizz.', 'Sample Quizz', 30, '2024-12-29 23:17:00.000000');

-- Dumping structure for table vou.user
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.user: ~2 rows (approximately)
INSERT INTO `user` (`id`, `avatar`, `created_at`, `dob`, `email`, `facebook_link`, `full_name`, `gender`, `password`, `phone_number`, `role`, `status`, `turn_num`, `username`) VALUES
    (3, NULL, '2025-01-03 10:40:28.919000', '2003-03-01 06:00:00.000000', 'phuonghieuto@gmail.com', NULL, 'To Phuong Hieu', 'MALE', '$2a$10$glpJ1SsXNJQIG/4TD5mNFO2757IQA3e98MQKTfRC06wMDCG1QetNO', '0123456789', 'ADMIN', 'ACTIVE', '0', 'phuonghieuto');
INSERT INTO `user` (`id`, `avatar`, `created_at`, `dob`, `email`, `facebook_link`, `full_name`, `gender`, `password`, `phone_number`, `role`, `status`, `turn_num`, `username`) VALUES
(4, 'https://example.com/avatars/user1.jpg', '2025-01-02 09:00:00.000000', '1995-06-15 00:00:00.000000', 'john.doe@example.com', 'https://facebook.com/john.doe', 'John Doe', 'Male', '$2a$10$abc123examplepasswordhash', '0987654321', 'USER', 'ACTIVE', 15, 'johndoe'),
(5, 'https://example.com/avatars/user2.jpg', '2025-01-01 14:30:00.000000', '1998-03-12 00:00:00.000000', 'jane.smith@example.com', 'https://facebook.com/jane.smith', 'Jane Smith', 'Female', '$2a$10$xyz456examplepasswordhash', '0912345678', 'USER', 'INACTIVE', 0, 'janesmith'),
(6, 'https://example.com/avatars/user3.jpg', '2025-01-03 11:00:00.000000', '2000-08-20 00:00:00.000000', 'mike.jones@example.com', NULL, 'Mike Jones', 'Male', '$2a$10$def789examplepasswordhash', '0956789123', 'BRAND', 'BANNED', 0, 'mikejones'),
(7, NULL, '2025-01-03 08:45:00.000000', '1987-11-05 00:00:00.000000', 'anna.brown@example.com', NULL, 'Anna Brown', 'Female', '$2a$10$ghi012examplepasswordhash', '0976543210', 'ADMIN', 'ACTIVE', 25, 'annabrown'),
(8, 'https://example.com/avatars/user4.jpg', '2025-01-03 09:15:00.000000', '1990-04-18 00:00:00.000000', 'peter.parker@example.com', 'https://facebook.com/peter.parker', 'Peter Parker', 'Male', '$2a$10$jkl345examplepasswordhash', '0945678910', 'USER', 'BANNED', 0, 'peterparker');

-- Dumping structure for table vou.user_answer
CREATE TABLE IF NOT EXISTS `user_answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `is_correct` bit(1) DEFAULT NULL,
  `time_answer` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  `answer_time` int DEFAULT NULL,
  `score` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpsk90eok3ounaet92hku3gny1` (`question_id`),
  CONSTRAINT `FKpsk90eok3ounaet92hku3gny1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.user_answer: ~3 rows (approximately)
INSERT INTO `user_answer` (`id`, `answer`, `is_correct`, `time_answer`, `user_id`, `question_id`, `answer_time`, `score`) VALUES
	(8, 'B', b'1', NULL, 1, 1, 10, 3),
	(9, 'B', b'1', NULL, 2, 1, 20, 6),
	(10, 'C', b'1', NULL, 1, 2, 10, 3);

-- Dumping structure for table vou.user_campaign_game
CREATE TABLE IF NOT EXISTS `user_campaign_game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_completed` bit(1) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `campaign_game_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbyhpg3morjob15ciox2ekue7h` (`campaign_game_id`),
  CONSTRAINT `FKbyhpg3morjob15ciox2ekue7h` FOREIGN KEY (`campaign_game_id`) REFERENCES `game_campaign` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.user_campaign_game: ~0 rows (approximately)

-- Dumping structure for table vou.user_item
CREATE TABLE IF NOT EXISTS `user_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total_item` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `item_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpnf7ma2ql912dg92xna4y89ix` (`item_id`),
  CONSTRAINT `FKpnf7ma2ql912dg92xna4y89ix` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.user_item: ~2 rows (approximately)
INSERT INTO `user_item` (`id`, `total_item`, `user_id`, `item_id`) VALUES
	(11, 3, 2, 1),
	(13, 0, 2, 2);

-- Dumping structure for table vou.user_voucher
CREATE TABLE IF NOT EXISTS `user_voucher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `add_to_time` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `voucher_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5llb4x2ixiwa75csgei7hbl5r` (`voucher_id`),
  CONSTRAINT `FK5llb4x2ixiwa75csgei7hbl5r` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.user_voucher: ~3 rows (approximately)
INSERT INTO `user_voucher` (`id`, `add_to_time`, `status`, `user_id`, `voucher_id`) VALUES
	(10, '2024-12-29 18:13:29.343000', 'ACTIVE', 2, '1'),
	(11, '2024-12-29 18:13:37.436000', 'ACTIVE', 2, '1'),
	(12, '2024-12-29 20:26:28.856000', 'ACTIVE', 2, '1');

-- Dumping structure for table vou.voucher
CREATE TABLE IF NOT EXISTS `voucher` (
  `code` varchar(255) NOT NULL,
  `brand_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `expired_date` datetime(6) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `qr_code` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.voucher: ~1 rows (approximately)
INSERT INTO `voucher` (`code`, `brand_id`, `created_at`, `description`, `discount`, `expired_date`, `image`, `qr_code`, `status`) VALUES
	('1', 1, '2024-12-28 14:34:55.000000', 'Discount for Hat', 10, '2024-12-28 14:34:59.000000', NULL, NULL, NULL);

-- Dumping structure for table vou.voucher_campaign
CREATE TABLE IF NOT EXISTS `voucher_campaign` (
  `campaign_id` bigint NOT NULL,
  `remaining` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `voucher_id` varchar(255) NOT NULL,
  PRIMARY KEY (`campaign_id`,`voucher_id`),
  KEY `FK3n9oxj4lfhr9c3koi6e7r91jl` (`voucher_id`),
  CONSTRAINT `FK3n9oxj4lfhr9c3koi6e7r91jl` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.voucher_campaign: ~1 rows (approximately)
INSERT INTO `voucher_campaign` (`campaign_id`, `remaining`, `total`, `voucher_id`) VALUES
	(1, 97, 100, '1');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
