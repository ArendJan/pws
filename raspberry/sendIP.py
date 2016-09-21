import requests
import socket
import socket
import urllib2
import time
def internet_on():
    try:
        response=urllib2.urlopen('http://google.com',timeout=10)
        print "lel"
        return True
    except urllib2.URLError as err: pass
    return False
    
while(not internet_on()):
	print "not yet"
	time.sleep(1)
	
	
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.connect(('8.8.8.8', 0))  # connecting to a UDP address doesn't send packets

ip = socket.gethostbyname(socket.getfqdn())
ip = s.getsockname()[0]
url="http://pws.svshizzle.com/raspberry/IP.php?ip="+ip
resp = requests.get(url)
