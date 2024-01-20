#!/usr/bin/python3

import os
package = "mysql.connector"

try:
    __import__package 
except:
    os.system("pip install mysql-connector-python")

import mysql.connector

#Connecting to DB
conn = mysql.connector.connect(
    user='root',
    password='root',
    host='127.0.0.1',
    database='librarymanagementsystem'
)
cursor = conn.cursor()

query = "SELECT COUNT(*) FROM book;"
cursor.execute(query)

# Fetch the result of the query
result = cursor.fetchone()

# Get the count of records (if result is not None)
count = result[0] if result else 0
if count > 0:
    print(f"The table book has {count} records.")    
else:
    print("The table book is empty.")
    dirname = os.path.dirname(__file__)
    filename = os.path.join(dirname, 'books.csv')
    fileObj = open(filename, 'r', encoding="utf-8")
    text_file = list(fileObj)
    author_id = 0
    author_list = []
    book_author_list=[]
   
    for line in text_file[1:25001]:        
        line = line.strip()
        column_list = line.split('\t')
        
        isbn13 = column_list[1]
        title = column_list[2]
        authors = column_list[3]
        sql = f"INSERT INTO book (isbn, title) VALUES (%s, %s)" 
        record_to_insert = (isbn13, title)
        cursor.execute(sql, record_to_insert)

        authors = set(authors.split(','))
        for author in authors:
            
            if (author not in author_list):
                author_id += 1
                author_list += [author]       
                
                sql2 = f"INSERT INTO authors (author_id, name) VALUES (%s, %s)"
                record_to_insert2 = (str(author_id), author)
                cursor.execute(sql2, record_to_insert2)

            
            record_to_insert3 = (str(author_list.index(author)+1), isbn13)
            book_author_list.append(record_to_insert3)
            
    
    sql3 = f"INSERT INTO book_authors (author_id, isbn) VALUES (%s, %s)"
    for record in book_author_list:        
        cursor.execute(sql3, record)
    fileObj.close()
    filename2 = os.path.join(dirname, 'borrowers.csv')
    fileObj2 = open(filename2, 'r', encoding="utf-8")
    text_file2 = list(fileObj2)
    
    for line in text_file2[1:1001]:        
        line = line.strip()
        column_list = line.split(',')
        
        cardId = column_list[0]
        ssn = column_list[1]
        bname = column_list[2]+' '+column_list[3]
        address=column_list[5]+','+column_list[6]+','+column_list[7]
        phone=column_list[8]
         
        sql4 = f"INSERT INTO borrower (card_id, address, bname, phone, ssn) VALUES (%s, %s, %s, %s, %s)"
        record_to_insert4 = (cardId, address , bname, phone, ssn)
        cursor.execute(sql4, record_to_insert4)
        
    fileObj2.close()    
    conn.commit()
cursor.close()
conn.close()


    
