-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 12, 2022 at 07:09 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.4.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `TMS`
--

-- --------------------------------------------------------

--
-- Table structure for table `stopppage`
--

CREATE TABLE `stopppage` (
  `sname` text NOT NULL,
  `route` text NOT NULL,
  `latitude` text NOT NULL,
  `longitude` text NOT NULL,
  `No` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stopppage`
--

INSERT INTO `stopppage` (`sname`, `route`, `latitude`, `longitude`, `No`) VALUES
('Surma_Tower', '2', '24.890491', '91.867585', 1),
('Jithu_Mias_Point', '2', '24.890559', '91.860054', 2),
('Lama_Bazar', '2', '24.896223', '91.861438', 3),
('Rikabi_Bazar', '2', '24.8990212', '91.862321', 4);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_allowcatedbus`
--

CREATE TABLE `tbl_allowcatedbus` (
  `No` int(101) NOT NULL,
  `busid` text NOT NULL,
  `frm` text NOT NULL,
  `dest` text NOT NULL,
  `route` text NOT NULL,
  `time` text NOT NULL,
  `datee` text NOT NULL,
  `reach` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_allowcatedbus`
--

INSERT INTO `tbl_allowcatedbus` (`No`, `busid`, `frm`, `dest`, `route`, `time`, `datee`, `reach`) VALUES
(1, '01', 'XYZ', 'ABC', '2', '9:00', '12-09-2022', '9.45'),
(2, '02', 'XYZ', 'ABC', '2', '6:00', '12-09-2022', '9.45'),
(3, '03', 'XYZ', 'ABC', '2', '2:00', '12-09-2022', '9.45');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_location`
--

CREATE TABLE `tbl_location` (
  `No` int(11) NOT NULL,
  `busid` text NOT NULL,
  `route` text NOT NULL,
  `latitude` text NOT NULL,
  `longitude` text NOT NULL,
  `datee` text NOT NULL,
  `time` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_location`
--

INSERT INTO `tbl_location` (`No`, `busid`, `route`, `latitude`, `longitude`, `datee`, `time`) VALUES
(1, '01', '2', '24.89045748', '91.86762873', '9:00', '11-9-2022');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_requestseat`
--

CREATE TABLE `tbl_requestseat` (
  `No` int(11) NOT NULL,
  `time` text NOT NULL,
  `uname` text NOT NULL,
  `date` text NOT NULL,
  `route` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_requestseat`
--

INSERT INTO `tbl_requestseat` (`No`, `time`, `uname`, `date`, `route`) VALUES
(1, '12:0', 'Mr. Roy', '11-9-2022', '1'),
(2, '0:0', 'fayez', '12-9-2022', '2');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `No` int(11) NOT NULL,
  `sid` text NOT NULL,
  `role` text NOT NULL,
  `uname` text NOT NULL,
  `fname` text NOT NULL,
  `pass` text NOT NULL,
  `phone` text NOT NULL,
  `dept` text NOT NULL,
  `pick` text NOT NULL,
  `section` text NOT NULL,
  `batch` text NOT NULL,
  `codename` text NOT NULL,
  `designation` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`No`, `sid`, `role`, `uname`, `fname`, `pass`, `phone`, `dept`, `pick`, `section`, `batch`, `codename`, `designation`) VALUES
(4, '1812020128', 'Student', 'Mr. Roy', 'Nilashish Roy', '123', '01746862116', 'Architecture', 'CSE', 'C', '48', 'null', 'null'),
(5, '8926', 'Teacher', 'fayez', 'Fayez Ali', '123', '26595', 'BBA', 'Surma_Tower', 'null', 'null', 'HYH', 'GUGD HXW');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `stopppage`
--
ALTER TABLE `stopppage`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `tbl_allowcatedbus`
--
ALTER TABLE `tbl_allowcatedbus`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `tbl_requestseat`
--
ALTER TABLE `tbl_requestseat`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`No`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `stopppage`
--
ALTER TABLE `stopppage`
  MODIFY `No` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_allowcatedbus`
--
ALTER TABLE `tbl_allowcatedbus`
  MODIFY `No` int(101) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_requestseat`
--
ALTER TABLE `tbl_requestseat`
  MODIFY `No` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `No` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
