#!/usr/bin/python3
#c:/0-Personal/UTD/Course work/DB Design/Proj1/books.csv
# from pathlib import Path

#Installing and importing packages
import os
package = "pandas"
package2 = "mysql.connector"
try:
    __import__package 
except:
    os.system("pip install "+ package)
try:
    __import__package2 
except:
    os.system("pip install mysql-connector-python")

import mysql.connector
import pandas as pd


#Connecting to DB
conn = mysql.connector.connect(
    user='root',
    password='root',
    # host='DESKTOP-6921US2',
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
    # fileObj = open('../books.csv', 'r', encoding="utf-8")
    text_file = list(fileObj)
    author_id = 0
    author_list = []
    book_author_list=[]
    # df_books = pd.DataFrame(columns=['isbn', 'title'])
    # df_authors = pd.DataFrame(columns=['authorId', 'name'])
    # df_Book_Authors = pd.DataFrame(columns=['authorId', 'isbn'])
    for line in text_file[1:25001]:
        # print('-' * 80)
        # print(line)
        line = line.strip()
        column_list = line.split('\t')
        # print(column_list)
        isbn13 = column_list[1]
        title = column_list[2]
        authors = column_list[3]
        # print("INSERT INTO Books VALUES (\"" + isbn13 + "\",\"" + title + "\");")
        # row1 = pd.Series([isbn13, title], index=df_books.columns)
        # df_books = df_books.append(row1,ignore_index=True) 
        
        sql = f"INSERT INTO book (isbn, title) VALUES (%s, %s)" 
        record_to_insert = (isbn13, title)
        cursor.execute(sql, record_to_insert)
        # cursor.execute('''
        #             INSERT INTO book (isbn, title)
        #             VALUES (?,?)
        #             ''',
        #             isbn13, 
        #             title
        #             )   
        # if()
        authors = set(authors.split(','))
        for author in authors:
            author_id += 1
            if (author not in author_list):
                # Deal with duplicate author
                # print(author + " already exists!!!!!!!!!!!!!")
                # print(author_list[author_id-2])
                # Lookup existing author_id and populate author_id variable

            # else:
                # Add author to list
                author_list += [author]
                # Be sure to look up existing author if applicable
                # print("INSERT INTO Authors VALUES (\"" + str(author_id) + "\",\"" + author + "\");")
                # row2 = pd.Series([author_id, author], index=df_authors.columns)
                # df_authors = df_authors.append(row2,ignore_index=True)  
                sql2 = f"INSERT INTO authors (author_id, name) VALUES (%s, %s)"
                record_to_insert2 = (author_id, author)
                cursor.execute(sql2, record_to_insert2)
                # cursor.execute('''
                #     INSERT INTO authors (author_id, name)
                #     VALUES (?,?)
                #     ''',
                #     author_id, 
                #     author
                #     )   

            # print("INSERT INTO Book_authors VALUES (\"" + str(author_list.index(author)) + "\",\"" + isbn13 + "\");")
            # row3 = pd.Series([author_list.index(author)+1, isbn13], index=df_Book_Authors.columns)
            # df_Book_Authors = df_Book_Authors.append(row3,ignore_index=True)     
            # sql3 = f"INSERT INTO book_authors (author_id, isbn) VALUES (%s, %s)"-
            record_to_insert3 = (author_list.index(author)+1, isbn13)
            book_author_list.append(record_to_insert3)
            # cursor.execute(sql3, record_to_insert3)-
            # cursor.execute('''
            #         INSERT INTO book_authors (author_id, isbn)
            #         VALUES (?,?)
            #         ''',
            #         author_list.index(author)+1, 
            #         isbn13
            #         )  
            # print(len(author_list))
        
    
    # print('=' * 80)
    # print(author_list)
    # print("df_books:")
    # print(df_books)
    # print("df_authors:")
    # print(df_authors)
    # print("df_Book_Authors:")
    # print(df_Book_Authors)
        
    fileObj.close()


    filename2 = os.path.join(dirname, 'borrowers.csv')
    fileObj2 = open(filename2, 'r', encoding="utf-8")
    text_file2 = list(fileObj2)
    # author_id = 0
    # author_list = []
    # df_borrowers = pd.DataFrame(columns=['cardId', 'ssn','bname','address','phone'])
    for line in text_file2[1:1001]:
        # print('-' * 80)
        # print(line)
        line = line.strip()
        column_list = line.split(',')
        # print(column_list)
        cardId = column_list[0]
        ssn = column_list[1]
        bname = column_list[2]+' '+column_list[3]
        address=column_list[5]+','+column_list[6]+','+column_list[7]
        phone=column_list[8]
        # print("INSERT INTO Books VALUES (\"" + isbn13 + "\",\"" + title + "\");")
        # row4 = pd.Series([cardId,ssn,bname,address,phone], index=df_borrowers.columns)
        # df_borrowers = df_borrowers.append(row4,ignore_index=True)     
        sql4 = f"INSERT INTO borrower (card_id, address, b_name, phone, ssn) VALUES (%s, %s, %s, %s, %s)"
        record_to_insert4 = (cardId, ssn, bname, address, phone)
        cursor.execute(sql4, record_to_insert4)
        # cursor.execute('''
        #         INSERT INTO borrower (card_id, address, b_name, phone, ssn )
        #         VALUES (?,?,?,?,?)
        #         ''',
        #         cardId,
        #         ssn,
        #         bname,
        #         address,
        #         phone
        #         )  

    # print("df_borrowers:")
    # print(df_borrowers)
    fileObj2.close()



    #Inserting records to book table 
    # query = "SELECT COUNT(*) FROM book;"
    # cursor.execute(query)

    # # Fetch the result of the query
    # result = cursor.fetchone()

    # # Get the count of records (if result is not None)
    # count = result[0] if result else 0
    # if count > 0:
    #     print(f"The table book has {count} records.")    
    # else:
    #     print("The table book is empty.")
        # df_books.to_sql('book', conn, if_exists='fail', index=False)

    #Inserting records to authors table 
    # query2 = "SELECT COUNT(*) FROM authors;"
    # cursor.execute(query2)

    # # Fetch the result of the query
    # result = cursor.fetchone()

    # # Get the count of records (if result is not None)
    # count = result[0] if result else 0
    # if count > 0:
    #     print(f"The table authors has {count} records.")
        
    # else:
    #     print("The table authors is empty.")
    #     #df_authors.to_sql('authors', conn, if_exists='fail', index=False)


    # #Inserting records to book_authors table 
    # query3 = "SELECT COUNT(*) FROM book_authors;"
    # cursor.execute(query3)

    # # Fetch the result of the query
    # result = cursor.fetchone()

    # # Get the count of records (if result is not None)
    # count = result[0] if result else 0
    # if count > 0:
    #     print(f"The table book_authors has {count} records.")    
    # else:
    #     print("The table book_authors is empty.")
    #     # df_Book_Authors.to_sql('book_authors', conn, if_exists='fail', index=False)

    # #Inserting records to borrower table 
    # query4 = "SELECT COUNT(*) FROM borrower;"
    # cursor.execute(query4)

    # # Fetch the result of the query
    # result = cursor.fetchone()

    # # Get the count of records (if result is not None)
    # count = result[0] if result else 0
    # if count > 0:
    #     print(f"The table borrower has {count} records.")    
    # else:
    #     print("The table borrower is empty.")
    #     df_borrowers.to_sql('borrower', conn, if_exists='fail', index=False)
    # print('Printing....1.....'+book_author_list[0])
    print('when starting inserting')
    sql3 = f"INSERT INTO book_authors (author_id, isbn) VALUES (%s, %s)"
    i=0
    for record in book_author_list:
        # print(record)
        cursor.execute(sql3, record)
        print(i)
        i=i+1
    print('Inserted all')
    conn.commit()
cursor.close()
conn.close()


    # df.to_sql('your_table_name', conn, if_exists='replace', index=False)