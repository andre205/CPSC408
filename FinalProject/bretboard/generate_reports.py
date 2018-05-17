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

#saves user report csv to server to be downloaded
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

#saves post report csv to server to be downloaded
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

#saves admin report csv to server to be downloaded
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

#saves stat report csv to server to be downloaded
def generate_stat_report():

    out = []

    cnx = sql.connect(**config)
    cursor = cnx.cursor()

    query = ("SELECT COUNT(DISTINCT userid) from users")
    cursor.execute(query)
    usercount = cursor.fetchone()

    iout = ['Total users',usercount[0]]
    out.append(iout)

    query = ("SELECT COUNT(DISTINCT userid) from admins")
    cursor.execute(query)
    admincount = cursor.fetchone()

    iout = ['Total admins',admincount[0]]
    out.append(iout)

    query = ("SELECT COUNT(DISTINCT postid) from posts")
    cursor.execute(query)
    postcount = cursor.fetchone()

    iout = ['Total posts',postcount[0]]
    out.append(iout)

    out.append("\n")

    query = ('SELECT username, numposts FROM ('
      ' SELECT username, COUNT(DISTINCT postid) as numposts from all_data'
      ' GROUP BY username'
      ' ) sub'
      ' ORDER BY numposts DESC LIMIT 1')
    cursor.execute(query)
    mostactive = cursor.fetchone()
    iout = ['Most active user',mostactive[0]]
    out.append(iout)

    out.append("\n")

    iout=['Posts by user']
    out.append(iout)

    query = ("SELECT username, COUNT(DISTINCT postid) as 'numposts' from all_data GROUP BY username;")
    cursor.execute(query)
    all_user_posts = cursor.fetchall()

    for p in all_user_posts:
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
