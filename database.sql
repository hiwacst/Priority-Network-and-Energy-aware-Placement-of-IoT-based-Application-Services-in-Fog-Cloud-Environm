-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 04, 2020 at 12:40 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `PAMED_Algo`
--

-- --------------------------------------------------------

--
-- Table structure for table `Active_Devices1`
--

CREATE TABLE `Active_Devices1` (
  `methodNumber` text NOT NULL,
  `colony1` double NOT NULL,
  `colony2` double NOT NULL,
  `cloud` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Active_Devices1`
--

INSERT INTO `Active_Devices1` (`methodNumber`, `colony1`, `colony2`, `cloud`) VALUES
('Only CLoud', 0, 0, 5),
('Random Placement', 2, 2, 3),
('Edgeward', 4, 0, 5),
('Paper1 For Evaluation', 4, 0, 0),
('Paper2 For Evaluation', 0, 0, 0),
('Our Proposed', 5, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Active_Devices2`
--

CREATE TABLE `Active_Devices2` (
  `methodNumber` text NOT NULL,
  `colony1` double NOT NULL,
  `colony2` double NOT NULL,
  `cloud` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Active_Devices2`
--

INSERT INTO `Active_Devices2` (`methodNumber`, `colony1`, `colony2`, `cloud`) VALUES
('Only CLoud', 0, 0, 189),
('Random Placement', 14, 4, 171),
('Edgeward', 14, 0, 175),
('Paper1 For Evaluation', 14, 19, 159),
('Paper2 For Evaluation', 0, 0, 0),
('Our Proposed', 14, 19, 160);

-- --------------------------------------------------------

--
-- Table structure for table `Active_Devices3`
--

CREATE TABLE `Active_Devices3` (
  `methodNumber` text NOT NULL,
  `colony1` double NOT NULL,
  `colony2` double NOT NULL,
  `cloud` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Active_Devices3`
--

INSERT INTO `Active_Devices3` (`methodNumber`, `colony1`, `colony2`, `cloud`) VALUES
('Only CLoud', 0, 0, 281),
('Random Placement', 14, 4, 264),
('Edgeward', 14, 0, 268),
('Paper1 For Evaluation', 14, 19, 252),
('Paper2 For Evaluation', 0, 0, 0),
('Our Proposed', 8, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Active_Devices4`
--

CREATE TABLE `Active_Devices4` (
  `methodNumber` text NOT NULL,
  `colony1` double NOT NULL,
  `colony2` double NOT NULL,
  `cloud` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Active_Devices4`
--

INSERT INTO `Active_Devices4` (`methodNumber`, `colony1`, `colony2`, `cloud`) VALUES
('Only CLoud', 0, 0, 382),
('Random Placement', 14, 4, 365),
('Edgeward', 14, 0, 369),
('Paper1 For Evaluation', 14, 19, 353),
('Paper2 For Evaluation', 0, 0, 0),
('Our Proposed', 9, 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Active_Devices5`
--

CREATE TABLE `Active_Devices5` (
  `methodNumber` text NOT NULL,
  `colony1` double NOT NULL,
  `colony2` double NOT NULL,
  `cloud` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Active_Devices5`
--

INSERT INTO `Active_Devices5` (`methodNumber`, `colony1`, `colony2`, `cloud`) VALUES
('Only CLoud', 0, 0, 478),
('Random Placement', 14, 4, 460),
('Edgeward', 14, 0, 464),
('Paper1 For Evaluation', 9, 2, 0),
('Paper2 For Evaluation', 0, 0, 0),
('Our Proposed', 9, 5, 0);

-- --------------------------------------------------------

--
-- Table structure for table `All_Delay_And_Satsified_For_10_Time_Runing`
--

CREATE TABLE `All_Delay_And_Satsified_For_10_Time_Runing` (
  `methodName` text NOT NULL,
  `numberOfService` int(11) NOT NULL,
  `delay` text NOT NULL,
  `satsified` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `All_Delay_And_Satsified_For_10_Time_Runing`
--

INSERT INTO `All_Delay_And_Satsified_For_10_Time_Runing` (`methodName`, `numberOfService`, `delay`, `satsified`) VALUES
('Only CLoud', 25, ',495,668,1448,321,1189,1362,842,1015,582,755', ',11,2,0,35,0,0,0,0,6,0'),
('Only CLoud', 50, ',495,668,1448,321,1189,1362,842,1015,582,755', ',16,1,0,37,0,0,0,0,7,0'),
('Only CLoud', 100, ',495,668,1448,321,1189,1362,842,1015,582,755', ',12,2,0,35,0,0,0,0,7,0'),
('Only CLoud', 200, ',495,668,1448,321,1189,1362,842,1015,582,755', ',14,4,0,39,0,0,0,0,6,0'),
('Only CLoud', 400, ',495,668,1448,321,1189,1362,842,1015,582,755', ',17,1,0,36,0,0,0,0,8,0'),
('Random Placement', 25, ',495,668,1448,321,1189,1362,842,1015,582,755', ',24,7,13,37,4,9,6,9,13,6'),
('Random Placement', 50, ',415', ',81'),
('Random Placement', 100, ',415', ',74'),
('Random Placement', 200, ',415', ',60'),
('Random Placement', 400, ',495,668,1448,321,1189,1362,842,1015,582,755', ',51,41,39,52,37,29,41,37,44,41'),
('Edgeward', 25, ',495,668,1448,321,1189,1362,842,1015,582,755', ',18,4,3,37,2,6,1,8,11,6'),
('Edgeward', 50, ',495,668,1448,321,1189,1362,842,1015,582,755', ',39,30,31,54,31,29,28,29,33,29'),
('Edgeward', 100, ',495,668,1448,321,1189,1362,842,1015,582,755', ',37,29,24,44,35,28,30,31,35,37'),
('Edgeward', 200, ',495,668,1448,321,1189,1362,842,1015,582,755', ',51,55,47,59,52,52,51,56,52,51'),
('Edgeward', 400, ',495,668,1448,321,1189,1362,842,1015,582,755', ',44,32,28,53,29,20,33,30,35,30'),
('Paper1 For Evaluation', 25, ',495,668,1448,321,1189,1362,842,1015,582,755', ',16,4,8,34,3,4,1,7,12,4'),
('Paper1 For Evaluation', 50, ',495,668,1448,321,1189,1362,842,1015,582,755', ',26,16,11,41,22,15,15,11,21,12'),
('Paper1 For Evaluation', 100, ',495,668,1448,321,1189,1362,842,1015,582,755', ',44,39,41,53,41,34,41,41,39,41'),
('Paper1 For Evaluation', 200, ',495,668,1448,321,1189,1362,842,1015,582,755', ',57,56,54,61,55,55,55,56,55,55'),
('Paper1 For Evaluation', 400, ',495,668,1448,321,1189,1362,842,1015,582,755', ',62,63,64,64,64,63,64,64,63,64'),
('Paper2 For Evaluation', 25, '0', '0'),
('Paper2 For Evaluation', 50, '0', '0'),
('Paper2 For Evaluation', 100, '0', '0'),
('Paper2 For Evaluation', 200, '0', '0'),
('Paper2 For Evaluation', 400, '0', '0'),
('Our Proposed', 25, ',495,668,1448,321,1189,1362,842,1015,582,755', ',21,6,13,23,8,8,7,12,11,11'),
('Our Proposed', 50, ',495,668,1448,321,1189,1362,842,1015,582,755', ',34,24,24,37,27,23,25,26,24,19'),
('Our Proposed', 100, ',495,668,1448,321,1189,1362,842,1015,582,755', ',43,42,48,55,47,38,44,47,43,51'),
('Our Proposed', 200, ',495,668,1448,321,1189,1362,842,1015,582,755', ',61,65,57,64,56,61,62,57,62,57'),
('Our Proposed', 400, ',495,668,1448,321,1189,1362,842,1015,582,755', ',64,64,65,66,64,65,62,60,66,62');

-- --------------------------------------------------------

--
-- Table structure for table `AVG_Task_Delay`
--

CREATE TABLE `AVG_Task_Delay` (
  `methodNumber` text NOT NULL,
  `task10` double NOT NULL,
  `task20` double NOT NULL,
  `task30` double NOT NULL,
  `task40` double NOT NULL,
  `task50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AVG_Task_Delay`
--

INSERT INTO `AVG_Task_Delay` (`methodNumber`, `task10`, `task20`, `task30`, `task40`, `task50`) VALUES
('Only CLoud', 2628.341, 2619.371, 2580.7755, 2645.4665, 2570.1742),
('Random Placement', 2358.277, 831.32, 831.32, 831.32, 1546.7767),
('Edgeward', 2474.093, 1739.918, 1734.8235, 1092.3765, 1767.1662),
('Paper1 For Evaluation', 2387.561, 2045.615, 1355.7705, 1048.917, 867.572),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 2410.1415, 2071.5845, 1473.258, 1018.7645, 940.6365);

-- --------------------------------------------------------

--
-- Table structure for table `Cloud_CPU_Utilization`
--

CREATE TABLE `Cloud_CPU_Utilization` (
  `methodNumber` text NOT NULL,
  `service10` double NOT NULL,
  `service20` double NOT NULL,
  `service30` double NOT NULL,
  `service40` double NOT NULL,
  `service50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Cloud_CPU_Utilization`
--

INSERT INTO `Cloud_CPU_Utilization` (`methodNumber`, `service10`, `service20`, `service30`, `service40`, `service50`) VALUES
('Only CLoud', 71, 63, 88, 98, 98),
('Random Placement', 56, 71, 97, 98, 83),
('Edgeward', 77, 78, 79, 95, 79),
('Paper1 For Evaluation', 91, 79, 97, 83, 35),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 64, 80, 0, 93, 40);

-- --------------------------------------------------------

--
-- Table structure for table `Cloud_RAM_Utilization`
--

CREATE TABLE `Cloud_RAM_Utilization` (
  `methodNumber` text NOT NULL,
  `service10` double NOT NULL,
  `service20` double NOT NULL,
  `service30` double NOT NULL,
  `service40` double NOT NULL,
  `service50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Cloud_RAM_Utilization`
--

INSERT INTO `Cloud_RAM_Utilization` (`methodNumber`, `service10`, `service20`, `service30`, `service40`, `service50`) VALUES
('Only CLoud', 54, 61, 58, 100, 100),
('Random Placement', 42, 56, 100, 100, 65),
('Edgeward', 60, 61, 62, 100, 63),
('Paper1 For Evaluation', 45, 53, 98, 98, 94),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 51, 54, 0, 56, 97);

-- --------------------------------------------------------

--
-- Table structure for table `Cloud_Resource_Wastage`
--

CREATE TABLE `Cloud_Resource_Wastage` (
  `methodNumber` text NOT NULL,
  `service10` double NOT NULL,
  `service20` double NOT NULL,
  `service30` double NOT NULL,
  `service40` double NOT NULL,
  `service50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Cloud_Resource_Wastage`
--

INSERT INTO `Cloud_Resource_Wastage` (`methodNumber`, `service10`, `service20`, `service30`, `service40`, `service50`) VALUES
('Only CLoud', 21.9, 32.4, 32.94, 4.84, 6.06),
('Random Placement', 25.05, 40.96, 3.32, 4.43, 33.39),
('Edgeward', 19.68, 42.3, 41.04, 0.25, 40.44),
('Paper1 For Evaluation', 19.95, 49.77, 0.03, 0.8, 56.16),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 20.07, 32.64, 0, 30.09, 45.6);

-- --------------------------------------------------------

--
-- Table structure for table `DeadlineSatsified`
--

CREATE TABLE `DeadlineSatsified` (
  `methodNumber` text NOT NULL,
  `task10` double NOT NULL,
  `task20` double NOT NULL,
  `task30` double NOT NULL,
  `task40` double NOT NULL,
  `task50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DeadlineSatsified`
--

INSERT INTO `DeadlineSatsified` (`methodNumber`, `task10`, `task20`, `task30`, `task40`, `task50`) VALUES
('Only CLoud', 5.4, 6.2, 5.6, 6.3, 6.3),
('Random Placement', 12.8, 81, 74, 60.5, 41.45),
('Edgeward', 9.6, 33.55, 33.1, 52.6, 33.75),
('Paper1 For Evaluation', 9.3, 19, 41.5, 55.9, 63.5),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 12, 26.3, 46.1, 60.2, 63.8);

-- --------------------------------------------------------

--
-- Table structure for table `Fog_CPU_Utilization`
--

CREATE TABLE `Fog_CPU_Utilization` (
  `methodNumber` text NOT NULL,
  `service10` double NOT NULL,
  `service20` double NOT NULL,
  `service30` double NOT NULL,
  `service40` double NOT NULL,
  `service50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Fog_CPU_Utilization`
--

INSERT INTO `Fog_CPU_Utilization` (`methodNumber`, `service10`, `service20`, `service30`, `service40`, `service50`) VALUES
('Only CLoud', 0, 0, 0, 0, 0),
('Random Placement', 74, 76, 88, 88, 72),
('Edgeward', 82, 54, 69, 83, 56),
('Paper1 For Evaluation', 25, 25, 77, 77, 25),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 77, 31, 67, 95, 29);

-- --------------------------------------------------------

--
-- Table structure for table `Fog_RAM_Utilization`
--

CREATE TABLE `Fog_RAM_Utilization` (
  `methodNumber` text NOT NULL,
  `service10` double NOT NULL,
  `service20` double NOT NULL,
  `service30` double NOT NULL,
  `service40` double NOT NULL,
  `service50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Fog_RAM_Utilization`
--

INSERT INTO `Fog_RAM_Utilization` (`methodNumber`, `service10`, `service20`, `service30`, `service40`, `service50`) VALUES
('Only CLoud', 0, 0, 0, 0, 0),
('Random Placement', 78, 80, 85, 85, 80),
('Edgeward', 84, 94, 85, 89, 81),
('Paper1 For Evaluation', 100, 100, 96, 96, 100),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 75, 98, 73, 61, 100);

-- --------------------------------------------------------

--
-- Table structure for table `Fog_Resource_Wastage`
--

CREATE TABLE `Fog_Resource_Wastage` (
  `methodNumber` text NOT NULL,
  `service10` double NOT NULL,
  `service20` double NOT NULL,
  `service30` double NOT NULL,
  `service40` double NOT NULL,
  `service50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Fog_Resource_Wastage`
--

INSERT INTO `Fog_Resource_Wastage` (`methodNumber`, `service10`, `service20`, `service30`, `service40`, `service50`) VALUES
('Only CLoud', 0, 0, 0, 0, 0),
('Random Placement', 4.36, 3.58, 2.55, 2.55, 4.45),
('Edgeward', 1.2, 2.36, 1.89, 1.48, 2.92),
('Paper1 For Evaluation', 8.52, 8.52, 1.4, 1.4, 8.52),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 4.33, 7.9, 1.54, 3.44, 8.06);

-- --------------------------------------------------------

--
-- Table structure for table `Max_Task_Delay`
--

CREATE TABLE `Max_Task_Delay` (
  `methodNumber` text NOT NULL,
  `task10` double NOT NULL,
  `task20` double NOT NULL,
  `task30` double NOT NULL,
  `task40` double NOT NULL,
  `task50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Max_Task_Delay`
--

INSERT INTO `Max_Task_Delay` (`methodNumber`, `task10`, `task20`, `task30`, `task40`, `task50`) VALUES
('Only CLoud', 5703.2, 5735.2, 5739, 5690, 5678.8),
('Random Placement', 5580.8, 831.32, 831.32, 831.32, 5652.6),
('Edgeward', 5700.6, 5630, 5676, 3986.2, 5625.6),
('Paper1 For Evaluation', 5766.4, 4671.4, 4046.8, 4054, 4049.2),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 5616, 5646.4, 5315.1, 3870, 3893.8);

-- --------------------------------------------------------

--
-- Table structure for table `Min_Task_Delay`
--

CREATE TABLE `Min_Task_Delay` (
  `methodNumber` text NOT NULL,
  `task10` double NOT NULL,
  `task20` double NOT NULL,
  `task30` double NOT NULL,
  `task40` double NOT NULL,
  `task50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Min_Task_Delay`
--

INSERT INTO `Min_Task_Delay` (`methodNumber`, `task10`, `task20`, `task30`, `task40`, `task50`) VALUES
('Only CLoud', 1770, 1764.9, 1761.8, 1764.2, 1719.75),
('Random Placement', 97.2, 831.32, 831.32, 831.32, 79.6),
('Edgeward', 94.35, 64.35, 52.55, 54.45, 68.95),
('Paper1 For Evaluation', 119.2, 99.2, 67.6, 64.1, 57.5),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 68.4, 67.9, 46.35, 52.5, 51.2);

-- --------------------------------------------------------

--
-- Table structure for table `PowerConsumption`
--

CREATE TABLE `PowerConsumption` (
  `methodNumber` text NOT NULL,
  `service10` double NOT NULL,
  `service20` double NOT NULL,
  `service30` double NOT NULL,
  `service40` double NOT NULL,
  `service50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PowerConsumption`
--

INSERT INTO `PowerConsumption` (`methodNumber`, `service10`, `service20`, `service30`, `service40`, `service50`) VALUES
('Only CLoud', 22975.1, 38317, 40271.2, 156676.95, 196023.55),
('Random Placement', 21328.985, 39439.085, 110030.64, 151468.84, 41151.865),
('Edgeward', 18679.97, 39215.155, 40114.25, 5155.99, 40041.155),
('Paper1 For Evaluation', 17805.055, 39573.555, 5164.82, 5054.06, 37254.555),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 18987.06, 38862.15, 1159.98, 41968.57, 37696.28);

-- --------------------------------------------------------

--
-- Table structure for table `ServicePlacement`
--

CREATE TABLE `ServicePlacement` (
  `methodNumber` text NOT NULL,
  `task10` text NOT NULL,
  `task20` text NOT NULL,
  `task30` text NOT NULL,
  `task40` text NOT NULL,
  `task50` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ServicePlacement`
--

INSERT INTO `ServicePlacement` (`methodNumber`, `task10`, `task20`, `task30`, `task40`, `task50`) VALUES
('Only CLoud', '0.0,0.0,100.0', '0.0,0.0,100.0', '0.0,0.0,100.0', '0.0,0.0,100.0', '0.0,0.0,100.0'),
('Random Placement', '11.0,1.0,88.0', '33.0,28.0,39.0', '33.0,28.0,39.0', '33.0,28.0,39.0', '33.0,32.5,34.5'),
('Edgeward', '12.0,0.0,88.0', '50.0,0.0,50.0', '56.00000000000001,0.0,44.0', '92.0,0.0,8.0', '49.5,0.0,50.5'),
('Paper1 For Evaluation', '11.0,1.0,88.0', '16.0,12.0,72.0', '56.00000000000001,18.0,26.0', '86.0,5.0,9.0', '100.0,0.0,0.0'),
('Paper2 For Evaluation', '0', '0', '0', '0', '0'),
('Our Proposed', '14.000000000000002,1.0,85.0', '21.0,11.0,68.0', '55.00000000000001,25.0,20.0', '93.0,5.0,2.0', '97.0,3.0,0.0');

-- --------------------------------------------------------

--
-- Table structure for table `Service_Type`
--

CREATE TABLE `Service_Type` (
  `numberOfService` int(11) NOT NULL,
  `criticalService` int(11) NOT NULL,
  `realService` int(11) NOT NULL,
  `normalService` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Service_Type`
--

INSERT INTO `Service_Type` (`numberOfService`, `criticalService`, `realService`, `normalService`) VALUES
(100, 3, 3, 4),
(200, 6, 6, 8),
(300, 10, 10, 10),
(400, 13, 13, 14),
(500, 16, 16, 18);

-- --------------------------------------------------------

--
-- Table structure for table `Task_Average_Delay_Of_Each_Type`
--

CREATE TABLE `Task_Average_Delay_Of_Each_Type` (
  `numberOfTask` int(11) NOT NULL,
  `criticalRequestAverageDelay` double NOT NULL,
  `realRequestAverageDelay` double NOT NULL,
  `normalRequestAverageDelay` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Task_Average_Delay_Of_Each_Type`
--

INSERT INTO `Task_Average_Delay_Of_Each_Type` (`numberOfTask`, `criticalRequestAverageDelay`, `realRequestAverageDelay`, `normalRequestAverageDelay`) VALUES
(100, 160.97, 0, 1304.17),
(200, 173.07, 0, 1449.35),
(300, 1444.99, 1981.63, 2016.57),
(400, 614.93, 0, 2839.64),
(500, 1498.12, 2006.99, 2028.12);

-- --------------------------------------------------------

--
-- Table structure for table `Task_Energy_Consumption`
--

CREATE TABLE `Task_Energy_Consumption` (
  `methodNumber` text NOT NULL,
  `task10` double NOT NULL,
  `task20` double NOT NULL,
  `task30` double NOT NULL,
  `task40` double NOT NULL,
  `task50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Task_Energy_Consumption`
--

INSERT INTO `Task_Energy_Consumption` (`methodNumber`, `task10`, `task20`, `task30`, `task40`, `task50`) VALUES
('Only CLoud', 31964296.5, 57091778.9, 24900201, 33754504.5, 56139565.4),
('Random Placement', 28907927.6242, 14622.4664, 10691.2072, 6494.3217, 30118080.2079),
('Edgeward', 29854626.3124, 32289332.6362, 14635437.6495, 8907684.7983, 33911920.8874),
('Paper1 For Evaluation', 24095463.0622, 17637995.1108, 8704385.5504, 7735622.3471, 7131321.1053),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 30009760.5536, 29465963.3882, 13491898.8506, 7332275.2731, 7817591.1089);

-- --------------------------------------------------------

--
-- Table structure for table `Task_Type`
--

CREATE TABLE `Task_Type` (
  `numberOfTask` int(11) NOT NULL,
  `criticalRequest` int(11) NOT NULL,
  `realRequest` int(11) NOT NULL,
  `normalRequest` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Task_Type`
--

INSERT INTO `Task_Type` (`numberOfTask`, `criticalRequest`, `realRequest`, `normalRequest`) VALUES
(100, 30, 0, 70),
(200, 60, 0, 140),
(300, 108, 97, 95),
(400, 120, 0, 280),
(500, 167, 165, 168);

-- --------------------------------------------------------

--
-- Table structure for table `Total_Task_Delay`
--

CREATE TABLE `Total_Task_Delay` (
  `methodNumber` text NOT NULL,
  `task10` double NOT NULL,
  `task20` double NOT NULL,
  `task30` double NOT NULL,
  `task40` double NOT NULL,
  `task50` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Total_Task_Delay`
--

INSERT INTO `Total_Task_Delay` (`methodNumber`, `task10`, `task20`, `task30`, `task40`, `task50`) VALUES
('Only CLoud', 2628341, 5238742, 2580775.5, 2645466.5, 5140348.5),
('Random Placement', 2358277, 831.32, 831.32, 831.32, 3093553.5),
('Edgeward', 2474093, 3479836, 1734823.5, 1092376.5, 3534332.5),
('Paper1 For Evaluation', 2387561, 2045615, 1355770.5, 1048917, 867572),
('Paper2 For Evaluation', 0, 0, 0, 0, 0),
('Our Proposed', 2410141.5, 2071584.5, 1473258, 1018764.5, 940636.5);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
