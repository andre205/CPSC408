# BretBoard

This is a messageboard application made using flask in python. It communicates with a mysql database to store user posts and login credentials.

To run the application you will need to install the following python3 libraries

	pip install flask

    pip install mysql-connector

    pip install csv

    pip install sql

If any installations fail, try the following. Some libraries have python2 duplicates, and we want the python3 versions

	python3 -m pip install [library]

To host the server, from the FinalProject folder:

	export FLASK_APP=bretboard

	export FLASK_ENV=development

	flask run

To create and populate the database, copy and paste the contents of db.sql into a datagrip terminal.

Navigate to 127.0.0.1:5000

If you have any questions or problems feel free to slack me or email me at andre205@mail.chapman.edu
