#include "MicroGear.h"
#include "WiFi.h"

const char* ssid     = "HUAWEI Y9 2018";
const char* password = "0983264147";

#define APPID   "EDC"
#define KEY     "atXbHGWUX9NWjH0"
#define SECRET  "LS3EzlrIeGAmElEw5bF1xIocC"
#define ALIAS   "esp32_node1"
#define LEDPIN   16
WiFiClient client;
int timer = 0;
int ledState = LOW;
int ledState1 = LOW; 
int ledState2 = LOW; 
int ledState3 = LOW; 
int ledState4 = LOW;
MicroGear microgear(client);

/* If a new message arrives, do this */
void onMsghandler(char *topic, uint8_t* msg, unsigned int msglen) {
  Serial.print("Incoming message --> ");
  msg[msglen] = '\0';
  Serial.print('#');
  Serial.print((char *)msg);
  String strMsg = (char *)msg;
  Serial.println('#');
  
  if (strMsg=="On1")
  {
    ledState1 = LOW; 
  }
  else if (strMsg=="Off1")
  {
    ledState1 = HIGH; 
  }
  else if (strMsg=="On2")
  {
    ledState2 = LOW ;  
    
  }
  else if (strMsg=="Off2")
  {
    ledState2 = HIGH; 
  }
  else if (strMsg=="On3")
  {
    ledState3 = LOW; 
     
  }
  else if (strMsg=="Off3")
  {
    ledState3 = HIGH; 
  }
  else if (strMsg=="On4")
  {
    ledState4 = LOW; 
     
  }else if (strMsg=="Off4")
  {
    ledState4 = HIGH;
    
  }

}

void onFoundgear(char *attribute, uint8_t* msg, unsigned int msglen) {
  Serial.print("Found new member --> ");
  for (int i = 0; i < msglen; i++)
    Serial.print((char)msg[i]);
  Serial.println();
}

void onLostgear(char *attribute, uint8_t* msg, unsigned int msglen) {
  Serial.print("Lost member --> ");
  for (int i = 0; i < msglen; i++)
    Serial.print((char)msg[i]);
  Serial.println();
}

/* When a microgear is connected, do this */
void onConnected(char *attribute, uint8_t* msg, unsigned int msglen) {
  Serial.println("Connected to NETPIE...");
  /* Set the alias of this microgear ALIAS */
  microgear.setAlias(ALIAS);
}


void setup() {
  /* Add Event listeners */

  /* Call onMsghandler() when new message arraives */
  microgear.on(MESSAGE, onMsghandler);

  /* Call onFoundgear() when new gear appear */
  microgear.on(PRESENT, onFoundgear);

  /* Call onLostgear() when some gear goes offline */
  microgear.on(ABSENT, onLostgear);

  /* Call onConnected() when NETPIE connection is established */
  microgear.on(CONNECTED, onConnected);

  Serial.begin(115200);
  Serial.println("Starting...");

  /* Initial WIFI, this is just a basic method to configure WIFI on ESP8266.                       */
  /* You may want to use other method that is more complicated, but provide better user experience */
  if (WiFi.begin(ssid, password)) {
    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
    }
  }

  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  /* Initial with KEY, SECRET and also set the ALIAS here */
  microgear.init(KEY, SECRET, ALIAS);

  /* connect to NETPIE to a specific APPID */
  microgear.connect(APPID);
  
  pinMode(0, OUTPUT);
  pinMode(2, OUTPUT);
  pinMode(15, OUTPUT);
  pinMode(13, OUTPUT);
}

void loop() {
  /* To check if the microgear is still connected */
  if (microgear.connected()) {
    Serial.println("connected");

    /* Call this method regularly otherwise the connection may be lost */
    microgear.loop();

    if (timer >= 1000) {
      Serial.println("Publish...");

      /* Chat with the microgear named ALIAS which is myself */
      microgear.chat(ALIAS, "Hello");
      timer = 0;
    }
    else timer += 100;
  }
  else {
    Serial.println("connection lost, reconnect...");
    if (timer >= 5000) {
      microgear.connect(APPID);
      timer = 0;
    }
    else timer += 100;
  }

  digitalWrite(0, ledState1);
  digitalWrite(2, ledState2);
  digitalWrite(15, ledState3);
  digitalWrite(13   , ledState4);
  delay(100);
}
