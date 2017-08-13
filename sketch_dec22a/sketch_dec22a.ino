
byte IR_IN = A0;
byte IR_EN = 2;
byte PR_IN = A1;
byte PR_EN = 9;
byte BP_OUT= 10;

byte BTN_IN = A4;
byte PWR_IN = A5;

short IR_AVG = 0;
short PR_AVG = 0;

short C_BT_SETUP_CONFIRM = 0x1FF1;
short C_BT_SETUP_BLUETOOTH = 0x1FF2;

int messageQueueLength=128;
short messageQueue[128];//Arduino array lenghts must be compile-time constants
byte messageQueueIndex=0;

boolean debug=true;

String UUID="";

void setup() {
  Serial.begin(9600);
  initMessageQueue();
  initSetup();
  initBluetooth();
  waitForStart();
}



void loop() {
  messageQueuePoll();
}


short BTRead(){
	return Serial.read();
}

void BTSend(short data){
	if (Serial.availableForWrite()>0){
		Serial.write(data);
	}
  else{
    addToMessageQueue(data);
  }
}

boolean _isBTAvailable(){
	return Serial.available()>0;
}

void beep(int delayMillis){
	BP_OUT=1;
	if(delayMillis!=-1){
	  delay(delayMillis);
    BP_OUT=0;
	}
}

void _startCountDown(){
	beep(1000);
	beep(1000);
	beep(-1);	
	BTSend(millis());
	int _t = calcReactionTime(millis());
	BTSend(_t);	
  BP_OUT=0;
}

int calcReactionTime(unsigned long startTime){
	int _interval=0;
	bool stop;
	do{
		stop = checkForSensor(PR_IN, 0);
		_interval = millis() - startTime;
   debugLog("");
  } while(_interval < 5000 || !stop);   
	return _interval;	
}

bool checkForSensor(int pin, int value){
	return(analogReadToDigital(pin)==value);
}

int analogReadToDigital(byte pin){
	short _in = analogRead(pin);
	if(_in>2.5){
		return 1;
	}
	return 0;
}

void startAndCalcPR(unsigned long startTime){
	int _interval=0;
  bool stop;
  while(_interval < 5000 && !stop){
		stop = checkForSensor(PR_IN, 0);
		_interval = millis() - startTime;
	} 
	BTSend(_interval)	;
}



//messageQueueImpl

void messageQueuePoll(){
  if(messageQueue[messageQueueIndex]!=-1){
    BTSend(messageQueue[messageQueueIndex]);
    messageQueueIndex++;
  }
}

void addToMessageQueue(short value){
  messageQueue[messageQueueIndex]=value;
  messageQueueIndex++;
  if(messageQueueIndex>255){
    messageQueueIndex=0;
  }
}

//initial setup
void waitForStart(){
  debugLog("Waiting for pressure");
  int _i = 0;
  while(_i-=1){
    boolean pressureValue = checkForSensor(PWR_IN, 1);
    if(pressureValue){
      _i=1;
    }
  }
  beep(300);
  beep(300);
  debugLog("Pressure found");
}

void initSetup(){
  debugLog("Setting up start");
  int _i = 0;
  while(_i-=1){
    boolean isPowerButton = checkForSensor(PWR_IN, 1);
    short bluetoothMessage = BTRead();
    if(isPowerButton || bluetoothMessage==C_BT_SETUP_CONFIRM){
      _i=1;
    }
  }
  debugLog("Finished setting up");
}

void initBluetooth(){
  debugLog("Setting up bluetooth");
  int _i = 0;
  while(_i-=1){
    boolean isPowerButton = checkForSensor(PWR_IN, 1);
    short bluetoothMessage = BTRead();
    if(isPowerButton || bluetoothMessage==C_BT_SETUP_BLUETOOTH){
      _i=1;
    }
  }
  debugLog("Finished setting up bluetooth");
  beep(1000);
}

void initMessageQueue(){
  for(int i=0;i!=messageQueueLength;i++){
    messageQueue[i]=-1;
  }
}

//Debug or Temp

void debugLog(String message){
  if(debug){
    Serial.print(message);
  }
}

