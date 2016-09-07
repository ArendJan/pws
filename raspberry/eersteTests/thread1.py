import threading
import requests
import time
import json
import sys
def keyboardThread():
	
	while 1:
		try:
			text = raw_input("Barcode:")
			threadC = threading.Thread(target=sendCodeThread, args=[text])
			threadC.start()
		except KeyboardInterrupt:
			print ""
			sys.exit()
	
def sendCodeThread(code):
		url = "http://pws.svshizzle.com/newItem.php?code=" + code
		resp = requests.get(url)
		print resp.text#de text van de server.
		
def getStatus():
	try:
		url = "http://pws.svshizzle.com/status.php"
		response = requests.get(url)
		return json.loads(response.text)
	except Interrupt:
		print "ripppp"

def getAndParseStatus():
	status = getStatus()
	switchStatus(status)
	
def switchStatus(status):
	if(status.status=="barcode"):
		#print barcode
		print "barcode"
	if(status.status=="boodschappenlijstje"):
		#print boodschappenlijstje
		print "boodschappenlijstje"
	

def checkServer():
	while 1:
		try:
			getAndParseStatus()
			time.sleep(0.5)
		except Interrupt:
			print "shitttt"


threading.Thread(target = keyboardThread, args=[]).start()



