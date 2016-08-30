# _*_ coding:utf-8 _*_
import requests
import sys
from BeautifulSoup import BeautifulSoup
#import os


class Item(object):
    titel = ""
    prijs = ""
    omschrijving = ""
    bereidingsmethode = ""
    bewaren = ""
    ingredienten = ""
    fabrikant = ""

    def __item__(self, titel=None, prijs=None, omschrijving=None, bereidingsmethode=None, bewaren=None, ingredienten=None, fabrikant=None):
        self.titel= titel
        self.prijs = prijs
        self.omschrijving = omschrijving
        self.bereidingsmethode = bereidingsmethode
        self.bewaren = bewaren
        self.ingredienten = ingredienten
        self.fabrikant = fabrikant

    def getItem(self,html, beginString, eindString, offset=0):
        try:
            indexB = html.index(beginString)+len(beginString)
            indexE = html[indexB:].index(eindString) + indexB
            description = html[indexB:indexE]
            return description[offset:]

        except:
            return "ebola"

    def bestaatItem(self, html):
        if(self.getItem(html, "name=\"description\"", "\" />", 10)=="Jumbo Groceries"):
            return False
        return True


    def sloopHTML(self,html):
        #de titel slopen!!
        self.titel = self.getItem(html, "name=\"description\"", "\" />", 10)
        prijstext = self.getItem(html, "<div class=\"jum-sale-price\" data-jum-role=\"price\" data-dynamic-block-name=\"SalePrice\" data-dynamic-block-id=\"\"><span class=\"jum-price-format\">", "</span>")
        self.prijs = prijstext[:prijstext.index("<sup>")]+","+prijstext[prijstext.index("<sup>")+len("<sup>"):prijstext.index("</sup>")]
        self.omschrijving = self.getItem(html, "<div class=\"jum-summary-description\">", "</p>", 3).replace("<br/>","")
        self.bereidingsmethode = self.getItem(html, "<h3>Bereidingsmethode</h3>", "</p>",4)
        self.bewaren = self.getItem(html, "<div class=\"jum-product-storage jum-product-info-item\">\n<h3>Bewaren</h3>\n<p>", "</p>",0)
        self.ingredienten = self.getItem(html, "<div class=\"jum-ingredients-info jum-product-info-item\"><h3>Ingredi&euml;nten</h3><ul>","</ul>",0).replace("<li>", "x").replace("</li>", "\n")
        self.fabrikant = self.getItem(html, "<div class=\"jum-product-brand-info jum-product-info-item\"><h3>Over de fabrikant</h3><p>", "<h3>",0)








text = raw_input()
url = "http://www.jumbo.com/zoeken?SearchTerm="+text
resp = requests.get(url, verify=True)
html = resp.text
html = html.encode('ascii', 'ignore')
item = Item()
if(not item.bestaatItem(html)):
    print "betaat niet!"
    sys.exit()
item.sloopHTML(html)
print item.prijs
print item.titel
print item.omschrijving
print item.bereidingsmethode
print item.bewaren
print item.ingredienten
print item.fabrikant

cmd = 'sudo echo \"Je hebt '+item.titel+' gescand!!!\n\n\n\n\n\" > /dev/usb/lp0'




#os.system(cmd)
