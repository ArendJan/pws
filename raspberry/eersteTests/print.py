import os
import datetime
import subprocess
def getTimeStamp():
    dt = datetime.datetime.now()
    return dt.strftime("%Y%j%H%M%S") + str(dt.microsecond)

filename = os.path.dirname(os.path.realpath(__file__)) + "/" + getTimeStamp() + ".txt"
filex = open(filename, 'w+')
filex.write(getTimeStamp())
filex.close()

command = "sudo lpr '"+ filename + "'"
print command
subprocess.Popen(command, shell=True).wait()
os.remove(filename)
