
-- Dumping structure for table campaign_db.campaign
CREATE TABLE IF NOT EXISTS `campaign` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `field` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping structure for table campaign_db.favourite_campaign_user
CREATE TABLE IF NOT EXISTS `favourite_campaign_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `add_to_time` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `campaign_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp15m86nq945fl7ylxlhs6coct` (`campaign_id`),
  CONSTRAINT `FKp15m86nq945fl7ylxlhs6coct` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table vou.campaign: ~2 rows (approximately)
INSERT INTO `campaign` (`id`, `created_at`, `end_date`, `filed_id`, `image`, `name`, `start_date`, `status`, `brand_id`, `field`) VALUES
                                                                                                                                      (3, '2024-12-20 09:00:00.000000', '2024-12-31 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/52e9d44a4f52ad14f1dc8460962e33791c3ad6e04e507441722a72dd914fc7_640.jpg', 'Holiday Season Deals', '2024-12-22 00:00:00.000000', 'PENDING', 2, 'Retail'),
                                                                                                                                      (4, '2024-12-10 10:00:00.000000', '2024-12-25 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/5fe7dd404255b10ff3d8992cc12c30771037dbf85254794174267fdd9e44_640.jpg', 'Winter Clearance Sale', '2024-12-12 00:00:00.000000', 'ACTIVE', 3, 'Fashion'),
                                                                                                                                      (5, '2025-01-01 08:00:00.000000', '2025-01-10 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/50e2d1414852b10ff3d8992cc12c30771037dbf852547940772c7ed59f4f_640.jpg', 'New Year Promotions', '2025-01-01 00:00:00.000000', 'ENDED', 1, 'Electronics'),
                                                                                                                                      (6, '2024-11-01 15:00:00.000000', '2024-11-15 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/54e3dc4b4250b10ff3d8992cc12c30771037dbf85254784b722673dc9148_640.jpg', 'Black Friday Discounts', '2024-11-05 00:00:00.000000', 'REJECTED', 4, 'Technology'),
                                                                                                                                      (7, '2025-01-02 12:00:00.000000', '2025-01-15 23:59:59.000000', null, 'https://randomwordgenerator.com/img/picture-generator/53e2d7464f57b10ff3d8992cc12c30771037dbf85254784973267cd49344_640.jpg', 'Back to School Offers', '2025-01-03 00:00:00.000000', 'ACTIVE', 5, 'Education Supplies');

