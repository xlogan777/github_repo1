'''
Created on May 6, 2018

@author: jimmy
'''
"use this url to hangle GET/POST requests"
"https://scotch.io/bar-talk/processing-incoming-request-data-in-flask"

from flask import Flask, request #import main Flask class and request object
import queue
import threading
import time

class myThread (threading.Thread):
    def __init__(self, threadID, name, q, lock):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.q = q
        self.lock = lock
    def run(self):
        print ("Starting " + self.name)
        process_data(self.name, self.q, self.lock)
        print ("Exiting " + self.name)

def process_data(threadName, queue, lock):
    
    while True:
        lock.acquire()
        if not queue.empty():
            data = queue.get()            
            lock.release()
            
            if(data is None):
                print ("%s ending now!" % (threadName))
                break
            else:
                print ("%s processing %s" % (threadName, data))
                time.sleep(1);
        else:
            lock.release()
            time.sleep(1)

app = Flask(__name__) #create the Flask app

@app.route("/query-example")
def query_example():
    language = request.args.get("language") #if key doesn't exist, returns None
    framework = request.args["framework"] #if key doesn't exist, returns a 400, bad request error
    website = request.args.get("website")

    return '''<h1>The language value is: {}</h1>
              <h1>The framework value is: {}</h1>
              <h1>The website value is: {}'''.format(language, framework, website)

@app.route("/killthreads")
def form_example():
    queueLock.acquire()
    for i in threads:
        workQueue.put(None)
    queueLock.release()
    
    return "KILLED THREADS!"

@app.route('/json-example', methods=['POST']) #GET requests will be blocked
def json_example():
    req_data = request.get_json()    
    queueLock.acquire()
    workQueue.put(req_data)
    queueLock.release()

    return "PROCESSED MESSAGE!"

if __name__ == "__main__":
    #app.run(debug=True, port=5000)

    threadList = ["Thread-1", "Thread-2", "Thread-3"]
    #threadList = ["Thread-1"]
    queueLock = threading.Lock()
    workQueue = queue.Queue(1000)
    threads = []
    threadID = 1
    
    # Create new threads
    for tName in threadList:
        thread = myThread(threadID, tName, workQueue, queueLock)
        thread.start()
        threads.append(thread)
        threadID += 1

    app.run()

    # Wait for all threads to complete
    for t in threads:
        t.join()
    print ("Exiting Main Thread after all threads joined main to end!")
    time.sleep(1)
