-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 25 déc. 2023 à 23:08
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `sr_project`
--

-- --------------------------------------------------------

--
-- Structure de la table `facture_vehicule`
--

CREATE TABLE `facture_vehicule` (
  `code_facture` varchar(200) NOT NULL,
  `date_facture` varchar(200) NOT NULL,
  `client` varchar(200) NOT NULL,
  `designation` varchar(200) NOT NULL,
  `qte` int(11) NOT NULL,
  `prix` decimal(10,0) NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `facture_vehicule`
--

INSERT INTO `facture_vehicule` (`code_facture`, `date_facture`, `client`, `designation`, `qte`, `prix`, `total`) VALUES
('002', '30/11/2023', 'ryhab arafet', 'bmw', 1, 80, 80),
('001', '20/12/2023', 'raslen arafet', 'audi a3', 1, 200, 200);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
