----Book
--
INSERT INTO lib.book (available_copies, book_id, year_published, author, genre, image_url, isbn, title) VALUES
(50, 1, 2011, 'Veronica Roth', 'Action', '/uploads/e1ccc0c4-930c-4c21-b13b-c8b37c843787_Divergent.jpg', 'B004CFA9RS', 'Divergent'),
(50, 2, 2015, 'J.K. Rowling', 'Action', '/uploads/7b25a0e0-838c-4000-b1e6-f522c88b4e3d_Harry Potter and the Sorcerer.jpg', 'B0192CTN72', 'Harry Potter and the Sorcerer''s Stone'),
(50, 3, 2015, 'J.K. Rowling', 'Action', '/uploads/613c5f9c-c757-44a4-8b66-bf482b467014_Harry potter and the Goblet.jpg', 'B0192CTMUU', 'Harry Potter and the Goblet of Fire'),
(50, 4, 2015, 'J.K. Rowling', 'Action', '/uploads/8f917d17-7cab-4348-a232-2c16a4d94cfc_Harry potter and the Prisoner.jpg', 'B0192CTMX2', 'Harry Potter and the Prisoner of Azkaban'),
(50, 5, 2015, 'J.K. Rowling', 'Action', '/uploads/7ea3e299-62c0-4207-b233-20b919571af7_Harry potter and the Order of the Phoenix.jpg', 'B0192CTMXM', 'Harry Potter and the Order of the Phoenix'),
(50, 6, 2003, 'George R. R. Martin', 'Action', '/uploads/31eb3b7e-07e4-4a6c-9ecb-efc243154220_A Game of Thrones.jpg', 'B000QCS8TW', 'A Game of Thrones'),
(50, 7, 1960, 'Harper Lee', 'Fiction', '/uploads/416bbd42-ade0-4b1e-a0af-6951bb951053_To kill a mockingbird.jpg', 'AB1234566', 'To Kill a Mockingbird'),
(50, 8, 1957, 'William Shakespeare', 'Drama', '/uploads/231d35ea-bc36-4fdc-aa14-23487fca484d_18135.jpg', 'BE8765432', 'Romeo and Juliet'),
(50, 9, 1949, 'George Orwell', 'Fiction', '/uploads/9f9b86d3-f048-4e29-ab08-3c622cc9762b_5470.jpg', 'CD123456', '1984'),
(50, 10, 1925, 'F. Scott Fitzgerald', 'Fiction', '/uploads/025b0ff1-717c-4614-8be7-e1c3775c1d49_41733839.jpg', 'EW1234565', 'The Great Gatsby'),
(50, 11, 1947, 'George Orwell', 'Fiction', '/uploads/718cd61d-174f-4c14-a0e6-f6623cb4d320_170448.jpg', 'FI1234567', 'Animal Farm'),
(50, 12, 1951, 'J.D. Salinger', 'Fiction', '/uploads/243f3d0c-fac1-4ba0-bb57-10bd7355187b_5107.jpg', 'FI543287', 'The Catcher in the Rye'),
(50, 13, 2007, 'Ravinder Singh', 'Love Story', '/uploads/94cb7b3f-8348-43a7-90fd-7ad27ea1ea5e_md_9780143418764.jpg', 'LS234567', 'I Too Had a Love Story'),
(50, 14, 2011, 'Ravinder Singh', 'Love Story', '/uploads/a6e2ff1d-3f4f-4725-9658-6264892ab1f1_13041934.jpg', 'LS876543', 'Can Love Happen Twice?'),
(50, 15, 2014, 'Ravinder Singh', 'Love Story', '/uploads/75dbc75e-e97c-47c5-a4d2-36b6cbfd3900_23301981.jpg', 'LS3456788', 'Your Dreams Are Mine Now'),
(50, 16, 2022, 'Lancali', 'Romance', '/uploads/a54f0957-4e30-4fb1-9035-55ce48bab4d2_61713106.jpg', 'RO2345678', 'I Fell in Love with Hope'),
(50, 17, 2024, 'Laura Nowlin', 'Romance', '/uploads/03d7b6a6-b143-4850-ad5e-ba8510c401f4_64645812.jpg', 'RO2345678', 'If Only I Had Told Her'),
(50, 18, 2024, 'Shrijeet Shandilya', 'Romance', '/uploads/b44807d2-5065-4b1c-8ddc-8da3072fb780_222911108.jpg', 'RO3456789', 'Can We Be Strangers Again?'),
(50, 19, 1978, 'Dennis M. Ritchie', 'Technology', '/uploads/24fa2b1c-c487-4274-ac38-0ca4332c72a2_515601.jpg', 'TE23456789', 'The C Programming Language'),
(50, 20, 1989, 'Thomas H. Cormen', 'Technology', '/uploads/0d6f7955-0543-4bd9-b2e8-f022e616eafc_108986.jpg', 'TO345678', 'Introduction to Algorithms'),
(50, 21, 2007, 'Robert C. Martin', 'Technology', '/uploads/f2b9a2b6-9693-492c-bfd7-e2787c199eb7_3735293.jpg', 'TO2345678', 'Clean Code: A Handbook of Agile Software Craftsmanship'),
(50, 22, 1999, 'Dave Thomas', 'Technology', '/uploads/81bde26f-ccf4-4739-8aab-ef076810b639_4099.jpg', 'TO2345678', 'The Pragmatic Programmer: From Journeyman to Master'),
(50, 23, 1968, 'Shel Silverstein', 'Fantasy', '/uploads/0eaff911-247c-436f-a782-28a41ac77be9_370493.jpg', 'FA1234567', 'The Giving Tree'),
(50, 24, 1957, 'Dr. Seuss', 'Fantasy', '/uploads/b96b4857-e677-4a54-aa62-39eea02a0279_233093.jpg', 'FA123456', 'The Cat in the Hat'),
(50, 25, 1952, 'E.B. White', 'Classics', '/uploads/94c2e9d7-b251-4939-ad63-572f7cc4c89a_24178.jpg', 'CL2345678', 'Charlotte’s Web');

