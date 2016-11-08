from crontab import CronTab

cron = CronTab(user=True)
job = cron.new(command='python /home/pi/smartfridge/eersteTests/print.py')
job.every(2).minutes()
cron.write()
