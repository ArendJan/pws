import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)

GPIO.setup(4, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(27, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(22, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(18, GPIO.OUT)
GPIO.setup(23, GPIO.OUT)
GPIO.setup(24, GPIO.OUT)
GPIO.setup(25, GPIO.OUT)


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
        
        state = "IN"
        time.sleep(0.2)

    if GPIO.input(27) == False:
        state = "OPEN"
        
        time.sleep(0.2)

    if GPIO.input(22) == False:
        state = "OUT"
        
        time.sleep(0.2)
    if(state!=statePrev):
        GPIO.output(lights[statePrev], GPIO.LOW)
        GPIO.output(lights[state], GPIO.HIGH)
        statePrev = state
    
