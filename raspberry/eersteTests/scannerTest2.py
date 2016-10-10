import sys

fp = open('/dev/hidraw0', 'rb')
tStr = ''
while True:
  buffer = fp.read(8)
  for c in buffer:
    if ord(c) > 0:
      #print ord(c)
      tStr = tStr + c

  print tStr + "\n"
#doesn't work
#same source as #1.