------Member
INSERT INTO lib.member (member_id, name, email, phone, address, membership_status, password, user_role) VALUES
(1, 'David Miller', 'admin@example.com', '6543210987', '1011 Road, County', 'ACTIVE', '$2a$10$qZ83LpZEz.Ln0GOibBK3o.FcKlV0FNtEE.2nOL/npd9YbHWQ0WNei', 'ADMIN'),
(2, 'Alice', 'alice@gamil.com', '9876543210', 'hyderabad', 'ACTIVE', '$2a$10$7YP/hJSMHHb1urKPsUk9jeZRS.gJP1f2l.fcimnVqr5FMXiyDnRsu', 'MEMBER'),
(3, 'John', 'john@gmail.com', '8765432109', 'Tpt', 'ACTIVE', '$2a$10$1eOvj1fO5rVIdgVlAOfyfOhk6LrUKAX7UCZnjs7eaMw9.2CgL0pJu', 'MEMBER'),
(4, 'Charlie', 'charlie@gmail.com', '7654321098', 'Lane village', 'ACTIVE', '$2a$10$0WqRQHT7XqWkNZkreiRc2Ot9QYX9blZ2lFwnsR/VYo7PvMHjivZ8.', 'MEMBER'),
(5, 'Ella', 'ella@gmail.com', '5432109876', 'UK', 'ACTIVE', '$2a$10$HsekIdjTk18jyxJVP5pp7eoYVy.ybfHU/R05lUHQTUBbiFRjPtnxW', 'MEMBER'),
(6, 'Davis', 'davis@gmail.com', '4321098765', 'Bangalore', 'ACTIVE', '$2a$10$RNSnqcMYVyb6z973tf0cjelaHVRhXiLpzGEv7VNnAOuf.OOPCtyOq', 'MEMBER'),
(7, 'Frank', 'frank@gmail.com', '6543210988', 'Chennai', 'ACTIVE', '$2a$10$j3.LSp7PtXvNQ.XX2RnG8uBeSKZTwPVRCMkxMp/RYRx1FvZWd5PU6', 'MEMBER'),
(8, 'Henry', 'henry@gmail.com', '4321098766', 'Bangalore', 'ACTIVE', '$2a$10$MCLlFvwVodgnkhPdm6OIDuerPwdNibrC6d5Ulj1NMVvTCf8zv33Su', 'MEMBER'),
(9, 'Grace', 'grace@gmail.com', '3210987654', '123 Lane', 'ACTIVE', '$2a$10$Q/CyR688SweT/WrgFYBaVOu/XdjLG8tpgLbv4dpyDHkG2puJEF/Hq', 'MEMBER'),
(10, 'Jack', 'jack@gmail.com', '0987654321', '2223 Avenue, Town', 'ACTIVE', '$2a$10$pQWYy0Vpn6hsAllnJTOZrOGYgGj7/Xe/Zg16rrvSVyL68x6B1N0Iy', 'MEMBER'),
(11, 'White', 'white@gmail.com', '8765433332', 'Chennai', 'ACTIVE', '$2a$10$C0VtQ7YHkrpmkPrZt6U8geIzsqFu3HP9xab5mbh.q2XEoNPY8IqC.', 'MEMBER');

