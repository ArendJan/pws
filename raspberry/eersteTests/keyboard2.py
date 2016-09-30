f = open('/dev/input/event0')
while True:
    raw_input()
    print unicode(f.read(10))
