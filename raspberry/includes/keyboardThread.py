import RPi.GPIO as GPIO
from threading import Thread
import requests
import json
from .. import settings
import time
import sys
knopIn = 9
knopOpen = 22
knopOut = 10
ledIn = 27
ledOpen = 17
ledOut = 4

def start():
    GPIO.setmode(GPIO.BCM)
    GPIO.setwarnings(False)
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
    state = "add"
    statePrev = state
    lights = {
    "add": ledIn,
    "open":ledOpen,
    "del":ledOut
    }
    while True:
        time.sleep(0.1)
        if GPIO.input(knopIn) == False:
            print "knopjeIn"
            state = "add"
            time.sleep(0.2)

        if GPIO.input(knopOpen) == False:
            print "knopjeOpen"
            state = "open"

            time.sleep(0.2)

        if GPIO.input(knopOut) == False:
            print "knopjeOut"
            state = "del"

            time.sleep(0.2)

        if(state!=statePrev):
            GPIO.output(lights[statePrev], GPIO.LOW)
            GPIO.output(lights[state], GPIO.HIGH)
            statePrev = state

def barcodeThread():

    print "startBarcode"
    fp = open('/dev/hidraw0', 'rb') #This way the scanner file isn't reopened everytime and parts fall off.
    #stop = timeit.default_timer()

    #print "Opendevice:"+ str(stop - start)
    while True:
        
        
        data = scanCode(fp) #Hier moet barcode code in komen
        
        print data
        
        thread = Thread(target=request, args=(data, state))
        thread.start()
        print "done"

def request(code, astate):
    print code
    print astate
    try:
        url = settings.url + "itemChange"
        postVars = json.dumps({
        "UserId":settings.userId,
        "Barcode":code,
        "Action":astate
        })
        response = requests.post(url, data={"JSON":postVars})
        print response.text
        #print "responsejsjsjsdfhlkajsdflkjasdflkjasdfjlkasdfljk"

    except requests.exceptions.RequestException as e:
        print "ripppp"
        



def scanCode(fp):

    hid = { 4: 'a', 5: 'b', 6: 'c', 7: 'd', 8: 'e', 9: 'f', 10: 'g', 11: 'h', 12: 'i', 13: 'j', 14: 'k', 15: 'l', 16: 'm', 17: 'n', 18: 'o', 19: 'p', 20: 'q', 21: 'r', 22: 's', 23: 't', 24: 'u', 25: 'v', 26: 'w', 27: 'x', 28: 'y', 29: 'z', 30: '1', 31: '2', 32: '3', 33: '4', 34: '5', 35: '6', 36: '7', 37: '8', 38: '9', 39: '0', 44: ' ', 45: '-', 46: '=', 47: '[', 48: ']', 49: '\\', 51: ';' , 52: '\'', 53: '~', 54: ',', 55: '.', 56: '/'  }



    ss = ""
    shift = False

    done = False

    while not done:
       #time.sleep(1)
       ## Get the character from the HID
       buffer = fp.read(8)
       for c in buffer:
          if ord(c) > 0:

             ##  40 is carriage return which signifies
             ##  we are done looking for characters
             if int(ord(c)) == 40:
                done = True
                break;
             ss += hid[ int(ord(c)) ]


    return ss
    #from: https://www.raspberrypi.org/forums/viewtopic.php?f=45&t=55100
