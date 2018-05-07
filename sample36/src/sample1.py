'''
Created on May 6, 2018

@author: jimmy
'''

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

def main():
    
    threadList = ["Thread-1", "Thread-2", "Thread-3"]
    #threadList = ["Thread-1"]
    nameList = ["One", "Two", "Three", "Four", "Five"]
    queueLock = threading.Lock()
    workQueue = queue.Queue(10)
    threads = []
    threadID = 1
    
    # Create new threads
    for tName in threadList:
        thread = myThread(threadID, tName, workQueue, queueLock)
        thread.start()
        threads.append(thread)
        threadID += 1
    
    # Fill the queue
    queueLock.acquire()
    for word in nameList:
        workQueue.put(word)
    queueLock.release()
    
    queueLock.acquire()
    for i in threads:
        workQueue.put(None)
    queueLock.release()

    # Wait for all threads to complete
    for t in threads:
        t.join()
    print ("Exiting Main Thread after all threads joined main to end!")
    time.sleep(1);

main()
