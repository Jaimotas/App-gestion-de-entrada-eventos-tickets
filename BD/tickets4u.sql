-- MySQL dump compatible con versiones anteriores de MySQL
--
-- Host: localhost    Database: tickets4u
-- ------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol` enum('cliente','admin') NOT NULL DEFAULT 'cliente',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
INSERT INTO `usuario` VALUES (1,'admin','admin@tickets4u.com','admin123','admin'),(2,'cliente1','cliente1@tickets4u.com','cliente123','cliente');
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
CREATE TABLE `evento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_admin` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime NOT NULL,
  `ciudad` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `aforo` int NOT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `categoria` enum('ACTUAL','DESTACADO','INTERNACIONAL') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_admin` (`id_admin`),
  CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
INSERT INTO `evento` VALUES (1,1,'Concierto Estopa','Estopa vuelve a Madrid con un espectáculo lleno de energía, repasando sus grandes éxitos y presentando nuevos temas en una noche inolvidable.','2026-03-10 20:00:00','2026-03-10 23:59:59','Madrid','Wizink Center','Wizink Center',15000,'estopa.jpg','DESTACADO'),(2,1,'Mad Cool Festival','Uno de los festivales más importantes de Europa reúne a artistas internacionales en varias jornadas de música, cultura y entretenimiento.','2026-06-21 16:00:00','2026-06-21 23:59:59','Madrid','IFEMA','IFEMA',30000,'madcool.jpg','DESTACADO'),(3,1,'La Brama del Cèrvol','Una experiencia musical y escénica única que combina tradición, emoción y una puesta en escena impactante en pleno corazón de Barcelona.','2026-02-05 19:30:00','2026-02-05 23:59:59','Barcelona','Teatro Principal Barcelona','Teatro Principal Barcelona',8000,'la_brama_del_cervol.jpg','DESTACADO'),(4,1,'Anuel AA - Las Leyendas Nunca Mueren Tour','Anuel AA aterriza en Madrid con su gira internacional presentando sus mayores éxitos y un potente directo para los fans del género urbano.','2026-04-25 21:00:00','2026-04-25 23:59:59','Madrid','Palacio Vistalegre','Palacio Vistalegre',14000,'anuel.jpg','ACTUAL'),(5,1,'Eladio Carrión - DON KBRON','Eladio Carrión presenta su nuevo proyecto en un concierto cargado de ritmo, líricas contundentes y una producción de alto nivel.','2026-07-22 21:00:00','2026-07-22 23:59:59','Madrid','Wizink Centre','Wizink Centre',13000,'eladio.jpg','ACTUAL'),(12,1,'Bruno Mars - World Tour','Una noche inolvidable en Wembley con Bruno Mars: ritmos contagiosos, éxitos emblemáticos y espectáculo garantizado.','2026-01-22 16:56:00','2026-01-23 16:56:00','Londres','Wembley Stadium','Wembley Stadium',2000,'Bruno.jpg','INTERNACIONAL');
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE `pedido` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_evento` int NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `pago` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_evento` (`id_evento`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `usuario` (`id`),
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
INSERT INTO `pedido` VALUES (1,2,1,0.00,'0',NULL);
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_pedido` int NOT NULL,
  `id_evento` int NOT NULL,
  `qr` varchar(255) NOT NULL,
  `estado` enum('usado','activo','cancelado') NOT NULL DEFAULT 'activo',
  `tipo_entrada` varchar(255) DEFAULT 'general',
  PRIMARY KEY (`id`),
  UNIQUE KEY `qr` (`qr`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_evento` (`id_evento`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `usuario` (`id`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `ticket_ibfk_3` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
INSERT INTO `ticket` VALUES (1,2,1,1,'funciona','activo','general',NULL);
UNLOCK TABLES;

--
-- Table structure for table `descuento`
--

DROP TABLE IF EXISTS `descuento`;
CREATE TABLE `descuento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_pedido` int NOT NULL,
  `id_evento` int NOT NULL,
  `codigo` varchar(50) NOT NULL,
  `porcentaje` decimal(5,2) NOT NULL,
  `tiempo_expiracion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_evento` (`id_evento`),
  CONSTRAINT `descuento_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `usuario` (`id`),
  CONSTRAINT `descuento_ibfk_2` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `descuento_ibfk_3` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `descuento`
--

LOCK TABLES `descuento` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `estadisticas`
--

DROP TABLE IF EXISTS `estadisticas`;
CREATE TABLE `estadisticas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_evento` int NOT NULL,
  `tickets_disponibles` int NOT NULL DEFAULT '0',
  `tickets_vendidos` int NOT NULL DEFAULT '0',
  `ingresos` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `id_evento` (`id_evento`),
  CONSTRAINT `estadisticas_ibfk_1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `estadisticas`
--

LOCK TABLES `estadisticas` WRITE;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
