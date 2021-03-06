import subprocess
import threading
from time import gmtime, strftime
import os
import datetime
import json
#This is the file with all posible jobs defined.
#If you want to add a job to the list, add it to the dictionary and create the correct function for it.






def restart(job):
    subprocess.Popen(["sudo", "reboot"]).wait() #Sends sudo restart to the terminal, which will restart the RPi

def shutdown(job):
    subprocess.Popen(["sudo", "halt"]).wait() #Sends sudo halt to the terminal, which directly shuts the pi down

def update(job):
    updateCode() #too big for one single easy function...


def listFunc(job):     #Prints the list of the items you should buy!!!
    output = ""
    liste = json.loads(job["List"])
    for item in liste: #iterates through the list

        output += "-" + item["Title"] + "\n"
    printCommand(output)

def text(job): #Print the text entered on the website / app.
    output = ""+ job["Text"]+""
    printCommand(output)


def qrCode(job):
    import barcode
    from barcode.writer import ImageWriter
    EAN = barcode.get_barcode_class('EAN')
    ean = EAN(job["Code"], writer=ImageWriter())
    #filename = os.path.dirname(os.path.realpath(__file__)) + "/../printjobs/" + getTimeStamp() + ".txt"
    ean.save( os.path.dirname(os.path.realpath(__file__)) + "/../printjobs/ean13_barcode")
    command = "lpr -o fit-to-page " + os.path.dirname(os.path.realpath(__file__)) + "/../printjobs/ean13_barcode.png"
    subprocess.Popen(command, shell=True).wait()
    #now print this thing!








def printCommand(text): #This is the function that is called with a string containing a text that should be printed.
    filename = os.path.dirname(os.path.realpath(__file__)) + "/../printjobs/" + getTimeStamp() + ".txt"
    filex = open(filename, 'w+')
    filex.write(text)
    filex.close()

    command = "sudo lpr '"+ filename + "'"
    print command
    subprocess.Popen(command, shell=True).wait()
    os.remove(filename)
    

def getTimeStamp():
    dt = datetime.datetime.now()
    return dt.strftime("%Y%j%H%M%S") + str(dt.microsecond)

def updateCode():
    print "Downloading and installing new updates."
    downloadGithub()
    print "Restarting the program"
    restart()
def downloadGithub():
    dir_path = os.path.dirname(os.path.dirname(os.path.dirname(os.path.realpath(__file__))));
    cmd ="curl -H \"Authorization: token 210928caef2212cda9586bb6dab335af19bfdf1a\" -Ls https://api.github.com/repos/arendjan/pws/tarball/ > "+ dir_path + "/smartfridge/wut.tar.gz"

    subprocess.Popen(cmd, shell=True).wait()
    cmd = "mkdir "+ dir_path + "/smartfridge/temp"
    subprocess.Popen(cmd, shell=True).wait()
    cmd = "tar -xvzf "+ dir_path + "/smartfridge/wut.tar.gz -C "+ dir_path + "/smartfridge/temp"
    subprocess.Popen(cmd, shell=True).wait()
    cmd = "command cp -rfv "+ dir_path + "/smartfridge/temp/*/raspberry/* "+ dir_path + "/smartfridge "
    subprocess.Popen(cmd, shell=True).wait()
    cmd = "rm -rf "+ dir_path + "/smartfridge/temp"
    subprocess.Popen(cmd, shell=True).wait()
    cmd = "rm -rf "+ dir_path + "/smartfridge/wut.tar.gz"
    subprocess.Popen(cmd, shell=True).wait()
    os.execl(sys.executable, sys.executable, *sys.argv) #this is some magic, it'll restart the main,php function. Don't ask why :P








options = {
    "restart" : restart,
    "barcode" : qrCode,
    "list" :listFunc,
    "text" :  text,
    "update" : update,
    "shutdown" : shutdown
}
def parseJob(jsonX):#this will call your function!

    if jsonX["Type"] in options:
        options[jsonX["Type"]](jsonX)
    else:
        print "Not added to the dictionary options"
