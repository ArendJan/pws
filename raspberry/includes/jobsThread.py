import os
import settings
import sys
import pprint
import json
import sleep
from ... import settings
import jobs
from threading import Thread
def start():
    print "start jobs thread"

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

    except Interrupt:
        print "ripppp"
    return data


def readAndParse(json):
    for job in json["jobs"]:
        checkJob(job["id"])
        thread = Thread(target = jobs.parseJob, args = (json,)) #we'll do this, so there won't be a big delay when parsing everything.
        thread.start()



def jobsThread():
