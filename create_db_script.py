import os
package = "mysql.connector"

try:
    __import__package 
except:
    os.system("pip install mysql-connector-python")

import mysql.connector

connection = mysql.connector.connect(
    host="127.0.0.1",
    user="root",
    password="root",
)

target_database = "librarymanagementsystem"

cursor = connection.cursor()
cursor.execute("SHOW DATABASES")

databases = [database[0] for database in cursor]

if target_database not in databases:
    # Database doesn't exist; create it
    cursor.execute(f"CREATE DATABASE {target_database}")
    print(f"Database '{target_database}' created.")
else:
    print(f"Database '{target_database}' already exists.")

cursor.close()

