from flask import Flask, redirect, url_for, request
app = Flask(__name__)

@app.route('/dashboard/<name>')
def dashboard(name):
   return 'welcome %s' % name

@app.route('/login',methods = ['POST', 'GET'])
def login():
   if request.method == 'POST':
      user = request.form['u']
      password = request.form['p']
      if(password == "p"):
          return redirect(url_for('dashboard',name = user))
      else:
          return redirect(url_for('login'))
   else:
      user = request.args.get('u')
      password = request.args.get['p']
      return redirect(url_for('success',name = user))

if __name__ == '__main__':
   app.run(debug = True)
