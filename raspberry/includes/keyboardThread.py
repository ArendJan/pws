import RPi.GPIO as GPIO
from threading import Thread
import requests

from .. import settings
import time
knopIn = 22
knopOpen = 10
knopOut = 9
ledIn = 4
ledOpen = 17
ledOut = 27

def start():
    GPIO.setmode(GPIO.BCM)
    print "start van keyboardthreads"


    GPIO.setup(knopIn, GPIO.IN, pull_up_down=GPIO.PUD_UP)
    GPIO.setup(knopOpen, GPIO.IN, pull_up_down=GPIO.PUD_UP)
    GPIO.setup(knopOut, GPIO.IN, pull_up_down=GPIO.PUD_UP)
    GPIO.setup(ledIn, GPIO.OUT)
    GPIO.setup(ledOpen, GPIO.OUT)
    GPIO.setup(ledOut, GPIO.OUT)
    GPIO.output(ledIn, GPIO.LOW)
    GPIO.output(ledOpen, GPIO.LOW)
    GPIO.output(ledOut, GPIO.LOW)
    thread = Thread(target=buttonThread, args=())
    thread.start()
    thread = Thread(target=barcodeThread, args=())
    thread.start()

def buttonThread():
    global state
    state = ""
    state = "IN"
    statePrev = state
    lights = {
    "IN": ledIn,
    "OPEN":ledOpen,
    "OUT":ledOut
    }
    while True:
        if GPIO.input(knopIn) == False:
            print "knopjeIn"
            state = "IN"
            time.sleep(0.2)

        if GPIO.input(knopOpen) == False:
            print "knopjeOpen"
            state = "OPEN"

            time.sleep(0.2)

        if GPIO.input(knopOut) == False:
            print "knopjeOut"
            state = "OUT"

            time.sleep(0.2)

        if(state!=statePrev):
            GPIO.output(lights[statePrev], GPIO.LOW)
            GPIO.output(lights[state], GPIO.HIGH)
            statePrev = state

def barcodeThread():
    while True:
        data = raw_input() #Hier moet barcode code in komen
        if(False):
            request(data) #check of het een barcode is


def request(code):
    try:
        url = settings.url + "defaultOutput/itemChange"
        postVars = json.dumps({
        "UserId":settings.userId,
        "Barcode":code,
        "Action":add
        })
        response = requests.post(url, data={"JSON":postVars})
        print response.text

    except Exception:
        print "ripppp"
