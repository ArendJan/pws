import os
import sys
import argparse
parser = argparse.ArgumentParser(description="Your personal userId")
parser.add_argument('--userId')
args = parser.parse_args()
userid = args.userId
dir_path = os.path.dirname(os.path.realpath(__file__))
print "this file installs the all the needed files and packages for the smart fridge"
if os.getuid() != 0:
    print "I need sudo right. plz (insert cute cat image)"
    sys.exit(1)
print "updating packages list"
os.system("sudo apt-get update")
print "installing curl"
os.system("sudo apt-get -qq install curl")
print "creating directory"
cmd = "mkdir "+ dir_path + "/smartfridge"
os.system(cmd)
print "downloading code from python"
cmd ="curl -H \"Authorization: token 210928caef2212cda9586bb6dab335af19bfdf1a\" -Ls https://api.github.com/repos/arendjan/pws/tarball > "+ dir_path + "/smartfridge/wut.tar.gz"
os.system(cmd)
cmd = "mkdir -m 777 "+ dir_path + "/smartfridge/temp"
os.system(cmd)
print "unzipping the file"
cmd = "tar -xzf "+ dir_path + "/smartfridge/wut.tar.gz -C "+ dir_path + "/smartfridge/temp"
os.system(cmd)
print "copying the files"
cmd = "command cp -rf "+ dir_path + "/smartfridge/temp/*/raspberry/* "+ dir_path + "/smartfridge "
os.system(cmd)
print "removing garbage"
cmd = "rm -rf "+ dir_path + "/smartfridge/temp"
os.system(cmd)
cmd = "rm -rf "+ dir_path + "/smartfridge/wut.tar.gz"
os.system(cmd)
if(userid!=None):
    f = open(dir_path+"/smartfridge/settings.py", 'w')
    f.write("userId=\""+userid+"\"\ninterval=5\nurl=\"http://pws.svshizzle.com/api/\"");
    print userid
print "Done!"
