import os
import sys
import pprint
import json

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
    return json.load(data)

def request():
    try:
        url = settings.url + "getJobs"
        postVars = json.dump({
        "userId":settings.userId
        });
        response = requests.post(url, data={"JSON":postVars})
        return json.loads(response.text)

    except Exception:
        print "ripppp"
    return "asdf"


def readAndParse(json):
    for job in json["jobs"]:
        checkJob(job["id"])
        thread = Thread(target = jobs.parseJob, args = (json,)) #we'll do this, so there won't be a big delay when parsing everything.
        thread.start()
