#include "ESP8266WiFi.h"
#include <HttpClient.h>
#include <Bridge.h>


#define relay D0
#define s_moisture A0

const char *ssid = "ssid";
const char *pass = "pass";
const char* host = "host";
const int port = 5000;

void setup() {
  String response;
  Serial.begin(9600);
  pinMode(relay, OUTPUT);
  pinMode(s_moisture, INPUT);

  WiFi.begin(ssid, pass);

  while(WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.println("In connessione...");
  }

  Serial.println(WiFi.localIP());

}
int value=0;


void loop() {

  //delay(500);
  
 String line1=controlloRichiesta("/ControlloMoisture");
 if(line1.indexOf("True")>0){ 
    grado();
  }
 String line2=controlloRichiesta("/ControlloIrrigazione");
  
  if(line2.indexOf("True")>0){
    innaffia();
  }
 
  Serial.println();
}


String controlloRichiesta(String url){
  Serial.print("connecting to ");
  Serial.print(host);
  Serial.print(':');
  Serial.println(port);

  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  if (!client.connect(host, port)) {
    Serial.println("connection failed");
    delay(5000);
    return "";
  }

  // This will send a string to the server
  Serial.println("sending data to server");
  if (client.connected()) {
    client.print(String("GET ") + url + " HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "Connection: close\r\n\r\n");
  }

  // wait for data to be available
  unsigned long timeout = millis();
  while (client.available() == 0) {
    if (millis() - timeout > 5000) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      delay(60000);
      return "";
    }
  }

  String line = "";

  // Read all the lines of the reply from server and print them to Serial
  Serial.println("receiving from remote server");
  // not testing 'client.connected()' since we do not need to send data here
  while (client.available()) {
    line = client.readStringUntil('\r');
  }

  Serial.println(line);

  // Close the connection
  Serial.println();
  Serial.println("closing connection");
  client.stop();

  return line;
}

  void grado(){
  ++value;

  Serial.print("connecting to ");
  Serial.println(host);
 
  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort =5000;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
 
  // We now create a URI for the request
  String url = "/grado/";
 
  Serial.print("Requesting URL: ");
  Serial.println(url);
  client.print(String("GET ") + url + analogRead(s_moisture) + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" +
               "Connection: close\r\n\r\n");
    Serial.println(analogRead(s_moisture));
     unsigned long timeout = millis();
  while (client.available() == 0) {
    if (millis() - timeout > 5000) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
   }
}

void innaffia(){
  
  digitalWrite(relay, HIGH);
  delay(2000);
  digitalWrite(relay, LOW);
  //delay(2000);

}
