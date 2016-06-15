
byte IR_IN = A0;
byte IR_EN = 2;
byte PR_IN = A1;
byte PR_EN = A2;
byte BP_OUT= 10;
byte State = 255;

short IR_AVG = 0;
short PR_AVG = 0;


void setup() {
  Serial.begin(9600);
  //Set to config IR
  State=2;
  _pinConfig();
  //Calibrate_Sensor(IR_IN);
  //Calibrate_Sensor(PR_IN);

  Calibrate_Sensor(IR_IN);
  
  State=0;
  delayUntil(0xFF);

  Serial.print("Received ack from mbl");
  Serial.print("Waiting on start");

  delayUntil(0xF1);

   Serial.print("Received start command, starting ");

  _startAndCalcIR(millis());
  //TODO:Actually start
  
  
}

int Calibrate_Sensor(byte id){
  short avg =0;
  short _i=0;
  byte target=_mapSensorToVar(id);  
  while (_i < 512){
    short _in;
    _in = analogRead(target);    
    avg = avg + _in;
    if(_i > 0){
      avg = avg / 2;
    }
    _i = _i +1;
  }
  
  return avg;     
}

void loop() {
	byte _newState = _pollChanges();
	if (_newState != State){
		State=_newState;
		_pinConfig();
	}
	_stateBasedAction();

}

byte _pollChanges(){
	int _in = -1;
	if(Serial.available()){
		
	}
	
	return _in;
}

byte _mapSensorToVar(byte id){
  byte t;
  if(id == PR_IN){
	digitalWrite(PR_EN, HIGH);
    t=PR_IN;  
  }
  else if (id == IR_IN){
    t=IR_IN;
	digitalWrite(IR_EN, HIGH);
  }
  return t;
}

void _pinConfig(){
		if(State==255 || State == 0){
			pinMode(IR_IN, INPUT);
			pinMode(PR_IN, INPUT);
			pinMode(IR_EN, OUTPUT);
			digitalWrite(IR_EN, LOW);	
			digitalWrite(PR_EN, LOW);
			State=0;
		}
		if(State==1){
			digitalWrite(PR_EN, HIGH);
		}
		if(State==2){
			digitalWrite(IR_EN, HIGH);
			digitalWrite(PR_EN, LOW);
		}
		
}

byte BTRead(){
	return Serial.read();
}

void BTSend(unsigned long data){
	if (Serial.availableForWrite()>0){
		Serial.write(data);
	}
}

boolean _isBTAvailable(){
	return Serial.available()>0;
}


void _stateBasedAction(){
	if(State==0){
		if (_isBTAvailable()){
			byte _in = BTRead();
			if(_in>0){
				State=1;
				_pinConfig();
				BTSend(State);
				return;
			}
		}
	}

	
}

void _beep(){
	BP_OUT=1;
	delay(1);
}

void _startCountDown(){
	_beep();
	_beep();
	_beep();
  unsigned long _m = millis();	
	BTSend(_m);
 _m = calcReactionTime(millis());
	BTSend(_m);
		
	State=2;
	_pinConfig();
}

int calcReactionTime(unsigned long startTime){
	int _interval=0;
	bool stop=false;
	do{
		stop = checkForSensor(PR_IN, 0);
		_interval = millis() - startTime;
	}
  while(_interval < 5000 && !stop);
	return _interval;	
}

boolean checkForSensor(int pin, int value){
	return(analogReadToDigital(pin)==value);
}

bool analogReadToDigital(byte pin){
	short _in = analogRead(pin);
	if(_in>2.5){
		return 1;
	}
	return 0;
}

void _startAndCalcIR(unsigned long startTime){
  int _interval;
  bool stop;
	do{
		stop = checkForSensor(IR_IN, 0);
		_interval = millis() - startTime;
	}
  while(_interval < 5000 && !stop);
  if(_interval > 5000){
    Serial.print("Went to timeout");
  }
	BTSend(_interval);	
}

bool delayUntil(byte trgt){
  int _msg = 0x00;
  while(_msg!=trgt){    
    delay(10);
    _msg = BTRead();
  }
  return true;
}


