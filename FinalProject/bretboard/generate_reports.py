from db import get_db
import mysql.connector as sql
import csv

config = {
  'user': 'root',
  'password': 'p',
  'host': '127.0.0.1',
  'database': 'bretboard',
  'raise_on_warnings': True,
}

def generate_user_report():

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

    with open("bretboard/reports/user_report.csv", 'w+') as f:
        writer = csv.writer(f)
        writer.writerows(out)

def generate_post_report():

    cnx = sql.connect(**config)
    cursor = cnx.cursor()

    query = ("SELECT postid,u.username,created,title,body FROM posts p JOIN Users u ON p.authorid=u.userID")

    cursor.execute(query)

    all_posts = cursor.fetchall()

    out = [["Post ID","Username","Post Date","Title","Body"] ]

    for p in all_posts:
        iout = []
        for i in range(len(p)):
            iout.append(p[i])
        out.append(iout)

    with open("bretboard/reports/post_report.csv", 'w+') as f:
        writer = csv.writer(f)
        writer.writerows(out)

def generate_admin_report():

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

    with open("bretboard/reports/admin_report.csv", 'w+') as f:
        writer = csv.writer(f)
        writer.writerows(out)

#CHANGE THIS
def generate_stat_report():

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

    with open("bretboard/reports/stat_report.csv", 'w+') as f:
        writer = csv.writer(f)
        writer.writerows(out)


def generate_all_reports():
    generate_user_report()
    generate_post_report()
    generate_admin_report()
    generate_stat_report()
