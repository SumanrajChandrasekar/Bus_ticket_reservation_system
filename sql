CREATE DATABASE bus_reservation;
USE bus_reservation;

CREATE TABLE buses (
    bus_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_name VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50),
    seats INT
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_id INT,
    passenger_name VARCHAR(50),
    FOREIGN KEY (bus_id) REFERENCES buses(bus_id)
);
