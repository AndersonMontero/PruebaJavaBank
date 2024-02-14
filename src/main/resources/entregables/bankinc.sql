-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-02-2024 a las 07:27:13
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bankinc`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `id_admin` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `apellido` varchar(40) NOT NULL,
  `id_tarjeta` int(16) NOT NULL,
  `fecha_bloqueo` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `numero_idenficacion` int(15) NOT NULL,
  `tipo_identifacion` varchar(5) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `tipo_tarjeta` varchar(10) NOT NULL,
  `id_tarjeta` int(16) NOT NULL,
  `fecha_creacion` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento`
--

CREATE TABLE `movimiento` (
  `id` int(11) NOT NULL,
  `id_tarjeta` int(16) NOT NULL,
  `fecha_movimiento` date NOT NULL,
  `monto_dinero` int(11) NOT NULL,
  `tipo_operacion` bigint(3) NOT NULL DEFAULT 1 COMMENT '1: Compra\r\n2: avance\r\n3: retiro',
  `estado_movimiento` bigint(2) NOT NULL DEFAULT 1 COMMENT '1: compra/avance\r\n2: anulada'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `movimiento`
--

INSERT INTO `movimiento` (`id`, `id_tarjeta`, `fecha_movimiento`, `monto_dinero`, `tipo_operacion`, `estado_movimiento`) VALUES
(1, 1, '2024-02-12', 10, 1, 2),
(2, 1, '2024-02-14', 20, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarjeta`
--

CREATE TABLE `tarjeta` (
  `id` int(11) NOT NULL,
  `id_producto` varchar(6) NOT NULL,
  `numero_tarjeta` varchar(16) NOT NULL,
  `nombre_titular` varchar(100) DEFAULT NULL,
  `fecha_vencimiento` date NOT NULL,
  `id_tipo_tarjeta` bigint(2) DEFAULT NULL COMMENT '1: Credito\r\n2: Debito',
  `tipo_tarjeta` varchar(10) DEFAULT NULL,
  `estado_tarjeta` bigint(20) NOT NULL DEFAULT 2 COMMENT '1: Activa\r\n2: Inactiva\r\n3: Bloqueada',
  `saldo` decimal(10,2) NOT NULL,
  `fecha_creacion` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `tarjeta`
--

INSERT INTO `tarjeta` (`id`, `id_producto`, `numero_tarjeta`, `nombre_titular`, `fecha_vencimiento`, `id_tipo_tarjeta`, `tipo_tarjeta`, `estado_tarjeta`, `saldo`, `fecha_creacion`) VALUES
(1, '123456', '1234560123456789', 'Anderson', '2024-02-17', 1, 'Credito', 1, 30.00, '2024-02-07'),
(2, '987654', '9876541076774658', NULL, '2024-02-01', NULL, NULL, 3, 70.00, '2024-02-15'),
(987659, '369852', '3698521059296387', NULL, '2027-02-11', NULL, NULL, 1, 0.00, '2024-02-11'),
(987661, '012345', '0123451065975768', NULL, '2027-02-12', NULL, NULL, 1, 0.00, '2024-02-12');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `id_tarjeta` (`id_tarjeta`);

--
-- Indices de la tabla `movimiento`
--
ALTER TABLE `movimiento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tarjeta_movimientos` (`id_tarjeta`);

--
-- Indices de la tabla `tarjeta`
--
ALTER TABLE `tarjeta`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `administrador`
--
ALTER TABLE `administrador`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `movimiento`
--
ALTER TABLE `movimiento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tarjeta`
--
ALTER TABLE `tarjeta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=987662;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_tarjeta`) REFERENCES `tarjeta` (`id`);

--
-- Filtros para la tabla `movimiento`
--
ALTER TABLE `movimiento`
  ADD CONSTRAINT `tarjeta_movimientos` FOREIGN KEY (`id_tarjeta`) REFERENCES `tarjeta` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
