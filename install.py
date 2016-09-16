import os
import sys
print "this file installs the all the needed files and packages for the smart fridge"
if os.getuid() != 0:
    print "I need sudo right. plz (insert cute cat image)"
    sys.exit(1)
print "installing curl"
os.system("sudo apt-get install curl")
cmd = "mkdir ~/smartfridge"
os.system(cmd)
os.system("cd ~/smartfridge")
cmd ="curl -H \"Authorization: token 210928caef2212cda9586bb6dab335af19bfdf1a\" \-L https://api.github.com/repos/arendjan/pws/tarball > ~/smartfridge/wut.tar.gz"
os.system(cmd)
cmd = "mkdir ~/smartfridge/temp"
os.system(cmd)
cmd = "tar -xvzf ~/smartfridge/wut.tar.gz -C ~/smartfridge/temp"
os.system(cmd)
cmd = "mv -v ~/smartfridge/temp/*/raspberry/* ~/smartfridge "
os.system(cmd)
cmd = "rm -rf ~/smartfridge/temp"
os.system(cmd)
cmd = "rm -rf ~/smartfridge/wut.tar.gz"
os.system(cmd)
