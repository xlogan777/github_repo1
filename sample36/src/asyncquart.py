'''
Created on May 10, 2018

@author: jimmy
'''
import asyncio
from quart import Quart
import quart

async def abar(a):
    print(a)

app = Quart(__name__)

async def quartrun(myloop):
    app.run(debug=False, loop=myloop)
    
async def doloop(myloop):
    myloop.run_until_complete(quartrun(myloop))

@app.route("/")
async def notify():
    await abar("abar")
    return "OK"

if __name__ == "__main__":

    # obtain the event loop from asyncio
    loop = asyncio.get_event_loop()
    
    # hook Goblin to the loop
    goblin_app = loop.run_until_complete(doloop(loop))
    
    host = "localhost"
    port = 5000
    
    # hook Quart to the loop
    quart_server = loop.run_until_complete(loop.create_server(
            lambda: quart.serving.Server(app, loop), host, port))
    
    # actually run the loop (and the program)
    try:
        loop.run_forever()
    except KeyboardInterrupt:  # pragma: no cover
        pass
    finally:
        quart_server.close()
        loop.run_until_complete(quart_server.wait_closed())
        loop.run_until_complete(loop.shutdown_asyncgens())
        loop.close()
    
#     loop = asyncio.get_event_loop()
#     loop.run_until_complete(doloop(loop))
#     loop.close()
    
    #app.run(debug=False)
    