-- Dumping structure for table brand_db.branch
CREATE TABLE IF NOT EXISTS `branch`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `address`  varchar(255) DEFAULT NULL,
    `enable`   bit(1)       DEFAULT NULL,
    `location` point  NOT NULL /*!80003 SRID 4326 */,
    `name`     varchar(255) DEFAULT NULL,
    `status`   varchar(255) DEFAULT NULL,
    `brand_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKfcqv80m9yureoqml45ryy2yee` (`brand_id`),
    CONSTRAINT `FKfcqv80m9yureoqml45ryy2yee` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Dumping structure for table brand_db.brand
CREATE TABLE IF NOT EXISTS `brand`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)  DEFAULT NULL,
    `enabled`    bit(1)       DEFAULT NULL,
    `field`      varchar(255) DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    `status`     varchar(255) DEFAULT NULL,
    `user_id`    bigint       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table vou.brand: ~1 rows (approximately)
INSERT INTO `brand` (`id`, `created_at`, `enabled`, `field`, `name`, `status`, `user_id`)
VALUES (1, '2024-12-28 14:32:32.000000', b'1', 'Fashion', 'Gucci', 'ACTIVE', 2);

INSERT INTO `brand` (`id`, `created_at`, `enabled`, `field`, `name`, `status`, `user_id`)
VALUES (2, '2024-12-29 10:15:00.000000', b'1', 'Technology', 'Apple', 'ACTIVE', 3),
       (3, '2024-12-30 09:00:00.000000', b'0', 'Fashion', 'H&M', 'INACTIVE', 4),
       (4, '2024-12-30 11:45:00.000000', b'1', 'Automotive', 'Tesla', 'ACTIVE', 5),
       (5, '2024-12-31 08:20:00.000000', b'0', 'Retail', 'Walmart', 'BANNED', 6),
       (6, '2024-12-31 15:30:00.000000', b'1', 'Entertainment', 'Netflix', 'ACTIVE', 7),
       (7, '2025-01-01 13:00:00.000000', b'1', 'Food & Beverage', 'Starbucks', 'INACTIVE', 8),
       (8, '2025-01-02 09:50:00.000000', b'0', 'Technology', 'Microsoft', 'BANNED', 9),
       (9, '2025-01-02 17:10:00.000000', b'1', 'Fashion', 'Louis Vuitton', 'ACTIVE', 10),
       (10, '2025-01-03 10:05:00.000000', b'1', 'Healthcare', 'Pfizer', 'INACTIVE', 11);

