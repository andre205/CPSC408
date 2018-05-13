import mysql.connector as sql


config = {
  'user': 'root',
  'password': 'pass',
  'host': '127.0.0.1',
  'database': 'FlaskDB',
  'raise_on_warnings': True,
}

cnx = sql.connect(**config)
cursor = cnx.cursor()

query = ("SELECT * FROM Users")

cursor.execute(query)

for (firstName) in cursor:
    print(firstName[0])
    print(firstName[1])
    print(firstName[2])

cursor.close()
cnx.close();
