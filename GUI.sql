-- Buat database baru
CREATE DATABASE gui_app_db;

-- Gunakan database yang baru saja dibuat
USE gui_app_db;

-- Buat tabel users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    favorite_movie VARCHAR(50)
);

-- Masukkan beberapa data awal ke dalam tabel users
INSERT INTO users (name, favorite_movie) VALUES ('Alice', 'The Matrix');
INSERT INTO users (name, favorite_movie) VALUES ('Bob', 'Inception');
INSERT INTO users (name, favorite_movie) VALUES ('Charlie', 'Interstellar');
