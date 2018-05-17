import functools

from flask import (
    Blueprint, flash, g, redirect, render_template, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash

from bretboard.db import get_db

bp = Blueprint('auth', __name__, url_prefix='/auth')

#for functions that require user to be logged in
def login_required(view):

    @functools.wraps(view)
    def wrapped_view(**kwargs):
        if g.userid is None:
            return redirect(url_for('auth.login'))

        return view(**kwargs)

    return wrapped_view

#retrieve session variables
@bp.before_app_request
def load_logged_in_user():

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

        #after registering, redirect to login page
        if error is None:
            db.cursor.execute(
                'INSERT INTO Users (username, pass, firstName, lastName) VALUES (%s, %s, %s, %s)',
                (username, generate_password_hash(password), firstName, lastName)
            )
            db.commit()
            return redirect(url_for('auth.login'))

        flash(error)

    return render_template('auth/register.html')


@bp.route('/login', methods=('GET', 'POST'))
def login():

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
        elif not check_password_hash(user[2], password):
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

    session.clear()
    return redirect(url_for('index'))
