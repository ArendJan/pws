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
        data = request()
        jsonX = decode(data)
        readAndParse(jsonX)
        time.sleep(settings.interval)

def decode(data):
    return json.loads(data)

def request():
    try:
        url = settings.url + "defaultOutput/getJobs"
        postVars = json.dumps({
        "UserId":settings.userId
        })
        response = requests.post(url, data={"JSON":postVars})
        return response.text

    except Exception:
        print "ripppp"
    return "asdf"


def readAndParse(jsonX):
    amount = len(jsonX["Jobs"])
    for x in range(0,amount):
        job = jsonX["Jobs"][x]
        checkJob(job["JobId"])
        thread = Thread(target = jobs.parseJob, args = (job,)) #we'll do this, so there won't be a big delay when parsing everything.
        thread.start()


def checkJob(jobId):
    url = settings.url + "markJobs"
    postVars = json.dumps({ "UserId": settings.userId, "JobId":jobId})
    requests.post(url, data={"JSON":postVars})
    #maybe some sort of error checking...........
