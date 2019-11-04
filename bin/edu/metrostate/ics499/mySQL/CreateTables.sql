#Creates all tables used in the RMS.
#All primary keys auto increment
create table Users(
UserId int auto_increment,	
Position varchar(20),				
FirstName varchar(30),
LastName varchar(30), 		
Password varchar(255),	
Contact varchar(10),	
Primary key(UserId));
insert into Users(UserID,Position,FirstName,LastName,Password,Contact)
 values(0001,"manager","admin",null,"admin",null);
ALTER TABLE Users AUTO_INCREMENT = 1000;

create table Schedules(
ScheduleId int auto_increment,
UserID int,
Date Date,
StartTime time,			
EndTime time,			
Primary key(ScheduleId),
Foreign key(UserID) References Users(UserID)); 
ALTER TABLE Schedules AUTO_INCREMENT = 1;

create table MenuItems(
MenuItem int auto_increment,
ItemName Varchar(255),
ItemDesc text,
primary key(MenuItem));
ALTER TABLE MenuItems AUTO_INCREMENT = 1;

Create table Tables(
TableID int auto_increment,
Primary key(TableID));
ALTER TABLE Tables AUTO_INCREMENT = 1;

create table Orders(
OrderID int auto_increment,
MenuItem int,
TableID int,
SpecialRequest varchar(200),
primary key(OrderID),
foreign key(TableID) References Tables(TableId),
foreign key(MenuItem) References MenuItems(MenuItem));
ALTER TABLE Orders AUTO_INCREMENT = 1;