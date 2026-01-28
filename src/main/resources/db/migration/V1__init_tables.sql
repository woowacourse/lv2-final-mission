CREATE TABLE `operating_hour` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `day_of_week` enum('FRIDAY','MONDAY','SATURDAY','SUNDAY','THURSDAY','TUESDAY','WEDNESDAY') DEFAULT NULL,
                                  `time` time(6) DEFAULT NULL,
                                  `shop_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FK1o7pntvpj5oi6w70kb93dfml0` (`shop_id`),
                                  CONSTRAINT `FK1o7pntvpj5oi6w70kb93dfml0` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `owner` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `business_license_url` varchar(255) DEFAULT NULL,
                         `business_registration_number` varchar(255) DEFAULT NULL,
                         `user_id` bigint DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK8nrpb1o74o0ol4ejcymyffplg` (`user_id`),
                         CONSTRAINT `FKsk80mgxau4fje7xby9j990rb` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reservation` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `date` date DEFAULT NULL,
                               `time` time(6) DEFAULT NULL,
                               `shop_id` bigint DEFAULT NULL,
                               `user_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK68f9h17uq79o3ybc7kvkwpas0` (`shop_id`),
                               KEY `FKrea93581tgkq61mdl13hehami` (`user_id`),
                               CONSTRAINT `FK68f9h17uq79o3ybc7kvkwpas0` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`),
                               CONSTRAINT `FKrea93581tgkq61mdl13hehami` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `shop` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `detail` varchar(255) DEFAULT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `type` enum('분식','야식','양식','일식','중식','한식','혼밥','회식') DEFAULT NULL,
                        `owner_id` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UK4l317pajsnbskh6y2x6tjsoj2` (`owner_id`),
                        CONSTRAINT `FK72gho3b70vuyvt6oevu29xpiu` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
