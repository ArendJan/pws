import os
import sys
print "anoesss"
dir_path = os.path.dirname(os.path.realpath(__file__))
print "this file installs the all the needed files and packages for the smart fridge"
if os.getuid() != 0:
    print "I need sudo right. plz (insert cute cat image)"
    sys.exit(1)
print "installing curl"
os.system("sudo apt-get install curl")
cmd = "mkdir "+ dir_path + "/smartfridge"
os.system(cmd)
os.system("cd "+ dir_path + "/smartfridge")
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
print "anoesss"
