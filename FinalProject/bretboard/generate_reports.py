from db import get_db
import mysql.connector as sql
import csv

def generate_user_report():

    config = {
      'user': 'root',
      'password': 'pass',
      'host': '127.0.0.1',
      'database': 'FlaskDB',
      'raise_on_warnings': True,
    }

    cnx = sql.connect(**config)
    cursor = cnx.cursor()

    query = ("SELECT userId,username,firstName,lastName,dateCreated FROM Users")

    cursor.execute(query)

    all_users = cursor.fetchall()

    out = [["User ID","Username","First Name","Last Name","Account Created"]]

    for u in all_users:
        iout = []
        for i in range(len(u)):
            iout.append(u[i])
        out.append(iout)

    with open("reports/user_report.csv", 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerows(out)

def generate_post_report():

    config = {
      'user': 'root',
      'password': 'pass',
      'host': '127.0.0.1',
      'database': 'FlaskDB',
      'raise_on_warnings': True,
    }

    cnx = sql.connect(**config)
    cursor = cnx.cursor()

    query = ("SELECT id,u.username,created,title,body FROM post p JOIN Users u ON p.author_id=u.userID")

    cursor.execute(query)

    all_posts = cursor.fetchall()

    out = [["Post ID","Username","Post Date","Title","Body"] ]

    for p in all_posts:
        iout = []
        for i in range(len(p)):
            iout.append(p[i])
        out.append(iout)

    with open("reports/post_report.csv", 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerows(out)

def generate_admin_report():

    config = {
      'user': 'root',
      'password': 'pass',
      'host': '127.0.0.1',
      'database': 'FlaskDB',
      'raise_on_warnings': True,
    }

    cnx = sql.connect(**config)
    cursor = cnx.cursor()

    query = ("SELECT u.username FROM Admins a JOIN Users u ON a.userID=u.userID")

    cursor.execute(query)

    all_posts = cursor.fetchall()

    out = [["Admin Username"] ]

    for p in all_posts:
        iout = []
        for i in range(len(p)):
            iout.append(p[i])
        out.append(iout)

    with open("reports/admin_report.csv", 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerows(out)


generate_user_report()
generate_post_report()
generate_admin_report()
