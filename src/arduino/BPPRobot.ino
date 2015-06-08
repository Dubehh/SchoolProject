#include <Wire.h>

/* Constanten */
const int timeTurnLeft = 537; /* 90 degree turn left */
const int timeTurnRight = 537; /* 90 degree turn Right*/
const int timePushForward = 400; /* pushes packet*/
const int timePushBackward = 400; /* resets the push thing*/
const int poortOneSpeed = 255; /* Speed*/
const int poortTwoSpeed = 255;  /* Speed */
 
/* Poorten */
const int directionPoortOne = 4;
const int speedPoortOne = 5;
const int directionPoortTwo = 7;
const int speedPoortTwo = 6;
 
/* Variablen */
String queue = "";
 
int binCounter = 1;
int binAlpha = 0;
int binBeta = 0;
int binGamma = 0;
int binDelta = 0;
 
/* Gegevens
   
   HIGH: Rechts
   LOW: Links
   Motor één: BBP as
   Motor twee: Ballenschieter BBP
 
*/
 
void setup() {
  Wire.begin(4);
  Wire.onReceive(receiveEvent);
  Serial.begin(9600);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
}
 
void loop() {
  if(queue.length() > 0){
    String current = queue.substring(0, 3);
    stringToVoid(current);
    queue = queue.substring(3, queue.length());
  }
}
 
/*
 Reads all commands
*/
void receiveEvent(int howMany){
   String cmd = "";
   while(0 < Wire.available()){
     char c = Wire.read();
     cmd += String(c);
   }
   if(queue.length() > 0){
      queue+= ","+cmd;
   }else{
      queue = cmd;
   }
}
 
/* Move to next right bin */
void nextBinRight(int rotations){
  int newcount = binCounter + rotations;
  while(newcount > 4){
    newcount -= 4;
  }
  binCounter = newcount;
}
 
/* Move to next left bin */
void nextBinLeft(int rotations){
  int newcount = binCounter - rotations;
  while(newcount < 1){
    newcount += 4;
  }
  binCounter = newcount;
}
 
/* Get huidige bin size */
int getCurrentBinSize(){
  switch(binCounter){
    case 1: return binAlpha;
    case 2: return binBeta;
    case 3: return binGamma;
    case 4: return binDelta;
  }
}
 
void updateBins(){
  switch(binCounter){
    case 1: binAlpha++;
    case 2: binBeta++;
    case 3: binGamma++;
    case 4: binDelta++;
  }
}
 
/*
 translates string commands to method
*/
void stringToVoid(String cmd){
   String command = getValue(cmd, ':', 0);
   int cmdId = command.toInt();
   int amount = getValue(cmd, ':',1).toInt();
   if(amount < 1) amount = 1;
   switch(cmdId){
      case 4: pushPacket(); break;
      case 5: bppLeft(amount); break;
      case 6: bppRight(amount); break;
      default: break;
    }
   pauseBPP();  
}
 
/* Pauses BPP simulator */
void pauseBPP(){
  analogWrite(speedPoortOne, 0);
  analogWrite(speedPoortTwo, 0);
  delay(50);
}

/*
 Rotate bpp to left side
*/
void bppLeft(int amount){
  digitalWrite(directionPoortOne, LOW);
  analogWrite(speedPoortOne, poortOneSpeed);
  nextBinLeft(amount);
  delay(timeTurnLeft*amount);
  if(getCurrentBinSize() > 3){
    bppLeft(1);
  }
}
 
/*
 rotate bpp to right side
*/
void bppRight(int amount){
  digitalWrite(directionPoortOne, HIGH);
  analogWrite(speedPoortOne, poortOneSpeed);
  nextBinRight(amount);
  delay(timeTurnRight*amount);
}

/* Pushes packet into segway*/
void pushPacket(){
  analogWrite(speedPoortTwo, poortTwoSpeed);
  digitalWrite(directionPoortTwo, HIGH);
  delay(timePushForward);
  digitalWrite(directionPoortTwo, LOW);
  delay(timePushBackward);
}
 
/*
 Split string
*/
String getValue(String data, char separator, int index)
{
 int found = 0;
  int strIndex[] = {0, -1};
  int maxIndex = data.length()-1;
  for(int i=0; i<=maxIndex && found<=index; i++){
  if(data.charAt(i)==separator || i==maxIndex){
  found++;
  strIndex[0] = strIndex[1]+1;
  strIndex[1] = (i == maxIndex) ? i+1 : i;
  }
 }
  return found>index ? data.substring(strIndex[0], strIndex[1]) : "";
}
