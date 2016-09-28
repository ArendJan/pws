import RPi.GPIO as GPIO
from threading import Thread
import time

def start():
    GPIO.setmode(GPIO.BCM)
    print "start van keyboardthreads"
    knopIn = 4
    knopOpen = 27
    knopOut = 28
    ledIn = 18
    ledOpen = 23
    ledOut = 24

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
    state = "nothing"
    statePrev = state
    lights = {
    "nothing": 18,
    "IN": 23,
    "OPEN":24,
    "OUT":25
    }
    while True:
        if GPIO.input(4) == False:
            print "knopje"
            state = "IN"
            time.sleep(0.2)

        if GPIO.input(27) == False:
            print "knopje"
            state = "OPEN"

            time.sleep(0.2)

        if GPIO.input(22) == False:
            print "knopje"
            state = "OUT"

            time.sleep(0.2)
        if(state!=statePrev):
            GPIO.output(lights[statePrev], GPIO.LOW)
            GPIO.output(lights[state], GPIO.HIGH)
            statePrev = state

def barcodeThread():
    while True:
        time.sleep(2)
        print state
