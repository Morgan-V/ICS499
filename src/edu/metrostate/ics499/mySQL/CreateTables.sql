
create table Users(
UserId int auto_increment,					# handles incrementing to the next id
Position varchar(20),				
FirstName varchar(30),
LastName varchar(30), 		
Password varchar(255),	
Contact varchar(10),	
Primary key(UserId));
ALTER TABLE Users AUTO_INCREMENT = 1000;	# starts IDs at 1000


create table Schedules(
ScheduleId int,
UserID int,
Date Date,
StartTime time,			
EndTime time,			
Primary key(ScheduleId),
Foreign key(UserID) References Users(UserID)); 

create table MenuItems(
MenuItem int,
ItemName Varchar(255),
ItemDesc text,
primary key(MenuItem));

Create table Tables(		#TODO	Add table requirements
TableID int,
Primary key(TableID));

create table Orders(
OrderID int,
MenuItem int,
TableID int,
primary key(OrderID),
foreign key(TableID) References Tables(TableId),
foreign key(MenuItem) References MenuItems(MenuItem));

