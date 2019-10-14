# PlantCare
It's a project to control our plants' status in real time. The idea was born from the need to monitor the humidity and when you need you can irrigate your plants far from home with one click.

**Requirements**

To develop PlantCare we used Flask python framework server side, Android Studio with Java for the App and Nodemcu esp8266, relay and moisture sensor.

**Development**

The server side was developed on Linux x64 machine and the client side was developed on Mac.

**How Execute**

After cloned the repository, to execute the project you must install python3, Flask with the command "sudo pip3 install flask", put on the server the file server.py and the directory "piante" and at the end execute server.py with the command "python3 server.py".
On client side you must install Arduino IDE and Android Studio.
With Android Studio import the directory "Applicazione" and change the Strings variable 'url' with the IP address of your server and then run the app.
With Arduino IDE import the sketch "arduino.ino" and change the variable ssid with your network ssid, pass with your modem password and host with the IP address of your server. If you use NodeMCU change the settings as report in this link: https://www.instructables.com/id/Steps-to-Setup-Arduino-IDE-for-NODEMCU-ESP8266-WiF/ .
At the end you can load the sketch on your board.

To debug the sketch running on arduino you can verify the serial monitor.
