-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `autor`
--

use biblioteca;

DROP TABLE IF EXISTS `autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autor` (
  `aut_id` bigint NOT NULL AUTO_INCREMENT,
  `aut_nombre` varchar(50) NOT NULL,
  `aut_nacionalidad` varchar(50) NOT NULL,
  `aut_estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`aut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor`
--

LOCK TABLES `autor` WRITE;
/*!40000 ALTER TABLE `autor` DISABLE KEYS */;
INSERT INTO `autor` VALUES (1,'Mario Vargas Llosa','Peruana',1),(2,'Cesar Vallejo Mendoza','Peruana',1),(3,'José María Arguedas','Peruana',1),(4,'Ricardo Palma Soriano','Peruana',1),(6,'Michael Carlos Trujillo Salinas','Peruana',1);
/*!40000 ALTER TABLE `autor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libro` (
  `lib_id` bigint NOT NULL AUTO_INCREMENT,
  `lib_titulo` varchar(150) NOT NULL,
  `lib_isbn` varchar(30) NOT NULL,
  `lib_fecha_publicacion` date NOT NULL,
  `lib_estado` tinyint(1) DEFAULT '1',
  `lib_aut_id` bigint DEFAULT NULL,
  PRIMARY KEY (`lib_id`),
  KEY `fk_libro_autor` (`lib_aut_id`),
  CONSTRAINT `fk_libro_autor` FOREIGN KEY (`lib_aut_id`) REFERENCES `autor` (`aut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
INSERT INTO `libro` VALUES (1,'La ciudad y los perros','9788420471839','1963-01-01',1,1),(2,'Conversación en La Catedral','9788420471860','1969-01-01',1,1),(3,'Los heraldos negros','9788498956993','1919-01-01',1,2),(4,'Trilce','9788498957006','1922-01-01',1,2),(5,'Los ríos profundos','9788437604947','1958-01-01',1,3),(6,'Todas las sangres','9788437604954','1964-01-01',1,3),(7,'Tradiciones Peruanas I','9788490741115','1872-01-01',1,4),(8,'Tradiciones Peruanas II','9788490741122','1874-01-01',1,4),(9,'Libro sin autor 1 - mod','9780000000001','2024-01-01',1,NULL),(10,'Libro sin autor 2','9780000000002','2024-02-01',1,NULL),(12,'Libro de prueba','986532471254','2026-03-15',1,NULL);
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-19 23:23:48
