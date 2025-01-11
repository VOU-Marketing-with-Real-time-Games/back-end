-- Dumping structure for table game_db.game_info
CREATE TABLE IF NOT EXISTS `game_info`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `enable`    bit(1)                      DEFAULT NULL,
    `image`     varchar(255)                DEFAULT NULL,
    `manual`    varchar(255)                DEFAULT NULL,
    `name`      varchar(255)                DEFAULT NULL,
    `game_type` enum ('QUIZZ','SHAKE_GAME') DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table vou.game_info: ~2 rows (approximately)
INSERT INTO `game_info` (`id`, `enable`, `image`, `manual`, `name`, `game_type`)
VALUES (1, b'1', '', 'manual', 'Quizz Game', 'QUIZZ'),
       (2, b'0', 'test_image1.png', 'Test manual update', 'Shake Gane', 'SHAKE_GAME');

-- Dumping structure for table game_db.game_campaign
CREATE TABLE IF NOT EXISTS `game_campaign`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `campaign_id`  bigint DEFAULT NULL,
    `game_id`      bigint DEFAULT NULL,
    `game_info_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK45809dorpmrru0j91f2a9dsux` (`game_info_id`),
    CONSTRAINT `FK45809dorpmrru0j91f2a9dsux` FOREIGN KEY (`game_info_id`) REFERENCES `game_info` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping structure for table game_db.puzzle
CREATE TABLE IF NOT EXISTS `puzzle`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `campaign_game_id` bigint       DEFAULT NULL,
    `description`      varchar(255) DEFAULT NULL,
    `image`            varchar(255) DEFAULT NULL,
    `item_num`         int          DEFAULT NULL,
    `name`             varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping structure for table game_db.item
CREATE TABLE IF NOT EXISTS `item`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `description`   varchar(255) DEFAULT NULL,
    `position`      int          DEFAULT NULL,
    `remaining_num` int          DEFAULT NULL,
    `total`         int          DEFAULT NULL,
    `puzzle_id`     bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKbckpr9o921do7tbft554j9qbx` (`puzzle_id`),
    CONSTRAINT `FKbckpr9o921do7tbft554j9qbx` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzle` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



-- Dumping structure for table game_db.quizz
CREATE TABLE IF NOT EXISTS `quizz`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `campaign_game_id`    bigint       DEFAULT NULL,
    `created_at`          datetime(6)  DEFAULT NULL,
    `description`         varchar(255) DEFAULT NULL,
    `name`                varchar(255) DEFAULT NULL,
    `second_per_question` int          DEFAULT NULL,
    `start_time`          datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `quizz` (`id`, `campaign_game_id`, `created_at`, `description`, `name`, `second_per_question`, `start_time`)
VALUES (1, 1, '2024-12-28 17:58:36.020000', 'This is a sample quizz.', 'Sample Quizz', 30,
        '2024-12-29 23:17:00.000000');

-- Dumping structure for table game_db.question
CREATE TABLE IF NOT EXISTS `question`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `answer`        varchar(255) DEFAULT NULL,
    `explaination`  varchar(255) DEFAULT NULL,
    `image`         varchar(255) DEFAULT NULL,
    `option1`       varchar(255) DEFAULT NULL,
    `option2`       varchar(255) DEFAULT NULL,
    `option3`       varchar(255) DEFAULT NULL,
    `option4`       varchar(255) DEFAULT NULL,
    `question_name` varchar(255) DEFAULT NULL,
    `quizz_id`      bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKhr4c9mew80p18e985m58llxov` (`quizz_id`),
    CONSTRAINT `FKhr4c9mew80p18e985m58llxov` FOREIGN KEY (`quizz_id`) REFERENCES `quizz` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- Dumping structure for table game_db.user_answer
CREATE TABLE IF NOT EXISTS `user_answer`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `answer`      varchar(255) DEFAULT NULL,
    `answer_time` int          DEFAULT NULL,
    `is_correct`  bit(1)       DEFAULT NULL,
    `score`       int          DEFAULT NULL,
    `user_id`     bigint       DEFAULT NULL,
    `question_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKpsk90eok3ounaet92hku3gny1` (`question_id`),
    CONSTRAINT `FKpsk90eok3ounaet92hku3gny1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping structure for table game_db.user_campaign_game
CREATE TABLE IF NOT EXISTS `user_campaign_game`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `is_completed`     bit(1) DEFAULT NULL,
    `user_id`          bigint DEFAULT NULL,
    `campaign_game_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKbyhpg3morjob15ciox2ekue7h` (`campaign_game_id`),
    CONSTRAINT `FKbyhpg3morjob15ciox2ekue7h` FOREIGN KEY (`campaign_game_id`) REFERENCES `game_campaign` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping structure for table game_db.user_item
CREATE TABLE IF NOT EXISTS `user_item`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `total_item` int    DEFAULT NULL,
    `user_id`    bigint DEFAULT NULL,
    `item_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKpnf7ma2ql912dg92xna4y89ix` (`item_id`),
    CONSTRAINT `FKpnf7ma2ql912dg92xna4y89ix` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
