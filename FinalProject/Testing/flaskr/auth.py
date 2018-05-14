import functools

from flask import (
    Blueprint, flash, g, redirect, render_template, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash

from flaskr.db import get_db

bp = Blueprint('auth', __name__, url_prefix='/auth')

def login_required(view):
    """View decorator that redirects anonymous users to the login page."""
    @functools.wraps(view)
    def wrapped_view(**kwargs):
        if g.userid is None:
            return redirect(url_for('auth.login'))

        return view(**kwargs)

    return wrapped_view


@bp.before_app_request
def load_logged_in_user():
    """If a user id is stored in the session, load the user object from
    the database into ``g.user``."""
    user_id = session.get('user_id')

    if user_id is None:
        g.userid = None
    else:
        get_db().cursor.execute(
            'SELECT * from Users WHERE userID = %s', (user_id,)
        )
        u = get_db().cursor.fetchone()
        if(u):
            g.userid = u[0]
            g.username = u[1]
            g.firstname = u[3]
            g.filter = ""

        get_db().cursor.execute(
            'SELECT * from admins WHERE userID = %s', (user_id,)
        )
        a = get_db().cursor.fetchone()
        if(a):
            g.userisadmin = True



@bp.route('/register', methods=('GET', 'POST'))
def register():
    """Register a new user.

    Validates that the username is not already taken. Hashes the
    password for security.
    """
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        firstName = request.form['firstName']
        lastName = request.form['lastName']
        db = get_db()
        error = None

        if not username:
            error = 'Username is required.'
        elif not password:
            error = 'Password is required.'
        db.cursor.execute(
            'SELECT userID from Users WHERE username = %s', (username,)
        )
        if db.cursor.fetchone() is not None:
            error = 'User {0} is already registered.'.format(username)

        if error is None:
            # the name is available, store it in the database and go to
            # the login page
            db.cursor.execute(
                'INSERT INTO Users (username, pass, firstName, lastName) VALUES (%s, %s, %s, %s)',
                (username, password, firstName, lastName)
            )
            db.commit()
            return redirect(url_for('auth.login'))

        flash(error)

    return render_template('auth/register.html')


@bp.route('/login', methods=('GET', 'POST'))
def login():
    """Log in a registered user by adding the user id to the session."""
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        db = get_db()
        error = None
        db.cursor.execute(
            'SELECT * from Users WHERE username = %s', (username,)
        )
        user = db.cursor.fetchone()

        if user is None:
            error = 'User does not exist.'
        elif user[2] != password:
            error = 'Incorrect password.'

        if error is None:
            # store the user id in a new session and return to the index
            session.clear()
            session['user_id'] = user[0]
            return redirect(url_for('index'))

        flash(error)

    return render_template('auth/login.html')


@bp.route('/logout')
def logout():
    """Clear the current session, including the stored user id."""
    session.clear()
    return redirect(url_for('index'))