--password for david - davidPass789
------Borrowing Transaction
--INSERT INTO lib.borrowing_transaction (book_id, borrow_date, return_date, member_id, transactionid, status) VALUES
---- Member 2
--(12,  '2025-06-01', '2025-06-04', 2, 1,  'RETURNED'),
--(13,  '2025-06-05', NULL,         2, 2,  'BORROWED'),
--(14,  '2025-06-07', NULL,         2, 3,  'BORROWED'),
--(15,  '2025-06-08', NULL,         2, 4,  'BORROWED'),
--(6,  '2025-06-09', '2025-06-12', 2, 5,  'RETURNED'),
--(7,  '2025-06-10', NULL,         2, 6,  'BORROWED'),
--(8,  '2025-06-11', '2025-06-14', 2, 7,  'RETURNED'),
--(9,  '2025-06-13', NULL,         2, 8,  'BORROWED'),
--(10, '2025-06-14', '2025-06-17', 2, 9,  'RETURNED'),
--(11, '2025-06-15', '2025-06-18', 2, 10, 'RETURNED'),
--
---- Member 3
--(12, '2025-06-01', '2025-06-03', 3, 11, 'RETURNED'),
--(13, '2025-06-04', NULL,         3, 12, 'BORROWED'),
--(14, '2025-06-05', NULL,         3, 13, 'BORROWED'),
--(15, '2025-06-06', '2025-06-09', 3, 14, 'RETURNED'),
--(16, '2025-06-07', NULL,         3, 15, 'BORROWED'),
--(17, '2025-06-08', NULL,         3, 16, 'BORROWED'),
--(18, '2025-06-09', '2025-06-12', 3, 17, 'RETURNED'),
--(19, '2025-06-10', '2025-06-13', 3, 18, 'RETURNED'),
--(20, '2025-06-11', '2025-06-15', 3, 19, 'RETURNED'),
--(21, '2025-06-12', '2025-06-16', 3, 20, 'RETURNED'),
--
---- Member 4
--(22, '2025-06-01', NULL,         4, 21, 'BORROWED'),
--(23, '2025-06-02', NULL,         4, 22, 'BORROWED'),
--(24, '2025-06-03', NULL,         4, 23, 'BORROWED'),
--(25, '2025-06-04', NULL,         4, 24, 'BORROWED'),
--(11,  '2025-06-05', '2025-06-07', 4, 25, 'RETURNED'),
--(12,  '2025-06-06', NULL,         4, 26, 'BORROWED'),
--(13,  '2025-06-07', '2025-06-09', 4, 27, 'RETURNED'),
--(14,  '2025-06-08', NULL,         4, 28, 'BORROWED'),
--(15,  '2025-06-09', '2025-06-12', 4, 29, 'RETURNED'),
--(6,  '2025-06-10', '2025-06-13', 4, 30, 'RETURNED'),
--
---- Member 5
--(7,  '2025-06-01', '2025-06-03', 5, 31, 'RETURNED'),
--(8,  '2025-06-04', NULL,         5, 32, 'BORROWED'),
--(9,  '2025-06-05', NULL,         5, 33, 'BORROWED'),
--(10, '2025-06-06', NULL,         5, 34, 'BORROWED'),
--(11, '2025-06-07', NULL,         5, 35, 'BORROWED'),
--(12, '2025-06-08', NULL,         5, 36, 'BORROWED'),
--(13, '2025-06-09', '2025-06-12', 5, 37, 'RETURNED'),
--(14, '2025-06-10', '2025-06-13', 5, 38, 'RETURNED'),
--(15, '2025-06-11', '2025-06-14', 5, 39, 'RETURNED'),
--(16, '2025-06-12', '2025-06-15', 5, 40, 'RETURNED'),
--
---- Member 6
--(17, '2025-06-01', NULL,         6, 41, 'BORROWED'),
--(18, '2025-06-02', NULL,         6, 42, 'BORROWED'),
--(19, '2025-06-03', NULL,         6, 43, 'BORROWED'),
--(20, '2025-06-04', NULL,         6, 44, 'BORROWED'),
--(21, '2025-06-05', NULL,         6, 45, 'BORROWED'),
--(22, '2025-06-06', '2025-06-09', 6, 46, 'RETURNED'),
--(23, '2025-06-07', '2025-06-10', 6, 47, 'RETURNED'),
--(24, '2025-06-08', '2025-06-11', 6, 48, 'RETURNED'),
--(25, '2025-06-09', '2025-06-12', 6, 49, 'RETURNED'),
--(11,  '2025-06-10', '2025-06-13', 6, 50, 'RETURNED'),
--
---- Member 7
--(12,  '2025-06-01', '2025-06-03', 7, 51, 'RETURNED'),
--(13,  '2025-06-04', NULL,         7, 52, 'BORROWED'),
--(14,  '2025-06-05', NULL,         7, 53, 'BORROWED'),
--(15,  '2025-06-06', NULL,         7, 54, 'BORROWED'),
--(6,  '2025-06-07', NULL,         7, 55, 'BORROWED'),
--(7,  '2025-06-08', NULL,         7, 56, 'BORROWED'),
--(8,  '2025-06-09', '2025-06-12', 7, 57, 'RETURNED'),
--(9,  '2025-06-10', '2025-06-13', 7, 58, 'RETURNED'),
--(10, '2025-06-11', '2025-06-14', 7, 59, 'RETURNED'),
--(11, '2025-06-12', '2025-06-15', 7, 60, 'RETURNED'),
--
---- Member 8
--(12, '2025-06-01', NULL,         8, 61, 'BORROWED'),
--(13, '2025-06-02', NULL,         8, 62, 'BORROWED'),
--(14, '2025-06-03', NULL,         8, 63, 'BORROWED'),
--(15, '2025-06-04', NULL,         8, 64, 'BORROWED'),
--(16, '2025-06-05', NULL,         8, 65, 'BORROWED'),
--(17, '2025-06-06', '2025-06-09', 8, 66, 'RETURNED'),
--(18, '2025-06-07', '2025-06-10', 8, 67, 'RETURNED'),
--(19, '2025-06-08', '2025-06-11', 8, 68, 'RETURNED'),
--(20, '2025-06-09', '2025-06-12', 8, 69, 'RETURNED'),
--(21, '2025-06-10', '2025-06-13', 8, 70, 'RETURNED'),
--
---- Member 9
--(22, '2025-06-01', '2025-06-03', 9, 71, 'RETURNED'),
--(23, '2025-06-04', NULL,         9, 72, 'BORROWED'),
--(24, '2025-06-05', NULL,         9, 73, 'BORROWED'),
--(25, '2025-06-06', NULL,         9, 74, 'BORROWED'),
--(11,  '2025-06-07', NULL,         9, 75, 'BORROWED'),
--(12,  '2025-06-08', NULL,         9, 76, 'BORROWED'),
--(13,  '2025-06-09', '2025-06-11', 9, 77, 'RETURNED'),
--(14,  '2025-06-10', '2025-06-12', 9, 78, 'RETURNED'),
--(15,  '2025-06-11', '2025-06-13', 9, 79, 'RETURNED'),
--(6,  '2025-06-12', '2025-06-15', 9, 80, 'RETURNED'),
--
---- Member 10
--(7,  '2025-06-01', NULL,         10, 81, 'BORROWED'),
--(8,  '2025-06-02', NULL,         10, 82, 'BORROWED'),
--(9,  '2025-06-03', NULL,         10, 83, 'BORROWED'),
--(10, '2025-06-04', NULL,         10, 84, 'BORROWED'),
--(11, '2025-06-05', NULL,         10, 85, 'BORROWED'),
--(12, '2025-06-06', '2025-06-09', 10, 86, 'RETURNED'),
--(13, '2025-06-07', '2025-06-10', 10, 87, 'RETURNED'),
--(14, '2025-06-08', '2025-06-11', 10, 88, 'RETURNED'),
--(15, '2025-06-09', '2025-06-12', 10, 89, 'RETURNED'),
--(16, '2025-06-10', '2025-06-13', 10, 90, 'RETURNED'),
--
---- Member 11
--(17, '2025-06-01', NULL,         11, 91, 'BORROWED'),
--(18, '2025-06-02', NULL,         11, 92, 'BORROWED'),
--(19, '2025-06-03', NULL,         11, 93, 'BORROWED'),
--(20, '2025-06-04', NULL,         11, 94, 'BORROWED'),
--(21, '2025-06-05', NULL,         11, 95, 'BORROWED'),
--(22, '2025-06-06', '2025-06-09', 11, 96, 'RETURNED'),
--(23, '2025-06-07', '2025-06-10', 11, 97, 'RETURNED'),
--(24, '2025-06-08', '2025-06-11', 11, 98, 'RETURNED'),
--(25, '2025-06-09', '2025-06-12', 11, 99, 'RETURNED'),
--(11,  '2025-06-10', '2025-06-13', 11, 100, 'RETURNED');

