
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
