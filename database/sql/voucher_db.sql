-- Dumping structure for table voucher_db.voucher
CREATE TABLE IF NOT EXISTS `voucher`
(
    `code`         varchar(255) NOT NULL,
    `brand_id`     bigint        DEFAULT NULL,
    `created_at`   datetime(6)   DEFAULT NULL,
    `description`  varchar(1000) DEFAULT NULL,
    `discount`     double        DEFAULT NULL,
    `expired_date` datetime(6)   DEFAULT NULL,
    `image`        varchar(255)  DEFAULT NULL,
    `qr_code`      varchar(255)  DEFAULT NULL,
    `status`       varchar(255)  DEFAULT NULL,
    PRIMARY KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping structure for table voucher_db.user_voucher
CREATE TABLE IF NOT EXISTS `user_voucher`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `add_to_time` datetime(6)  DEFAULT NULL,
    `status`      varchar(255) DEFAULT NULL,
    `user_id`     bigint       DEFAULT NULL,
    `voucher_id`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK5llb4x2ixiwa75csgei7hbl5r` (`voucher_id`),
    CONSTRAINT `FK5llb4x2ixiwa75csgei7hbl5r` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Dumping structure for table voucher_db.voucher_campaign
CREATE TABLE IF NOT EXISTS `voucher_campaign`
(
    `campaign_id` bigint       NOT NULL,
    `remaining`   int DEFAULT NULL,
    `total`       int DEFAULT NULL,
    `voucher_id`  varchar(255) NOT NULL,
    PRIMARY KEY (`campaign_id`, `voucher_id`),
    KEY `FK3n9oxj4lfhr9c3koi6e7r91jl` (`voucher_id`),
    CONSTRAINT `FK3n9oxj4lfhr9c3koi6e7r91jl` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `voucher` (`code`, `brand_id`, `created_at`, `description`, `discount`, `expired_date`, `image`, `qr_code`,
                       `status`)
VALUES ('1', 1, '2024-12-28 14:34:55.000000', 'Discount for Hat', 10, '2024-12-28 14:34:59.000000', NULL, NULL, NULL);

INSERT INTO `voucher_campaign` (`campaign_id`, `remaining`, `total`, `voucher_id`)
VALUES (1, 97, 100, '1');

INSERT INTO `user_voucher` (`id`, `add_to_time`, `status`, `user_id`, `voucher_id`)
VALUES (10, '2024-12-29 18:13:29.343000', 'ACTIVE', 2, '1'),
       (11, '2024-12-29 18:13:37.436000', 'ACTIVE', 2, '1'),
       (12, '2024-12-29 20:26:28.856000', 'ACTIVE', 2, '1');