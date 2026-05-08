-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 22, 2025 at 03:45 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `beasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `kriteria`
--

CREATE TABLE `kriteria` (
  `kode_kriteria` varchar(10) NOT NULL,
  `nama_kriteria` varchar(100) DEFAULT NULL,
  `bobot_kriteria` double DEFAULT NULL,
  `atribut` enum('Benefit','Cost') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kriteria`
--

INSERT INTO `kriteria` (`kode_kriteria`, `nama_kriteria`, `bobot_kriteria`, `atribut`) VALUES
('C1', 'Pendapatan Orang Tua', 5, 'Cost'),
('C2', 'Pekerjaan Orang Tua', 3, 'Benefit'),
('C3', 'Kondisi Tempat Tinggal', 4, 'Cost'),
('C4', 'Kepemilikan Aset', 2, 'Cost'),
('C5', 'Jumlah Tanggungan', 3, 'Benefit');

-- --------------------------------------------------------

--
-- Table structure for table `penilaian`
--

CREATE TABLE `penilaian` (
  `kd_alternatif` varchar(20) NOT NULL,
  `NISN` varchar(20) NOT NULL,
  `Nama` varchar(50) NOT NULL,
  `C1` double NOT NULL,
  `C2` double NOT NULL,
  `C3` double NOT NULL,
  `C4` double NOT NULL,
  `C5` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `kd_alternatif` varchar(20) NOT NULL,
  `NISN` varchar(20) NOT NULL,
  `Nama` varchar(50) NOT NULL,
  `tgllahir` date NOT NULL,
  `jk` varchar(30) NOT NULL,
  `Kelas` varchar(10) NOT NULL,
  `Alamat` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subkriteria`
--

CREATE TABLE `subkriteria` (
  `id_subkriteria` int(11) NOT NULL,
  `kode_kriteria` varchar(10) DEFAULT NULL,
  `nama_subkriteria` varchar(100) DEFAULT NULL,
  `bobot_subkriteria` double(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subkriteria`
--

INSERT INTO `subkriteria` (`id_subkriteria`, `kode_kriteria`, `nama_subkriteria`, `bobot_subkriteria`) VALUES
(1, 'C1', '< Rp. 1.000.000', 5.00),
(2, 'C1', '>Rp. 1.000.000 - <Rp. 2.000.000', 4.00),
(3, 'C1', '>Rp. 2.000.000 - <Rp. 3.000.000', 3.00),
(4, 'C1', '>Rp. 3.000.000 - <Rp. 4.000.000', 2.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kriteria`
--
ALTER TABLE `kriteria`
  ADD PRIMARY KEY (`kode_kriteria`);

--
-- Indexes for table `penilaian`
--
ALTER TABLE `penilaian`
  ADD PRIMARY KEY (`kd_alternatif`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`kd_alternatif`);

--
-- Indexes for table `subkriteria`
--
ALTER TABLE `subkriteria`
  ADD PRIMARY KEY (`id_subkriteria`),
  ADD KEY `kode_kriteria` (`kode_kriteria`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `subkriteria`
--
ALTER TABLE `subkriteria`
  MODIFY `id_subkriteria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `subkriteria`
--
ALTER TABLE `subkriteria`
  ADD CONSTRAINT `subkriteria_ibfk_1` FOREIGN KEY (`kode_kriteria`) REFERENCES `kriteria` (`kode_kriteria`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
