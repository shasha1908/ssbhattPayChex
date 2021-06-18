-- MySQL dump 10.13  Distrib 8.0.21, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: empSchema
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `employeeID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `emailID` varchar(45) DEFAULT NULL,
  `mobileNumber` bigint DEFAULT NULL,
  PRIMARY KEY (`employeeID`),
  UNIQUE KEY `userName_UNIQUE` (`userName`),
  UNIQUE KEY `password_UNIQUE` (`password`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (9,'Shashwat','Bhatt','ssbhatt','syr1908','ssbhatt@syr.edu',3158985103),(10,'Rutav','Modi','rbmodi','rut@123','rbmodi@syr.edu',3158887890),(11,'Jack','Ryan','jryan','jack456','jryan@gmail.com',3156789090);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clockDetails`
--

DROP TABLE IF EXISTS `clockDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clockDetails` (
  `logID` int NOT NULL AUTO_INCREMENT,
  `employeeID` int DEFAULT NULL,
  `ClockInTime` timestamp(4) NOT NULL,
  `ClockOutTime` timestamp(4) NULL DEFAULT NULL,
  `break` tinyint DEFAULT '0',
  `lunch` tinyint DEFAULT '0',
  PRIMARY KEY (`logID`),
  KEY `employeeID_idx` (`employeeID`),
  CONSTRAINT `employeeID` FOREIGN KEY (`employeeID`) REFERENCES `account` (`employeeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clockDetails`
--

LOCK TABLES `clockDetails` WRITE;
/*!40000 ALTER TABLE `clockDetails` DISABLE KEYS */;
INSERT INTO `clockDetails` VALUES (1,10,'2021-06-18 05:55:27.0000','2021-06-18 05:55:40.0000',0,0),(2,9,'2021-06-18 05:57:12.0000','2021-06-18 05:57:24.0000',0,0);
/*!40000 ALTER TABLE `clockDetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-18  2:01:39
