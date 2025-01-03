USE `voucher_db`;

-- Dumping structure for table voucher_db.user_voucher
CREATE TABLE IF NOT EXISTS `user_voucher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `add_to_time` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `voucher_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5llb4x2ixiwa75csgei7hbl5r` (`voucher_id`),
  CONSTRAINT `FK5llb4x2ixiwa75csgei7hbl5r` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping structure for table voucher_db.voucher
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

-- Dumping structure for table voucher_db.voucher_campaign
CREATE TABLE IF NOT EXISTS `voucher_campaign` (
  `campaign_id` bigint NOT NULL,
  `remaining` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `voucher_id` varchar(255) NOT NULL,
  PRIMARY KEY (`campaign_id`,`voucher_id`),
  KEY `FK3n9oxj4lfhr9c3koi6e7r91jl` (`voucher_id`),
  CONSTRAINT `FK3n9oxj4lfhr9c3koi6e7r91jl` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
