CREATE DATABASE addressbook;
GO
USE addressbook;
GO

CREATE TABLE Contact (ID INT, firstName nvarchar(30), lastName nvarchar(30), email nvarchar(30), mobilePhone nvarchar(20), homePhone nvarchar(20), birthday DATE);
GO

CREATE TABLE Product (ID INT, name nvarchar(30), prize FLOAT);
GO

CREATE TABLE ProductOrder (ID INT PRIMARY KEY, itemId INT NOT NULL, buyer nvarchar(30) NOT NULL, amount INT NOT NULL);
GO