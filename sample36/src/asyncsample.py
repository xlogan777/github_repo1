'''
Created on May 10, 2018

@author: jimmy
'''
import asyncio
from flask import Flask, request #import main Flask class and request object

app = Flask(__name__) #create the Flask app

async def say(what, when):
    await asyncio.sleep(when)
    print(what)

async def flaskrun():
    app.run()
    
async def doloop(myloop):
    myloop.run_until_complete(flaskrun())
#     myloop.close()
    
@app.route("/hello")
def hello():
    return "Helloworld"

def main():
    
    loop = asyncio.get_event_loop()
    
#     loop.create_task(say('first hello', 2))
#     loop.create_task(say('second hello', 1))
#     loop.run_forever()
#     loop.close()
    
#     loop = asyncio.get_event_loop()

    loop.run_until_complete(doloop(loop))
    loop.close()
#     app.run()

main();