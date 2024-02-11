CREATE TABLE IF NOT EXISTS `share_log`
(
    `id`                 varchar(255) NOT NULL,
    `recipient_email`    varchar(255) NOT NULL,
    `phone_number`       varchar(20)  NOT NULL,
    `creation_timestamp` timestamp    NOT NULL DEFAULT current_timestamp(),
    `sent_status`        tinyint(1) NOT NULL,
    `hashed_link`        varchar(255) NOT NULL,
    `sender_name`        varchar(255) NOT NULL,
    `sender_email`       varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
;

CREATE TABLE IF NOT EXISTS `pin_log`
(
    `id`                   varchar(255) NOT NULL,
    `hashed_pin`           varchar(255) NOT NULL,
    `client_fingerprint`   varchar(255) NOT NULL,
    `expiration_timestamp` timestamp    NOT NULL,
    PRIMARY KEY (`id`),
    KEY                    `idx_pin_log_client_fingerprint` (`client_fingerprint`),
    CONSTRAINT `pin_log_ibfk_1` FOREIGN KEY (`id`) REFERENCES `share_log` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
;

CREATE TABLE IF NOT EXISTS `access_log`
(
    `id`                 varchar(255) NOT NULL,
    `accessed_timestamp` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp (),
    `client_fingerprint` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`),
    KEY                  `idx_access_log_client_fingerprint` (`client_fingerprint`),
    CONSTRAINT `access_log_ibfk_1` FOREIGN KEY (`id`) REFERENCES `share_log` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
;
