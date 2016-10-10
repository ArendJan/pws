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
    print "startBarcode"
    while True:
        print "lelellelelellelel"
        data = scanCode() #Hier moet barcode code in komen
        print "lel"
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

    except requests.exceptions.RequestException as e:
        print "ripppp"
        print # coding=utf-8



def scanCode():

    hid = { 4: 'a', 5: 'b', 6: 'c', 7: 'd', 8: 'e', 9: 'f', 10: 'g', 11: 'h', 12: 'i', 13: 'j', 14: 'k', 15: 'l', 16: 'm', 17: 'n', 18: 'o', 19: 'p', 20: 'q', 21: 'r', 22: 's', 23: 't', 24: 'u', 25: 'v', 26: 'w', 27: 'x', 28: 'y', 29: 'z', 30: '1', 31: '2', 32: '3', 33: '4', 34: '5', 35: '6', 36: '7', 37: '8', 38: '9', 39: '0', 44: ' ', 45: '-', 46: '=', 47: '[', 48: ']', 49: '\\', 51: ';' , 52: '\'', 53: '~', 54: ',', 55: '.', 56: '/'  }

    hid2 = { 4: 'A', 5: 'B', 6: 'C', 7: 'D', 8: 'E', 9: 'F', 10: 'G', 11: 'H', 12: 'I', 13: 'J', 14: 'K', 15: 'L', 16: 'M', 17: 'N', 18: 'O', 19: 'P', 20: 'Q', 21: 'R', 22: 'S', 23: 'T', 24: 'U', 25: 'V', 26: 'W', 27: 'X', 28: 'Y', 29: 'Z', 30: '!', 31: '@', 32: '#', 33: '$', 34: '%', 35: '^', 36: '&', 37: '*', 38: '(', 39: ')', 44: ' ', 45: '_', 46: '+', 47: '{', 48: '}', 49: '|', 51: ':' , 52: '"', 53: '~', 54: '<', 55: '>', 56: '?'  }

    fp = open('/dev/hidraw0', 'rb')


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

             ##  If we are shifted then we have to
             ##  use the hid2 characters.
             if shift:

                ## If it is a '2' then it is the shift key
                if int(ord(c)) == 2 :
                   shift = True

                ## if not a 2 then lookup the mapping
                else:
                   ss += hid2[ int(ord(c)) ]
                   shift = False

             ##  If we are not shifted then use
             ##  the hid characters

             else:

                ## If it is a '2' then it is the shift key
                if int(ord(c)) == 2 :
                   shift = True

                ## if not a 2 then lookup the mapping
                else:
                   ss += hid[ int(ord(c)) ]

    return ss
    #from: https://www.raspberrypi.org/forums/viewtopic.php?f=45&t=55100
