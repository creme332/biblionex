-- MySQL dump 10.19  Distrib 10.3.39-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: biblionex
-- ------------------------------------------------------
-- Server version	10.3.39-MariaDB-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `biblionex`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `biblionex` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `biblionex`;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `author_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`author_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `material_id` int(11) unsigned NOT NULL,
  `page_count` int(21) unsigned DEFAULT NULL,
  `isbn` varchar(255) NOT NULL,
  PRIMARY KEY (`material_id`),
  UNIQUE KEY `isbn` (`isbn`),
  CONSTRAINT `book_consk1` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_author`
--

DROP TABLE IF EXISTS `book_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_author` (
  `material_id` int(11) unsigned NOT NULL,
  `author_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`material_id`,`author_id`),
  KEY `bookauthor_consk1` (`author_id`),
  CONSTRAINT `bookauthor_consk1` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`),
  CONSTRAINT `bookauthor_consk2` FOREIGN KEY (`material_id`) REFERENCES `book` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_author`
--

LOCK TABLES `book_author` WRITE;
/*!40000 ALTER TABLE `book_author` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fine`
--

DROP TABLE IF EXISTS `fine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fine` (
  `patron_id` int(10) unsigned NOT NULL,
  `loan_id` int(10) unsigned NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`loan_id`,`patron_id`,`date`),
  KEY `fine_patron_patron_id_fk` (`patron_id`),
  CONSTRAINT `fine_loan_loan_id_fk` FOREIGN KEY (`loan_id`) REFERENCES `loan` (`loan_id`),
  CONSTRAINT `fine_patron_patron_id_fk` FOREIGN KEY (`patron_id`) REFERENCES `patron` (`patron_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fine`
--

LOCK TABLES `fine` WRITE;
/*!40000 ALTER TABLE `fine` DISABLE KEYS */;
/*!40000 ALTER TABLE `fine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journal`
--

DROP TABLE IF EXISTS `journal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journal` (
  `material_id` int(11) unsigned NOT NULL,
  `issn` varchar(255) NOT NULL,
  `start_date` datetime NOT NULL,
  `website` varchar(255) NOT NULL,
  `frequency` enum('Weekly','Monthly','Quarterly','Annually','Biannually','Daily') DEFAULT NULL,
  PRIMARY KEY (`material_id`),
  UNIQUE KEY `issn` (`issn`),
  CONSTRAINT `journal_consk1` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journal`
--

LOCK TABLES `journal` WRITE;
/*!40000 ALTER TABLE `journal` DISABLE KEYS */;
/*!40000 ALTER TABLE `journal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librarian`
--

DROP TABLE IF EXISTS `librarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `librarian` (
  `librarian_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `phone_no` varchar(255) NOT NULL,
  `email` varchar(320) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`librarian_id`),
  UNIQUE KEY `librarian_email_uindex` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarian`
--

LOCK TABLES `librarian` WRITE;
/*!40000 ALTER TABLE `librarian` DISABLE KEYS */;
/*!40000 ALTER TABLE `librarian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loan` (
  `loan_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `patron_id` int(10) unsigned DEFAULT NULL,
  `barcode` int(10) unsigned DEFAULT NULL,
  `checkout_librarian_id` int(10) unsigned DEFAULT NULL,
  `checkin_librarian_id` int(10) unsigned DEFAULT NULL,
  `issue_date` datetime NOT NULL DEFAULT current_timestamp(),
  `return_date` datetime DEFAULT NULL,
  `due_date` datetime NOT NULL,
  `renewal_count` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`loan_id`),
  UNIQUE KEY `loan_pk` (`patron_id`,`barcode`,`issue_date`),
  KEY `checkin_librarian_id` (`checkin_librarian_id`),
  KEY `loan_librarian_librarian_id_fk` (`checkout_librarian_id`),
  KEY `loan_material_copy_barcode_fk` (`barcode`),
  CONSTRAINT `checkin_librarian_id` FOREIGN KEY (`checkin_librarian_id`) REFERENCES `librarian` (`librarian_id`),
  CONSTRAINT `loan_librarian_librarian_id_fk` FOREIGN KEY (`checkout_librarian_id`) REFERENCES `librarian` (`librarian_id`),
  CONSTRAINT `loan_material_copy_barcode_fk` FOREIGN KEY (`barcode`) REFERENCES `material_copy` (`barcode`),
  CONSTRAINT `loan_patron_patron_id_fk` FOREIGN KEY (`patron_id`) REFERENCES `patron` (`patron_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material` (
  `material_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `publisher_id` int(11) unsigned NOT NULL,
  `description` varchar(255) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `age_restriction` int(3) NOT NULL DEFAULT 0,
  `type` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`material_id`),
  KEY `m_consk1` (`publisher_id`),
  CONSTRAINT `m_consk1` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`publisher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_copy`
--

DROP TABLE IF EXISTS `material_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_copy` (
  `barcode` int(21) unsigned NOT NULL AUTO_INCREMENT,
  `material_id` int(11) unsigned NOT NULL,
  `order_id` int(11) unsigned NOT NULL,
  `condition` enum('New','Used - Like New','Used - Good','Used - Acceptable','Unacceptable') NOT NULL,
  `shelf_no` int(10) unsigned NOT NULL DEFAULT 0,
  `aisle_no` int(10) unsigned NOT NULL DEFAULT 0,
  `section_no` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`barcode`),
  KEY `material_copy_material_material_id_fk` (`material_id`),
  KEY `material_copy_order_order_id_fk` (`order_id`),
  CONSTRAINT `material_copy_material_material_id_fk` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`),
  CONSTRAINT `material_copy_order_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_copy`
--

LOCK TABLES `material_copy` WRITE;
/*!40000 ALTER TABLE `material_copy` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_copy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_genre`
--

DROP TABLE IF EXISTS `material_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_genre` (
  `material_id` int(10) unsigned NOT NULL,
  `genre` varchar(255) NOT NULL,
  PRIMARY KEY (`material_id`,`genre`),
  CONSTRAINT `material_genre_material_material_id_fk` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_genre`
--

LOCK TABLES `material_genre` WRITE;
/*!40000 ALTER TABLE `material_genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `librarian_id` int(11) unsigned NOT NULL,
  `vendor_id` int(11) unsigned NOT NULL,
  `material_id` int(11) unsigned NOT NULL,
  `status` enum('Pending','Processing','Shipped','Delivered','Completed','Canceled','Refunded','On Hold','Failed','Returned') NOT NULL DEFAULT 'Pending',
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `quantity` int(10) unsigned NOT NULL,
  `delivery_date` datetime DEFAULT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `order_material_material_id_fk` (`material_id`),
  KEY `order_librarian_librarian_id_fk` (`librarian_id`),
  KEY `order_vendor_vendor_id_fk` (`vendor_id`),
  CONSTRAINT `order_librarian_librarian_id_fk` FOREIGN KEY (`librarian_id`) REFERENCES `librarian` (`librarian_id`),
  CONSTRAINT `order_material_material_id_fk` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`),
  CONSTRAINT `order_vendor_vendor_id_fk` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patron`
--

DROP TABLE IF EXISTS `patron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patron` (
  `patron_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `phone_no` varchar(255) NOT NULL,
  `email` varchar(320) NOT NULL,
  `registration_date` datetime NOT NULL DEFAULT current_timestamp(),
  `birth_date` datetime DEFAULT NULL,
  `credit_card_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`patron_id`),
  UNIQUE KEY `patron_email_uindex` (`email`),
  UNIQUE KEY `patron_credit_card_no_uindex` (`credit_card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patron`
--

LOCK TABLES `patron` WRITE;
/*!40000 ALTER TABLE `patron` DISABLE KEYS */;
/*!40000 ALTER TABLE `patron` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publisher` (
  `publisher_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL DEFAULT 'Mauritius',
  PRIMARY KEY (`publisher_id`),
  UNIQUE KEY `publisher_pk` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendor` (
  `vendor_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(320) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact_person` varchar(255) NOT NULL,
  PRIMARY KEY (`vendor_id`),
  UNIQUE KEY `vendor_pk` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor_material`
--

DROP TABLE IF EXISTS `vendor_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendor_material` (
  `vendor_id` int(11) unsigned NOT NULL,
  `material_id` int(11) unsigned NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`vendor_id`,`material_id`),
  KEY `vendor_material_material_material_id_fk` (`material_id`),
  CONSTRAINT `vendor_material_material_material_id_fk` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`),
  CONSTRAINT `vendor_material_vendor_vendor_id_fk` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor_material`
--

LOCK TABLES `vendor_material` WRITE;
/*!40000 ALTER TABLE `vendor_material` DISABLE KEYS */;
/*!40000 ALTER TABLE `vendor_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `video` (
  `material_id` int(11) unsigned NOT NULL,
  `language` varchar(255) NOT NULL,
  `duration` int(21) unsigned NOT NULL,
  `rating` int(11) unsigned NOT NULL,
  `format` varchar(255) NOT NULL,
  PRIMARY KEY (`material_id`),
  CONSTRAINT `video_material_material_id_fk` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`),
  CONSTRAINT `format_extension_check` CHECK (`format` regexp '\\.(MP4|AVI|MOV|MKV|FLV|MPEG)$'),
  CONSTRAINT `rating_range_check` CHECK (`rating` >= 1 and `rating` <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-09 17:50:52
