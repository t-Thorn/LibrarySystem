create table Operator(
 OID INT identity(1,1),
 username varchar(20) primary key,
 password varchar(20),
 gender bit default 0,
 age int ,
 tel varchar(30) ,
)
create TABLE Subscribe(
   ISBN int primary key,
   orderTime date default GETDATE(),
   number int check (number>0),
   Operator varchar(20) references operator(username),
   discount float check(discount>0 and discount<1)
 )
create table Bookinfo(
  ISBN int primary key,
  FOREIGN KEY(ISBN) references Subscribe(ISBN),
  bookName varchar(20) not null,
  typeName varchar(20) not null,
  writer varchar(20) not null,
  translator varchar(20) default 'нч',
  publisher varchar(20) not null,
  publishDate date ,
  price float default 0
)

create table Borrow(
  BID  int identity(1,1),
  ISBN int primary key,
  FOREIGN KEY (ISBN) REFERENCES Bookinfo(ISBN),
  readUsername varchar(20),
  valid bit default 0,
  borrowDate date default GETDATE(),
  backDate date
)
create table Reader(
  UID int,
 username varchar(20) primary key,
 password varchar(20),
 name varchar(20),
 gender bit default 0,
 age int ,
 tel varchar(30) ,
 ID varchar(30),
 birthday date ,
 )
