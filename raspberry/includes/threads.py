import jobsThread
import keyboardThread
import awakeThread
def startAll():
        keyboardThread.start()
        jobsThread.start()
        awakeThread.start()








if __name__ == "__main__":
    print "Don't run this file! :P"
