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
    cmd ="curl -H \"Authorization: token 210928caef2212cda9586bb6dab335af19bfdf1a\" \-L https://api.github.com/repos/arendjan/pws/tarball > ~/smartfridge/wut.tar.gz"
    os.system(cmd)
    cmd = "mkdir temp"
    os.system(cmd)
    cmd = "tar -xvzf ~/smartfridge/wut.tar.gz -C ~/smartfridge/temp"
    os.system(cmd)
    cmd = "mv -v ~/smartfridge/temp/*/raspberry/* ~/smartfridge "
    os.system(cmd)
    cmd = "rm -rf ~/smartfridge/temp"
    os.system(cmd)
    cmd = "rm -rf ~/smartfridge/wut.tar.gz"
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
