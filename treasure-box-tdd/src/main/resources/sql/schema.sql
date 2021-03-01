CREATE TABLE `person` (
    `id`         bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `first_name` varchar(30)         NOT NULL DEFAULT '' COMMENT 'first_name',
    `last_name`  varchar(30)         NOT NULL DEFAULT '' COMMENT 'last_name',
    PRIMARY KEY (`id`)
);

INSERT INTO `person`(id, first_name, last_name) VALUES (1, 'joeqw', '13vzx');
INSERT INTO `person`(id, first_name, last_name) VALUES (2, '123', 'eqwzv');
