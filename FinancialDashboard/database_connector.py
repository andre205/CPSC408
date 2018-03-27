import mysql.connector as sql


config = {
  'user': 'root',
  'password': 'p',
  'host': '127.0.0.1',
  'database': 'StudentDB',
  'raise_on_warnings': True,
}

cnx = sql.connect(**config)
cursor = cnx.cursor()

query = ("SELECT FirstName FROM Student")

cursor.execute(query)


for (FirstName) in cursor:
    print(FirstName[0])


cursor.close()
cnx.close();
