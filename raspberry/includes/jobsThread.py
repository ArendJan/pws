import os
import settings
import sys
def start():
    print "start jobs thread"
    downloadGithub()


def updateCode():
    print "Downloading and installing new updates."
    downloadGithub()
    print "Restarting the program"
    restart()
def downloadGithub():
    dir_path = os.path.dirname(os.path.realpath(__file__))+"../../";
    cmd ="curl -H \"Authorization: token 210928caef2212cda9586bb6dab335af19bfdf1a\" \-L https://api.github.com/repos/arendjan/pws/tarball/AJMaandag199 > "+ dir_path + "/smartfridge/wut.tar.gz"
    os.system(cmd)
    cmd = "mkdir "+ dir_path + "/smartfridge/temp"
    os.system(cmd)
    cmd = "tar -xvzf "+ dir_path + "/smartfridge/wut.tar.gz -C "+ dir_path + "/smartfridge/temp"
    os.system(cmd)
    cmd = "mv -v "+ dir_path + "/smartfridge/temp/*/raspberry/* "+ dir_path + "/smartfridge "
    os.system(cmd)
    cmd = "rm -rf "+ dir_path + "/smartfridge/temp"
    os.system(cmd)
    cmd = "rm -rf "+ dir_path + "/smartfridge/wut.tar.gz"
    os.system(cmd)
def restart():
    os.execl(sys.executable, sys.executable, *sys.argv)

def jobsThread():
    try:
        url = "http://pws.svshizzle.com/status.php"
        response = requests.get(url)
        return json.loads(response.text)

    except Interrupt:
        print "ripppp"
