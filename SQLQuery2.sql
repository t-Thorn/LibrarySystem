alter table Borrow add constraint ISBN_B foreign key(ISBN)  references Bookinfo(ISBN) 
alter table Subscribe add constraint ISBN_S foreign key(ISBN)  references Bookinfo(ISBN) 