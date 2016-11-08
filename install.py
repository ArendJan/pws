import os
import sys
import argparse
import subprocess
#Add some arguments to the command!!! Makes life easier!!!!
parser = argparse.ArgumentParser(description="Your personal userId")
parser.add_argument('--userId')
parser.add_argument('--branch')
parser.add_argument('--updateInstall')
args = parser.parse_args()
userid = args.userId
branch = args.branch
update = args.updateInstall

#Get the path of the file that's currently running.
dir_path = os.path.dirname(os.path.realpath(__file__))

devnull = open(os.devnull, 'wb') #python >= 2.4

print "this file installs the all the needed files and packages for the smart fridge"
if os.getuid() != 0: #python voodoooo
    print "I need sudo right. plz (insert cute cat image)"
    sys.exit(1) # just die!
print "updating packages list"
if(update!="N"): #Then don't install updates (for updating and testing much faster, much wow)
    cmd = "sudo apt-get update"
    subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()

    print "installing curl and pip"
    cmd = "sudo apt-get -qq install curl python-setuptools python-dev build-essential python-pip"
    subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()

    print "Updating pip 1/2"
    cmd = "sudo pip install --upgrade pip"
    subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()

    print "Updating pip 2/2"
    cmd = "sudo pip install --upgrade virtualenv"
    subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()

    print "installing pyBarcode"
    cmd = "sudo pip install pyBarcode"
    subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()

    print "installing crontab-python"
    cmd = "sudo pip install python-crontab"
    subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()


print "creating directory"
cmd = "mkdir "+ dir_path + "/smartfridge"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()

#use curl to download the whole github folder. For testing: add a branch to the command.
print "downloading code from python"
add = ""

if(branch!=None):
    add = "/"+branch
#The Authorization token is because it is a private repo.
cmd ="curl -H \"Authorization: token 210928caef2212cda9586bb6dab335af19bfdf1a\" -Ls https://api.github.com/repos/arendjan/pws/tarball"+add+" > "+ dir_path + "/smartfridge/wut.tar.gz"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
cmd = "mkdir -m 777 "+ dir_path + "/smartfridge/temp"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
print "unzipping the file"
cmd = "tar -xzf "+ dir_path + "/smartfridge/wut.tar.gz -C "+ dir_path + "/smartfridge/temp"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
print "copying the files"
cmd = "command cp -rf "+ dir_path + "/smartfridge/temp/*/raspberry/* "+ dir_path + "/smartfridge "
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
cmd = "command cp -rf "+ dir_path + "/smartfridge/start.py "+ dir_path + "/ "
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
print "removing garbage"
cmd = "rm -rf "+ dir_path + "/smartfridge/temp"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
cmd = "rm -rf "+ dir_path + "/smartfridge/wut.tar.gz"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
#Only sudo has permission, but we need to have permission!
cmd = "sudo chmod -R 777 "+ dir_path + "/smartfridge/"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()
#Only sudo has permission, but we need to have permission!
cmd = "sudo chmod -R 777 "+ dir_path + "/smartfridge/printjobs"
subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=devnull).wait()

#create crontab
from crontab import CronTab
print "Creating cronjob"
cron = CronTab(user=True)
cron.remove_all()
job = cron.new(command='python ' + dir_path + "/start.py > /home/pi/backup.txt 2>&1")
job.every_reboot()
cron.write()
for job in cron:
    print job

#If you've entered an userId, the whole settings file will be rewritten.
if(userid!=None):
    f = open(dir_path+"/smartfridge/settings.py", 'w')
    f.write("userId=\""+userid+"\"\ninterval=5\nurl=\"http://pws.svshizzle.com/api/\"");

print "Done!"
