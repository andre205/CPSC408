import click
from flask import current_app, g
from flask.cli import with_appcontext

import mysql.connector as sql


def get_db():
    if 'db' not in g:

        config = {
          'user': 'root',
          'password': 'p',
          'host': '127.0.0.1',
          'database': 'bretboard',
          'raise_on_warnings': True,
        }

        g.db = sql.connect(**config)
        g.db.cursor = g.db.cursor()

    return g.db


def close_db(e=None):

    db = g.pop('db', None)

    if db is not None:
        db.cursor.close()
        db.close()


# def init_db():
#     db = get_db()
#
#     with current_app.open_resource('schema.sql') as f:
#         db.cursor.execute(f.read().decode('utf8'))


@click.command('init-db')
@with_appcontext
def init_db_command():
    # Clear existing data and create new tables.
    init_db()
    click.echo('Initialized the database.')


def init_app(app):
    app.teardown_appcontext(close_db)
    app.cli.add_command(init_db_command)
