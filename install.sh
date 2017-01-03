

if [[ $EUID -ne 0 ]]; then
    echo "I need sudo right. plz (insert cute cat image)"
    exit 1
fi

UPDATE=""
USERID=""

while getopts ":u:I:" opt; do
  case "$opt" in
    u) UPDATE="$OPTARG"
            ;;
    I) USERID="$OPTARG"
            ;;
    esac
done


if [ "N" != "$UPDATE" ]; then
  echo "updating..."
  sudo apt-get -qq update
  sudo apt-get -qq install curl python-setuptools python-dev build-essential python-pip
  sudo -H pip install --upgrade pip > /dev/null
  sudo -H pip install --upgrade virtualenv > /dev/null
  sudo -H pip install pyBarcode > /dev/null
  echo "done updating"
fi
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
mkdir "$DIR/smartfridge"
echo "downloading the code..."
curl -Ls "https://api.github.com/repos/arendjan/pws/tarball" > "$DIR/smartfridge/GIT.tar.gz"
echo "done downloading"
echo "unzipping and putting the files in the correct place..."
mkdir -m 777 $DIR/smartfridge/temp
tar -xzf "$DIR/smartfridge/GIT.tar.gz" -C "$DIR/smartfridge/temp"
cp -rf "$DIR"/smartfridge/temp/*/raspberry/*  "$DIR/smartfridge"
cp -rf "$DIR/smartfridge/start.py" "$DIR/start.py"
rm -rf "$DIR/smartfridge/temp"
rm -rf "$DIR/smartfridge/GIT.tar.gz"
sudo chmod -R 777 "$DIR/smartfridge/"
sudo chmod -R 777 "$DIR/smartfridge/printjobs" 
echo "done"
echo "setting up a cronjob"
#write out current crontab
crontab -l > mycron
#echo new cron into cron file
echo "@reboot sudo python $DIR/start.py" >> mycron
#install new cron file
crontab mycron
rm mycron
echo "done setting up the cronjob"

if [ ! -z  "$USERID"  ]; then
  echo "setting up your settings file"
  echo $'userId="'$USERID$'"\ninterval=5\nurl="http://pws.svshizzle.com/api/"' > "$DIR/smartfridge/settings.py"
  echo "Done writing"
fi
echo "Doei"