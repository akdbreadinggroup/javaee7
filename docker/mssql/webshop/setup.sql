USE rgdatabase;
GO

CREATE TABLE Product (ID INT, name nvarchar(30), prize FLOAT);
GO

CREATE TABLE ProductOrder (ID INT IDENTITY(1,1) PRIMARY KEY, itemId INT NOT NULL, buyer nvarchar(30) NOT NULL, amount INT NOT NULL);
GO
