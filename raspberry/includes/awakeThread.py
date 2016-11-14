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
    print "start awake thread"
    thread = Thread(target=awakeThread, args=())
    thread.start()

def awakeThread():
    while 1:
        try:
            request()
            time.sleep(settings.interval)
        except Exception:
            print "oops, wifi has some dezease"

def request():
    try:
        url = settings.url + "sendAwake"
        postVars = json.dumps({
        "UserId":settings.userId
        })
        response = requests.post(url, data={"JSON":postVars})


    except Exception:
        print "ripppp"
    return "[]"
