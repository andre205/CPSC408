from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for, send_file
)
from werkzeug.exceptions import abort

from bretboard.auth import login_required
from bretboard.db import get_db

from generate_reports import generate_all_reports

bp = Blueprint('messageboard', __name__)

@bp.route('/')
def index():

    db = get_db()
    db.cursor.execute(
        'SELECT p.postid, title, body, created, authorid, username'
        ' FROM posts p JOIN users u ON p.authorid = u.userid'
        ' ORDER BY created DESC'
    )
    p = db.cursor.fetchall()

    return render_template('messageboard/index.html', posts=p)


def get_post(id, check_author=True):

    get_db().cursor.execute(
        'SELECT p.postid, title, body, created, authorid, username'
        ' FROM posts p JOIN users u ON p.authorid = u.userid'
        ' WHERE p.postid = %s',
        (id,)
    )
    post = get_db().cursor.fetchone()

    if post is None:
        abort(404, "Post id {0} doesn't exist.".format(id))

    if check_author and post[4] != g.userid:
        abort(403)

    return post


@bp.route('/create', methods=('GET', 'POST'))
@login_required
def create():
    if request.method == 'POST':
        title = request.form['title']
        body = request.form['body']
        error = None

        if not title:
            error = 'Title is required.'

        if error is not None:
            flash(error)
        else:
            db = get_db()
            db.cursor.execute(
                'INSERT INTO posts (title, body, authorid)'
                ' VALUES (%s, %s, %s)',
                (title, body, g.userid)
            )
            db.commit()
            return redirect(url_for('messageboard.index'))

    return render_template('messageboard/create.html')


@bp.route('/search')
@bp.route('/search/<searchstring>', methods=('GET', 'POST'))
@login_required
def search(searchstring=""):

    s = searchstring
    if not s:
        s=""

    db = get_db()
    db.cursor.execute(
        'SELECT p.postid, title, body, created, authorid, username'
        ' FROM posts p JOIN users u ON p.authorid = u.userid'
        ' WHERE body LIKE %s'
        ' OR title LIKE %s'
        ' ORDER BY created DESC',
        ('%' + s + '%','%' + s + '%',)
    )
    p = db.cursor.fetchall()

    return render_template('messageboard/search.html', posts=p)


@bp.route('/<int:id>/update', methods=('GET', 'POST'))
@login_required
def update(id):

    post = get_post(id)

    if request.method == 'POST':
        title = request.form['title']
        body = request.form['body']
        error = None

        if not title:
            error = 'Title is required.'

        if error is not None:
            flash(error)
        else:
            db = get_db()
            db.cursor.execute(
                'UPDATE posts SET title = %s, body = %s WHERE postid = %s',
                (title, body, id)
            )
            db.commit()
            return redirect(url_for('messageboard.index'))

    return render_template('messageboard/update.html', post=post)


@bp.route('/reporting', methods=('GET','POST'))
@login_required
def reporting():

    return render_template('messageboard/reporting.html')


@bp.route('/<int:id>/delete', methods=('POST',))
@login_required
def delete(id):

    get_post(id)
    db = get_db()
    db.cursor.execute('DELETE FROM posts WHERE postid = %s', (id,))
    db.commit()
    return redirect(url_for('messageboard.index'))


@bp.route('/generate', methods=('GET','POST'))
@login_required
def generate_all():
    generate_all_reports()
    return render_template('messageboard/reporting.html')


@bp.route('/getPostReport')
@login_required
def post_report():
    return send_file('reports/post_report.csv',
                         mimetype='text/csv',
                         attachment_filename='post_report.csv',
                         as_attachment=True)

@bp.route('/getUserReport')
@login_required
def user_report():
    return send_file('reports/user_report.csv',
                         mimetype='text/csv',
                         attachment_filename='user_report.csv',
                         as_attachment=True)

@bp.route('/getAdminReport')
@login_required
def admin_report():
    return send_file('reports/admin_report.csv',
                         mimetype='text/csv',
                         attachment_filename='admin_report.csv',
                         as_attachment=True)

@bp.route('/getStatReport')
@login_required
def stat_report():
    return send_file('reports/stat_report.csv',
                         mimetype='text/csv',
                         attachment_filename='stat_report.csv',
                         as_attachment=True)
