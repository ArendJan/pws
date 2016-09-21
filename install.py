import os
import sys
import argparse
import subprocess
parser = argparse.ArgumentParser(description="Your personal userId")
parser.add_argument('--userId')
parser.add_argument('--branch')
args = parser.parse_args()
userid = args.userId
branch = args.branch
dir_path = os.path.dirname(os.path.realpath(__file__))
print "this file installs the all the needed files and packages for the smart fridge"
if os.getuid() != 0:
    print "I need sudo right. plz (insert cute cat image)"
    sys.exit(1)
print "updating packages list"

cmd = "sudo apt-get update"
subprocess.Popen(cmd, shell=True).wait()

print "installing curl"
cmd = "sudo apt-get -qq install curl"
subprocess.Popen(cmd, shell=True).wait()
print "creating directory"
cmd = "mkdir "+ dir_path + "/smartfridge"
subprocess.Popen(cmd, shell=True).wait()
print "downloading code from python"
add = ""
if(branch!=None):
    add = "/"+branch
cmd ="curl -H \"Authorization: token 210928caef2212cda9586bb6dab335af19bfdf1a\" -Ls https://api.github.com/repos/arendjan/pws/tarball"+add+" > "+ dir_path + "/smartfridge/wut.tar.gz"
subprocess.Popen(cmd, shell=True).wait()
cmd = "mkdir -m 777 "+ dir_path + "/smartfridge/temp"
subprocess.Popen(cmd, shell=True).wait()
print "unzipping the file"
cmd = "tar -xzf "+ dir_path + "/smartfridge/wut.tar.gz -C "+ dir_path + "/smartfridge/temp"
subprocess.Popen(cmd, shell=True).wait()
print "copying the files"
cmd = "command cp -rf "+ dir_path + "/smartfridge/temp/*/raspberry/* "+ dir_path + "/smartfridge "
subprocess.Popen(cmd, shell=True).wait()
cmd = "command cp -rf "+ dir_path + "/smartfridge/start.py "+ dir_path + "/ "
subprocess.Popen(cmd, shell=True).wait()
print "removing garbage"
cmd = "rm -rf "+ dir_path + "/smartfridge/temp"
subprocess.Popen(cmd, shell=True).wait()
cmd = "rm -rf "+ dir_path + "/smartfridge/wut.tar.gz"
subprocess.Popen(cmd, shell=True).wait()
if(userid!=None):
    f = open(dir_path+"/smartfridge/settings.py", 'w')
    f.write("userId=\""+userid+"\"\ninterval=5\nurl=\"http://pws.svshizzle.com/api/\"");
    print userid

print "Done!"
