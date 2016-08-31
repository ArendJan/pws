# _*_ coding:utf-8 _*_
import requests
import sys
#from BeautifulSoup import BeautifulSoup
import os


class Item(object):
    titel = ""
    

    def __item__(self, titel=None):
        self.titel= titel

    def getItem(self,html, beginString, eindString, offset=0):
        try:
            indexB = html.index(beginString)+len(beginString)
            indexE = html[indexB:].index(eindString) + indexB
            description = html[indexB:indexE]
            return description[offset:]

        except:
            return ""

    def bestaatItem(self, html):
        if(self.getItem(html, "name=\"description\"", "\" />", 10)=="Jumbo Groceries"):
            return False
        return True


    def sloopHTML(self,html):
        #de titel slopen!!
        self.titel = self.getItem(html, "name=\"description\"", "\" />", 10)#deze code omschrijven naar php code. De raspberry doet niet zoveel.
        







text = raw_input()
url = "http://www.jumbo.com/zoeken?SearchTerm="+text
resp = requests.get(url, verify=True)
html = resp.text
item = Item()
if(not item.bestaatItem(html)):
    print "betaat niet!"
    sys.exit()
item.sloopHTML(html)

print item.titel

cmd = 'sudo echo \"Je hebt '+item.titel+' gescand!!!\n\n\n\" > /dev/usb/lp0'

os.system(cmd)

	






