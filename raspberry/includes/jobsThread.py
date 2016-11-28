import os
import sys
import pprint
import json
import requests
import time
from .. import settings
import jobs
from threading import Thread

def start():
    print "start jobs thread"
    thread = Thread(target=jobThread, args=())
    thread.start()

def jobThread():
    while 1:
        try:
            data = request()
            jsonX = decode(data)
            readAndParse(jsonX)
            
        except Exception:
            print "oops, wifi has some dezease"
        time.sleep(settings.interval)

def decode(data):

    return json.loads(data)

def request():
    try:
        url = settings.url + "getJobs"
        postVars = json.dumps({
        "UserId":settings.userId
        })
        response = requests.post(url, data={"JSON":postVars})
        #print response.text
        return response.text

    except Exception:
        print "ripppp"
    return "[]"


def readAndParse(jsonX):
    try:
        amount = len(jsonX) #TODO: error checking.
        for x in range(0,amount):
            job = jsonX[x]
            checkJob(job["JobId"])
            thread = Thread(target = jobs.parseJob, args = (job,)) #we'll do this, so there won't be a big delay when parsing everything.
            thread.start()
    except Exception:
        print "readandparse error"

def checkJob(jobId):
    try:
        url = settings.url + "markJob"
        postVars = json.dumps({ "UserId": settings.userId, "JobId":jobId})
        requests.post(url, data={"JSON":postVars}, timeout=20)
    except Exception:
        print "oeps"
    #maybe some sort of error checking...........
