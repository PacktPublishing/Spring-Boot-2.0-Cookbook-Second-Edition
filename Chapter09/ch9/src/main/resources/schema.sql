-- Create syntax for TABLE 'author'
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
-- Create syntax for TABLE 'publisher'
DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
-- Create syntax for TABLE 'reviewer'
DROP TABLE IF EXISTS `reviewer`;
CREATE TABLE `reviewer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
-- Create syntax for TABLE 'book'
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `publisher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`),
  CONSTRAINT `FK_author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
);
-- Create syntax for TABLE 'book_reviewers'
DROP TABLE IF EXISTS `book_reviewers`;
CREATE TABLE `book_reviewers` (
  `book_id` bigint(20) NOT NULL,
  `reviewers_id` bigint(20) NOT NULL,
  CONSTRAINT `FK_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FK_reviewer` FOREIGN KEY (`reviewers_id`) REFERENCES `reviewer` (`id`)
);
