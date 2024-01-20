# Library-Management-System
This is a database host application where users can Search, Issue, and Check-in books. The user can also adds new borrowers.


## DATABASE DETAILS: 
The SQL database used here is MySQL. 
The database consists of the following tables:
1. book: It consists of two columns “isbn” and “title”, here Isbn is the primary key. Both “Isbn” and “Title” are of the type varchar .
2. authors: It consists of two columns “author_id” and “name” , here “author_id” is the primary key.
Columns “author_id” aand “Name” are of the type varchar.
3. book_authors: It consists of two columns “author_id” and “isbn”, here both columns combined constitute to form a primary key. Here, “author_id” and “isbn” are of type varchar. 
“author_id” has a foreign key constraint and it refers to “author_id” of authors table.
“isbn” has a foreign key constraint and it refers to “isbn” of book table.
4. Book_Loans: It consists of six columns, “Loan_id” of the type date , “isbn” and “card_id” of the type varchar, and all three “Date_out”, “Due_date”, “Date_in” of the type Date. Here “loan_id” column is the primary key.
“isbn” and “card_id” have foreign key constraints and they refer to “isbn” of Book and “card_id” of Borrower respectively.
5. Borrower: It consists of five columns, “card_id”, “ssn”, “bname”, “address”, “Phone”, here “card_id” column is the primary key and “ssn” column has a unique constraint on it.
Here, all “card_id”, “bname”, “address”, “Phone” and “ssn”, are of the type varchar.
6. Fines: It consists of 3 columns “loan_id” of the type bigint, “fine_amt” of the type decimal, “paid”, here “loan_id” of the type bit is the primary key.
“loan_id” also has a foreign key constraint and it refers to “loan_id” of book_loans table.

# Development Frameworks and Languages used
The Library Management System application developed uses Angular Framework for Front End, Spring Boot for Backend and MySQL for database management. 
Also used Python for creation of database and parsing and pushing data into the tables created. 
The project uses java version "21.0.1", jdk version 21, Angular CLI version “13.1.2” and Node version “14.17.5”. 

##  Compiling, building and Running the project instructions:
1.	Extract the .zip file to a desired location. 
2.	Open the command terminal where the create_db_script and book_parser_new python scripts and the csv files are located.
3.	To create the database run `python create_db_script.py` in the command line.
4.	Navigate inside the librarymanagementsystem folder where pom.xml file is located and in command terminal run ` mvn spring-boot:run` to run the backend Spring Boot application and making the tables in the database.
5.	To parse the data from the csv files and push data into the tables, again navigate back to the location where the create_db_script and book_parser_new python scripts and the csv files were located and run the following command `python book_parser_new.py`.
6.	Now, navigate back to the librarymanagementsystemui folder. If node js isn’t installed run `npm install` and then run the following commands `ng build` and `ng serve` in the command terminal to run the frontend Angular application.



