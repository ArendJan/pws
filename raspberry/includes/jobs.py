import subprocess
#This is the file with all posible jobs defined.
#If you want to add a job to the list, add it to the dictionary and create the correct function for it.






def restart(json):
    subprocess.Popen(["sudo", "restart"]) #Sends sudo restart to the terminal, which will restart the RPi

def shutdown(json):
    subprocess.Popen(["sudo", "halt"]) #Sends sudo halt to the terminal, which directly shuts the pi down

def update(json):
    updateCode() #too big for one single easy function...


def list(json):
    output = ""
    with open('/dev/usb/lp0', 'w') as printer:
        for item in json["Items"]:
            output += "- " + item["Title"]
            printer.write("- " + item["Title"] + "\n")

def text(json):
    with open('/dev/usb/lp0', "w") as printer:
        printer.write("New text:\n"+json["Text"]+"\n\n\n")

def qrCode(json):
    print "lelelle"









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
    "qrCode" : qrCode,
    "list" :listFunc,
    "text" :  text,
    "update" : update,
    "shutdown" : shutdown
}
def parseJob(json):#this will call your function!
    options[json["Type"]](json)
