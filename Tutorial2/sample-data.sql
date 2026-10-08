-- Run against the jdbc/sample database after the first deploy (tables are created by JPA).
INSERT INTO tblCustomer (ID, EMAIL, PASSWORD, USERTYPE) VALUES (1, 'john@abc.co.za', 'pass123', 'customer');

INSERT INTO tblItem (ITEMID, NAME, ITEMTYPE, QTY, PRICE) VALUES (1, 'Office Chair', 'Furniture', 10, 1200.00);
INSERT INTO tblItem (ITEMID, NAME, ITEMTYPE, QTY, PRICE) VALUES (2, 'Desk Lamp', 'Electronics', 25, 350.00);
INSERT INTO tblItem (ITEMID, NAME, ITEMTYPE, QTY, PRICE) VALUES (3, 'Printer', 'Electronics', 5, 2500.00);
INSERT INTO tblItem (ITEMID, NAME, ITEMTYPE, QTY, PRICE) VALUES (4, 'Notebook Pack', 'Stationery', 100, 85.50);
