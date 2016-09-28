import sys
import select
import time
def heardEnter():
    i,o,e = select.select([sys.stdin],[],[],0.0001)
    for s in i:
        print s
        if s == sys.stdin:
            input = sys.stdin.readline()
            return True

while True:
    heardEnter()
    time.sleep(1)
