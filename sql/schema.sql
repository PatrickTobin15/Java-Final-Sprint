-- ============================================
-- Gym Management System - Database Schema
-- Run this script in PostgreSQL to set up the DB
-- ============================================

-- Create the database (run this separately if needed)
-- CREATE DATABASE gym_db;

-- Connect to gym_db before running the rest:
-- \c gym_db

-- ============================================
-- USERS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    userId       SERIAL PRIMARY KEY,
    userName     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,  -- BCrypt hashed
    email        VARCHAR(100) NOT NULL,
    phoneNumber  VARCHAR(20),
    address      VARCHAR(255),
    userRole     VARCHAR(20)  NOT NULL CHECK (userRole IN ('Admin', 'Trainer', 'Member'))
);

-- ============================================
-- MEMBERSHIPS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS memberships (
    membershipId          SERIAL PRIMARY KEY,
    membershipType        VARCHAR(50)  NOT NULL,
    membershipDescription VARCHAR(255),
    membershipCost        DECIMAL(10,2) NOT NULL,
    memberId              INT NOT NULL REFERENCES users(userId) ON DELETE CASCADE
);

-- ============================================
-- WORKOUT CLASSES TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS workoutclasses (
    workoutClassId          SERIAL PRIMARY KEY,
    workoutClassType        VARCHAR(50)  NOT NULL,
    workoutClassDescription VARCHAR(255),
    trainerId               INT NOT NULL REFERENCES users(userId) ON DELETE CASCADE
);

-- ============================================
-- GYM MERCHANDISE TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS gymmerch (
    merchId         SERIAL PRIMARY KEY,
    merchName       VARCHAR(100) NOT NULL,
    merchType       VARCHAR(50)  NOT NULL,
    merchPrice      DECIMAL(10,2) NOT NULL,
    quantityInStock INT NOT NULL DEFAULT 0
);

-- ============================================
-- SAMPLE DATA
-- ============================================

-- NOTE: passwords below are BCrypt hashes of 'password123'
INSERT INTO users (userName, password, email, phoneNumber, address, userRole)
VALUES
('admin1',   '$2a$10$7EqJtq98hPqEX7fNZaFWoOe3wAhPFQv2vOhLUwNzBh3N2hNQzNkMy', 'admin@gym.com',   '709-555-0001', '1 Admin Street',    'Admin'),
('trainer1', '$2a$10$7EqJtq98hPqEX7fNZaFWoOe3wAhPFQv2vOhLUwNzBh3N2hNQzNkMy', 'trainer@gym.com', '709-555-0002', '2 Trainer Avenue',  'Trainer'),
('member1',  '$2a$10$7EqJtq98hPqEX7fNZaFWoOe3wAhPFQv2vOhLUwNzBh3N2hNQzNkMy', 'member@gym.com',  '709-555-0003', '3 Member Boulevard','Member');

INSERT INTO workoutclasses (workoutClassType, workoutClassDescription, trainerId)
VALUES
('Yoga',    'Beginner-friendly yoga session',        2),
('HIIT',    'High intensity interval training',      2),
('Spin',    'Indoor cycling cardio class',           2);

INSERT INTO gymmerch (merchName, merchType, merchPrice, quantityInStock)
VALUES
('Gym Water Bottle', 'gear',  15.99, 50),
('Protein Bar',      'food',  3.49,  100),
('Energy Drink',     'drink', 4.99,  75),
('Gym T-Shirt',      'gear',  24.99, 30),
('Resistance Bands', 'gear',  19.99, 40);

INSERT INTO memberships (membershipType, membershipDescription, membershipCost, memberId)
VALUES
('Monthly', 'One month full access', 49.99, 3),
('Annual',  'Full year access',      449.99, 3);
