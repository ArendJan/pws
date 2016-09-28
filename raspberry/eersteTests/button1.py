import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)

GPIO.setup(4, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(27, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(22, GPIO.IN, pull_up_down=GPIO.PUD_UP)
state = "nothing"
while True:
    if GPIO.input(4) == False:
        print('Button Pressed4')
        state = "IN"
        time.sleep(0.2)

    if GPIO.input(27) == False:
        state = "OPEN"
        print('Button Pressed27')
        time.sleep(0.2)

    if GPIO.input(22) == False:
        state = "OUT"
        print('Button Pressed22')
        time.sleep(0.2)
