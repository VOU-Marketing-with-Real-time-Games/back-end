-- Dumping structure for table brand_db.brand
CREATE TABLE IF NOT EXISTS `brand`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)  DEFAULT NULL,
    `enabled`    bit(1)       DEFAULT NULL,
    `field`      varchar(255) DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    `user_id`    bigint       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table brand_db.brand
INSERT INTO `brand` (`id`, `created_at`, `enabled`, `field`, `name`, `user_id`)
VALUES
    (1, '2024-12-28 14:32:32.000000', b'1', 'Fashion', 'Gucci', 2),
    (2, '2024-12-29 10:15:00.000000', b'1', 'Technology', 'Apple', 3),
    (3, '2024-12-30 09:00:00.000000', b'0', 'Fashion', 'H&M', 4),
    (4, '2024-12-30 11:45:00.000000', b'1', 'Automotive', 'Tesla', 5),
    (5, '2024-12-31 08:20:00.000000', b'0', 'Retail', 'Walmart', 6),
    (6, '2024-12-31 15:30:00.000000', b'1', 'Entertainment', 'Netflix', 7),
    (7, '2025-01-01 13:00:00.000000', b'1', 'Food & Beverage', 'Starbucks', 8),
    (8, '2025-01-02 09:50:00.000000', b'0', 'Technology', 'Microsoft', 9),
    (9, '2025-01-02 17:10:00.000000', b'1', 'Fashion', 'Louis Vuitton', 10),
    (10, '2025-01-03 10:05:00.000000', b'1', 'Healthcare', 'Pfizer', 11);

-- Dumping structure for table brand_db.branch
CREATE TABLE IF NOT EXISTS `branch`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `address`  varchar(255) DEFAULT NULL,
    `location` point  NOT NULL /*!80003 SRID 4326 */,
    `name`     varchar(255) DEFAULT NULL,
    `brand_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKfcqv80m9yureoqml45ryy2yee` (`brand_id`),
    CONSTRAINT `FKfcqv80m9yureoqml45ryy2yee` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table brand_db.branch
INSERT INTO `branch` (`id`, `name`, `address`, `location`, `brand_id`)
VALUES
    (1, 'Gucci NY', '123 Main St, New York, NY', ST_GeomFromText('POINT(10.82302 106.62965)', 4326), 1),
    (2, 'Apple HQ', '1 Infinite Loop, Cupertino, CA', ST_GeomFromText('POINT(11.82302 106.629650)', 4326), 1),
    (3, 'H&M SF', '456 Market St, San Francisco, CA', ST_GeomFromText('POINT(10.82302 107.329650)', 4326), 1),
    (4, 'Tesla HQ', '3500 Deer Creek Rd, Palo Alto, CA', ST_GeomFromText('POINT(10.82302 108.629650)', 4326), 1),
    (5, 'Walmart HQ', '702 SW 8th St, Bentonville, AR', ST_GeomFromText('POINT(9.82302 106.629650)', 4326), 2),
    (6, 'Netflix HQ', '100 Winchester Cir, Los Gatos, CA', ST_GeomFromText('POINT(10.92302 106.729650)', 4326), 2),
    (7, 'Starbucks HQ', '2401 Utah Ave S, Seattle, WA', ST_GeomFromText('POINT(10.89302 106.929650)', 4326), 2),
    (8, 'Microsoft HQ', '1 Microsoft Way, Redmond, WA', ST_GeomFromText('POINT(10.55302 107.649650)', 4326), 3),
    (9, 'Louis Vuitton Paris', '22 Avenue Montaigne, Paris, France', ST_GeomFromText('POINT(10.82302 106.629650)', 4326), 2),
    (10, 'Pfizer HQ', '235 E 42nd St, New York, NY', ST_GeomFromText('POINT(10.82302 106.629650)', 4326), 4);
