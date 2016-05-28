
byte IR_IN = A0;
byte IR_EN = 2;
byte PR_IN = A1;
byte BP_OUT= 10;
byte State = 255;

short IR_AVG = 0;
short PR_AVG = 0;


void setup() {
  Serial.begin(9600);
  _pinConfig();
  Calibrate_Sensor(IR_IN);
  Calibrate_Sensor(PR_IN);
}

int Calibrate_Sensor(byte id){
  short avg =0;
  short _i=0;
  int _i=0;
  byte target=_mapSensorToVar(id);  
  while (_i < 512){
    
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
	_newState = _pollChanges();
	if (_newState != State){
		State=_newState();
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
	DigitalWrite(PR_EN, HIGH);
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
	return Serial.read()
}

void BTSend(byte data){
	if (Serial.availableForWrite()>0){
		Serial.write(data);
	}
}

boolean _isBTAvailable(){
	return Serial.available()>0
}


void _stateBasedAction(){
	if(State==0){
		if (_isBTAvailable()){
			byte _in = BTRead()
			if(_in()>0){
				State=1;
				_pinConfig();
				BTSend(State);
				return;
			}
		}
	}
	if(State==1){
		_startCountDownAndStart();
		
	}
	if(State==2){
		_startAndCalcIR();
		State=0;
	}
	
}

void _beep(){
	BP_OUT=1;
	sleep(1);
}

void _startCountDown(){
	_beep();
	_beep();
	_beep();	
	BTSend(milis);
	int _t = calcReactionTime(milis());
	BTSend(t);	
	State=2;
	pinConfig();
}

int calcReactionTime(unsigned long startTime){
	int _interval=0;
	bool stop;
	do{
		stop = checkForSensor(PR_IN, 0);
		interval = milis() - startTime;
		while(_interval < 5000 && !stop)
	}
	return _interval	
}

void checkForSensor(int pin, int value){
	return(analogReadToDigital(pin)==value)
}

bool analogReadToDigital(byte pin){
	short _in = analogRead(pin);
	if(_in>2.5){
		return 1;
	}
	return 0;
}

void _startAndCalcIR(){
	do{
		stop = checkForSensor(IR_IN, 0);
		interval = milis() - startTime;
		while(_interval < 5000 && !stop)
	}
	BTSend(_interval)	
}


