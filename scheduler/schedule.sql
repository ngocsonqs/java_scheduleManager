-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2016 年 5 朁E17 日 02:14
-- サーバのバージョン： 5.6.25
-- PHP Version: 5.6.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `schedule`
--

-- --------------------------------------------------------

--
-- テーブルの構造 `schedule`
--

CREATE TABLE IF NOT EXISTS `schedule` (
  `username` varchar(16) NOT NULL COMMENT 'ユーザ名',
  `schedule_date` date NOT NULL COMMENT 'スケジュールの日付',
  `schedule_time` time NOT NULL COMMENT 'スケジュールの時間',
  `title` varchar(128) NOT NULL COMMENT 'スケジュールのタイトル',
  `content` text NOT NULL COMMENT 'スケジュールの内容',
  `insert_datetime` datetime NOT NULL COMMENT 'レコードの登録日時',
  `update_datetime` timestamp NULL DEFAULT NULL COMMENT 'レコードの更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(16) NOT NULL COMMENT 'ユーザ名',
  `password` varchar(16) NOT NULL COMMENT 'パスワード',
  `name` varchar(32) NOT NULL COMMENT '名前',
  `birthday` date NOT NULL COMMENT '名前',
  `admin_flg` int(11) NOT NULL DEFAULT '0' COMMENT '0:一般, 1:管理者',
  `created` datetime NOT NULL COMMENT 'レコードの登録日時',
  `modified` timestamp NULL DEFAULT NULL COMMENT 'レコードの更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='???[?U???Ǘ??????e?[?u??';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`username`,`schedule_date`,`schedule_time`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
