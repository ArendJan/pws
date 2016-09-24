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
        json = decode(data)
        readAndParse(json)
        time.sleep(settings.interval)

def decode(data):
    return json.loads(data)

def request():
    try:
        url = settings.url + "defaultOutput/getJobs"
        print url
        postVars = json.dumps({
        "userId":settings.userId
        });
        response = requests.post(url, data={"JSON":postVars})
        return response.text

    except Exception:
        print "ripppp"
    return "asdf"


def readAndParse(json):
    for job in json["Jobs"][]:
        checkJob(job["JobId"])
        thread = Thread(target = jobs.parseJob, args = (json,)) #we'll do this, so there won't be a big delay when parsing everything.
        thread.start()


def checkJob(id):
    print "ellezxdf"
