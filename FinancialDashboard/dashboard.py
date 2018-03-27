import os
from flask import Flask,redirect

app = Flask(__name__)

@app.route("/")
@app.route("/dashboard")
def test():
    return "D A S H"

if __name__ == '__main__':
    app.run()
