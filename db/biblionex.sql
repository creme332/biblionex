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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'Smith','John','john.smith@gmail.com'),(2,'Johnson','Emily','emily.johnson@gmail.com'),(3,'Brown','Michael','michael.brown@gmail.com'),(4,'Williams','Sarah','sarah.williams@gmail.com'),(5,'Jones','David','david.jones@gmail.com'),(6,'Garcia','Linda','linda.garcia@gmail.com'),(7,'Miller','James','james.miller@gmail.com'),(8,'Davis','Barbara','barbara.davis@gmail.com'),(9,'Martinez','Robert','robert.martinez@gmail.com'),(10,'Hernandez','Patricia','patricia.hernandez@gmail.com');
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
INSERT INTO `book` VALUES (1,300,'978-1-123456-00-1'),(2,500,'978-1-123456-01-1'),(3,250,'978-1-123456-02-1'),(4,400,'978-1-123456-03-1'),(5,350,'978-1-123456-04-1');
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
INSERT INTO `book_author` VALUES (1,1),(2,2),(3,3),(4,4),(5,5);
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
INSERT INTO `journal` VALUES (6,'1234-5678','2023-01-01 00:00:00','http://example.com/journal1','Monthly'),(7,'2345-6789','2023-02-01 00:00:00','http://example.com/journal2','Biannually'),(8,'3456-7890','2023-03-01 00:00:00','http://example.com/journal3','Quarterly'),(9,'4567-8901','2023-04-01 00:00:00','http://example.com/journal4','Annually'),(10,'5678-9012','2023-05-01 00:00:00','http://example.com/journal5','Weekly');
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,1,'A gripping thriller novel','http://example.com/book_image.jpg',0,'Book','Treasure Island'),(2,1,'An epic fantasy saga','http://example.com/book_image.jpg',0,'Book','Pride and Prejudice'),(3,2,'A heartwarming romance story','http://example.com/book_image.jpg',0,'Book','Wuthering Heights'),(4,3,'A scientific exploration','http://example.com/book_image.jpg',0,'Book','Guide to Galaxy'),(5,4,'A historical drama','http://example.com/book_image.jpg',0,'Book','Absalom'),(6,5,'Cutting-edge scientific journal','http://example.com/journal_image.jpg',0,'Journal','American Journal of Applied Sciences'),(7,6,'Biomedical research journal','http://example.com/journal_image.jpg',0,'Journal',' American Journal of Biochemistry and Biotechnology'),(8,7,'Environmental sustainability journal','http://example.com/journal_image.jpg',0,'Journal',' Energy Research Journal'),(9,8,'Educational psychology journal','http://example.com/journal_image.jpg',0,'Journal','Educational Psychology Journal'),(10,9,'Historical review journal','http://example.com/journal_image.jpg',0,'Journal','Journal of Mechatronics and Robotics'),(16,1,'Action-packed thriller','http://example.com/video_image.jpg',0,'Video','Oppenheimer'),(17,2,'Comedy entertainment','http://example.com/video_image.jpg',0,'Video','Back to Black'),(18,3,'Educational documentary','http://example.com/video_image.jpg',0,'Video','Raging Bull'),(19,4,'Historical drama series','http://example.com/video_image.jpg',0,'Video','The Wolf of Wall Street'),(20,5,'Sci-fi adventure','http://example.com/video_image.jpg',0,'Video','Eternal Sunshine of the Spotless Mind');
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
  `order_id` int(11) unsigned DEFAULT NULL,
  `condition` enum('New','Used - Like New','Used - Good','Used - Acceptable','Unacceptable') NOT NULL,
  `shelf_no` int(10) unsigned NOT NULL DEFAULT 0,
  `aisle_no` int(10) unsigned NOT NULL DEFAULT 0,
  `section_no` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`barcode`),
  KEY `material_copy_material_material_id_fk` (`material_id`),
  KEY `material_copy_order_order_id_fk` (`order_id`),
  CONSTRAINT `material_copy_material_material_id_fk` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`),
  CONSTRAINT `material_copy_order_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_copy`
--

LOCK TABLES `material_copy` WRITE;
/*!40000 ALTER TABLE `material_copy` DISABLE KEYS */;
INSERT INTO `material_copy` VALUES (11,1,NULL,'New',1,2,3),(12,1,NULL,'Used - Good',1,2,4),(13,2,NULL,'New',2,3,4),(14,2,NULL,'Used - Like New',2,3,5),(15,3,NULL,'New',3,4,5),(16,3,NULL,'Used - Acceptable',3,4,6),(17,4,NULL,'New',4,5,6),(18,4,NULL,'Used - Good',4,5,7),(19,5,NULL,'New',5,6,7),(20,5,NULL,'Used - Like New',5,6,8),(21,6,NULL,'New',1,2,3),(22,6,NULL,'Used - Good',1,2,4),(23,7,NULL,'New',2,3,4),(24,7,NULL,'Used - Like New',2,3,5),(25,8,NULL,'New',3,4,5),(26,8,NULL,'Used - Acceptable',3,4,6),(27,9,NULL,'New',4,5,6),(28,9,NULL,'Used - Good',4,5,7),(29,10,NULL,'New',5,6,7),(30,10,NULL,'Used - Like New',5,6,8),(31,16,NULL,'New',1,2,3),(32,16,NULL,'Used - Good',1,2,4),(33,17,NULL,'New',2,3,4),(34,17,NULL,'Used - Like New',2,3,5),(35,18,NULL,'New',3,4,5),(36,18,NULL,'Used - Acceptable',3,4,6),(37,19,NULL,'New',4,5,6),(38,19,NULL,'Used - Good',4,5,7),(39,20,NULL,'New',5,6,7),(40,20,NULL,'Used - Like New',5,6,8);
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
INSERT INTO `material_genre` VALUES (1,'Fiction'),(1,'Thriller'),(2,'Adventure'),(2,'Fantasy'),(3,'Fiction'),(3,'Romance'),(4,'Non-fiction'),(4,'Science'),(5,'Drama'),(5,'Historical'),(6,'Research'),(6,'Science'),(7,'Biomedical'),(7,'Health'),(8,'Environment'),(8,'Sustainability'),(9,'Education'),(9,'Psychology'),(10,'History'),(10,'Review'),(16,'Action'),(16,'Thriller'),(17,'Comedy'),(17,'Entertainment'),(18,'Documentary'),(18,'Educational'),(19,'Drama'),(19,'Historical'),(20,'Adventure'),(20,'Sci-fi');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES (1,'Penguin Random House','contact@penguinrandomhouse.com','USA'),(2,'HarperCollins','info@harpercollins.com','USA'),(3,'Macmillan Publishers','support@macmillan.com','USA'),(4,'Simon & Schuster','help@simonandschuster.com','USA'),(5,'Hachette Livre','contact@hachette.com','France'),(6,'Scholastic','info@scholastic.com','USA'),(7,'Pearson Education','support@pearsoned.com','UK'),(8,'Oxford University Press','help@oup.com','UK'),(9,'Springer Nature','contact@springernature.com','Germany'),(10,'Cambridge University Press','info@cambridge.org','UK');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES (1,'sales@bookvendor1.com','Tom Sawyer','Alice Johnson'),(2,'info@bookvendor2.com','Jerry Thomas','Bob Smith'),(3,'contact@bookvendor3.com','Paul Gaultier','Charlie Brown'),(4,'support@bookvendor4.com','Oliver Loppy','David Williams'),(5,'help@bookvendor5.com','Jonas Pauly','Eva Davis'),(6,'sales@bookvendor6.com','Jackson Boa','Frank Garcia'),(7,'info@bookvendor7.com','Sergent Lor','Grace Miller'),(8,'contact@bookvendor8.com','Patrick Gros','Hank Martinez'),(9,'support@bookvendor9.com','Emanuel Jolicoeur','Ivy Hernandez'),(10,'help@bookvendor10.com','Tom Jerry','Jack Thomas');
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
INSERT INTO `vendor_material` VALUES (1,1,20.00),(1,16,18.00),(2,2,25.00),(3,3,18.00),(4,4,30.00),(4,17,15.00),(5,5,22.00),(5,18,20.00),(6,6,15.00),(7,7,20.00),(7,19,25.00),(8,8,18.00),(9,9,22.00),(10,10,17.00),(10,20,22.00);
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
  CONSTRAINT `rating_range_check` CHECK (`rating` >= 1 and `rating` <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (16,'English',120,4,'MP4'),(17,'French',90,3,'AVI'),(18,'Spanish',110,5,'MOV'),(19,'German',100,4,'MKV'),(20,'Japanese',95,5,'FLV');
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

-- Dump completed on 2024-07-03 14:00:32
