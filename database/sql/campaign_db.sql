-- Dumping structure for table campaign_db.campaign
-- Dumping structure for table campaign_db.campaign
CREATE TABLE IF NOT EXISTS `campaign`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `brand_id`    bigint       DEFAULT NULL,
    `created_at`  datetime(6)  DEFAULT NULL,
    `description` TEXT         DEFAULT NULL,
    `end_date`    datetime(6)  DEFAULT NULL,
    `field`       varchar(255) DEFAULT NULL,
    `image`       varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `start_date`  datetime(6)  DEFAULT NULL,
    `status`      varchar(255) DEFAULT NULL,
    `note`        varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping structure for table campaign_db.favourite_campaign_user
CREATE TABLE IF NOT EXISTS `favourite_campaign_user`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `add_to_time` datetime(6) DEFAULT NULL,
    `user_id`     bigint      DEFAULT NULL,
    `campaign_id` bigint      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKp15m86nq945fl7ylxlhs6coct` (`campaign_id`),
    CONSTRAINT `FKp15m86nq945fl7ylxlhs6coct` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table vou.campaign: ~2 rows (approximately)
-- Dumping data for table campaign_db.campaign: ~5 rows (approximately)
INSERT INTO `campaign` (`id`, `created_at`, `end_date`, `image`, `name`, `start_date`, `status`, `brand_id`, `field`, `description`)
VALUES
(3, '2025-12-20 09:00:00.000000', '2025-12-31 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/52e9d44a4f52ad14f1dc8460962e33791c3ad6e04e507441722a72dd914fc7_640.jpg', 'Holiday Season Deals', '2025-12-22 00:00:00.000000', 'PENDING', 2, 'Retail', 'Enjoy amazing discounts and special offers during the holiday season. Shop now and save big on your favorite products!'),
(4, '2025-11-01 15:00:00.000000', '2025-11-15 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/54e3dc4b4250b10ff3d8992cc12c30771037dbf85254784b722673dc9148_640.jpg', 'Black Friday Discounts', '2025-11-05 00:00:00.000000', 'REJECTED', 4, 'Technology', 'Don\'t miss out on our Black Friday deals! Get the best prices on electronics, fashion, and more. Limited time only!'),
(5, '2025-01-01 08:00:00.000000', '2025-01-10 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/50e2d1414852b10ff3d8992cc12c30771037dbf852547940772c7ed59f4f_640.jpg', 'New Year Promotions', '2025-01-01 00:00:00.000000', 'COMPLETED', 1, 'Electronics', 'Ring in the new year with fantastic promotions and exclusive deals. Start the year off right with great savings!'),
(6, '2025-12-10 10:00:00.000000', '2025-12-25 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/5fe7dd404255b10ff3d8992cc12c30771037dbf85254794174267fdd9e44_640.jpg', 'Winter Clearance Sale', '2025-12-12 00:00:00.000000', 'ACTIVE', 3, 'Fashion', 'Clear out the winter stock with our clearance sale. Find incredible bargains on winter apparel and accessories.'),
(7, '2025-01-02 12:00:00.000000', '2025-01-15 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/53e2d7464f57b10ff3d8992cc12c30771037dbf85254784973267cd49344_640.jpg', 'Back to School Offers', '2025-01-03 00:00:00.000000', 'ACTIVE', 5, 'Education Supplies', 'Get ready for the new school year with our back-to-school offers. Save on school supplies, clothing, and more!');

INSERT INTO `campaign` (`id`, `created_at`, `end_date`, `image`, `name`, `start_date`, `status`, `brand_id`, `field`, `description`)
VALUES
    (8, '2025-12-15 10:00:00.000000', '2025-12-31 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/53e2d7454854a514f1dc8460962e33791c3ad6e04e50744077287ad29f4ec4_640.jpg', 'Year-End Electronics Blowout', '2025-12-20 00:00:00.000000', 'PENDING', 6, 'Electronics', 'Don’t miss our year-end blowout sale on all electronics. Get amazing deals on gadgets, appliances, and more!'),
    (9, '2025-11-20 09:30:00.000000', '2025-12-10 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/53e2d64b495aa514f1dc8460962e33791c3ad6e04e50744172297cdd964bc4_640.jpg', 'Winter Wonders Sale', '2025-11-25 00:00:00.000000', 'APPROVED', 2, 'Retail', 'Step into the holiday spirit with our Winter Wonders Sale. Discounts across all departments to make your season bright!'),
    (10, '2025-10-10 14:00:00.000000', '2025-10-31 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/52e1d5444c53b10ff3d8992cc12c30771037dbf85254794e732c72d5904e_640.jpg', 'Halloween Clearance Event', '2025-10-15 00:00:00.000000', 'REJECTED', 3, 'Fashion', 'Get spooky deals on costumes, accessories, and Halloween decorations while supplies last!'),
    (11, '2025-02-01 11:00:00.000000', '2025-02-14 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/53e2d340485ab10ff3d8992cc12c30771037dbf85254794e732f7ed49148_640.jpg', 'Valentine’s Day Specials', '2025-02-05 00:00:00.000000', 'CANCELED', 7, 'Gifts', 'Celebrate love with our Valentine’s Day Specials. Offers on chocolates, flowers, and personalized gifts!'),
    (12, '2025-01-15 10:30:00.000000', '2025-01-31 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/53e2d5424854b10ff3d8992cc12c30771037dbf85254794e722e7ed6974a_640.jpg', 'Fitness New Year Sale', '2025-01-18 00:00:00.000000', 'ACTIVE', 8, 'Fitness', 'New Year, new you! Enjoy discounts on gym memberships, workout gear, and fitness accessories. Start the year strong!'),
    (13, '2025-12-01 09:00:00.000000', '2025-12-24 23:59:59.000000', 'https://randomwordgenerator.com/img/picture-generator/54e8d34b4f5bb10ff3d8992cc12c30771037dbf85254784972277ed69248_640.jpg', 'Holiday Countdown Deals', '2025-12-05 00:00:00.000000', 'COMPLETED', 9, 'Retail', 'Count down to the holidays with exclusive daily deals. Don’t miss out on these limited-time offers!');

