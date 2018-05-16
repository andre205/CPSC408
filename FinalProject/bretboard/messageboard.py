from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for
)
from werkzeug.exceptions import abort

from bretboard.auth import login_required
from bretboard.db import get_db

bp = Blueprint('messageboard', __name__)

@bp.route('/')
def index():
    """Show all the posts, most recent first."""
    db = get_db()
    db.cursor.execute(
        'SELECT p.id, title, body, created, author_id, username'
        ' FROM post p JOIN users u ON p.author_id = u.userid'
        ' ORDER BY created DESC',
    )
    p = db.cursor.fetchall()

    return render_template('messageboard/index.html', posts=p)


def get_post(id, check_author=True):
    # Checks that the id exists and optionally that the current user is
    # the author.
    #
    # returns the post with author information
    # :raise 404: if a post with the given id doesn't exist
    # :raise 403: if the current user isn't the author

    get_db().cursor.execute(
        'SELECT p.id, title, body, created, author_id, username'
        ' FROM post p JOIN users u ON p.author_id = u.userid'
        ' WHERE p.id = %s',
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
                'INSERT INTO post (title, body, author_id)'
                ' VALUES (%s, %s, %s)',
                (title, body, g.userid)
            )
            db.commit()
            return redirect(url_for('messageboard.index'))

    return render_template('messageboard/create.html')

@bp.route('/search', methods=('GET', 'POST'))
@login_required
def search():
    if request.method == 'POST':
        searchstring = request.form['searchstring']

        db.cursor.execute(
            'SELECT p.id, title, body, created, author_id, username'
            ' FROM post p JOIN users u ON p.author_id = u.userid'
            # ' WHERE body LIKE %%  %s %% '
            ' ORDER BY created DESC' , (searchstring,)
        )
        p = db.cursor.fetchall()
        print(p[0])
        return render_template('messageboard/search.html', posts=p)

    return render_template('messageboard/search.html')


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
                'UPDATE post SET title = %s, body = %s WHERE id = %s',
                (title, body, id)
            )
            db.commit()
            return redirect(url_for('messageboard.index'))

    return render_template('messageboard/update.html', post=post)

# FAVORITING - NEEDS WORK
@bp.route('/', methods=('GET','POST'))
@login_required
def favorite(id):
    """Favorite a post"""
    post = get_post(id)

    db = get_db()
    db.cursor.execute(
        'INSERT INTO favorites (userId, postID) VALUES (%s, %s)',
        (g.userid, id)
    )
    db.commit()
    return redirect(url_for('messageboard.index'))

@bp.route('/reporting', methods=('GET','POST'))
@login_required
def reporting():

    return render_template('messageboard/reporting.html')




@bp.route('/<int:id>/delete', methods=('POST',))
@login_required
def delete(id):
he post.
    get_post(id)
    db = get_db()
    db.cursor.execute('DELETE FROM post WHERE id = %s', (id,))
    db.commit()
    return redirect(url_for('messageboard.index'))
