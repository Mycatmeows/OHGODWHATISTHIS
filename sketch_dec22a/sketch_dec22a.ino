byte IR_IN = A0;
byte PR_IN = A1;
byte IR_EN = A2;
byte BP_EN = 10;
byte State = 255;
byte BTN_IN = A5;

short IR_AVG = 0;
short PR_AVG = 0;

void setup() {
  Serial.begin(9600);
}

void loop() {
  startupLoop();
  while(true){
    Serial.print("DONE");
  }
  
}

void startupLoop() {
  boolean start = false;
  while (!start) {
    int btn_in = 0;
    btn_in = analogRead(A5);
    if (btn_in == HIGH) break;
    int bt_in = 0;
    if (Serial.available() && Serial.read() == 0xF0)
      break;
  }
}



