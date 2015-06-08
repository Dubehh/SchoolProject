#include <Wire.h>
 
/* Constanten */
const int xMotor1 = 4;
const int xMotor2 = 5;
const int yMotor1 = 7;
const int yMotor2 = 6;
 
const int xMotorSpeed = 150;
const int yMotorSpeedUp = 240;
const int yMotorSpeedDown = 120;
 
/* Variabelen */
String queue = "";
int y = 0;
int x = 0;
 
void setup() {
  Wire.begin();
  Serial.begin(9600);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
}
 
void loop() {
  initiateCommands();
  if(queue.length() > 0){
    String current = queue.substring(0, 3);
    stringToVoid(current);
    queue = queue.substring(3, queue.length());
  }else{
    analogWrite(yMotor2, 0);
    analogWrite(xMotor2, 0);
  }
}
 
/*
 Reads all commands
*/
void initiateCommands(){
 if(Serial.available()){
   String cmd = Serial.readStringUntil('\n');
   if(queue.length() > 0){
     queue+= ","+cmd;
   }else{
     queue = cmd;
   }
 }
}
 
/* Send command to slave */
void sendToSlave(String cmd){
  Wire.beginTransmission(4);
  char charBuf[cmd.length()+1];
  cmd.toCharArray(charBuf, cmd.length()+1);
  Wire.write(charBuf);
  Wire.endTransmission();
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
      case 0: up(amount); break;
      case 1: down(amount); break;
      case 2: left(amount); break;
      case 3: right(amount); break;
      default: 
        sendToSlave(cmd); 
        if(cmdId == 4) delay(1:10800);
        break;
    }
}
 
/* Move robot up */
void up(int amount){
  if(y >= 4)
  {
    return;
  }
  int delayTime = 700;
  digitalWrite(yMotor1, LOW);
  analogWrite(yMotor2, yMotorSpeedUp);
  delay(delayTime*amount);
  analogWrite(yMotor2, 0);
  y++;
}
 
/* Move robot down */
void down(int amount){
  if(y <= 0)
  {
    return;
  }
  int delayTime = 175;
  switch(y)
  {
    case 4:
    delayTime = 200;
    break;
   
    case 3:
    delayTime = 175;
    break;
   
    case 2:
    delayTime = 150;
    break;
   
    case 1:
    delayTime = 125;
    break;
  }
  digitalWrite(yMotor1, HIGH);
  analogWrite(yMotor2, yMotorSpeedDown);
  delay(delayTime*amount);
  analogWrite(yMotor2, 0);
  y--;
}
 
/* Move robot left */
void left(int amount){
  digitalWrite(xMotor1, LOW);
  analogWrite(xMotor2, xMotorSpeed);
  delay(1900*amount);
  analogWrite(xMotor2, 0);
}
 
/* Move robot right */
void right(int amount){
  digitalWrite(xMotor1, HIGH);
  analogWrite(xMotor2, xMotorSpeed);
  delay(1900*amount);
  analogWrite(xMotor2, 0);
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
