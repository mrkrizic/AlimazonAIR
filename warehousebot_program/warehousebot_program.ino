
/**
 * @file warehousebot_program.ino
 * @author Mario Krizic
 * 
 * function list:
 * void isr_process_encoder1(void);
 * void isr_process_encoder2(void);
 * void isr_process_encoder4(void);
 * void move(int direction, int speed);
 * void setup();
 * void loop();
 * void wait_seconds(float seconds);
 * void loop_motors();
 * 
*/

#include"MeMegaPi.h"

/*Encoder Motors 
  Slot 1 and 2 have the motors for moving the robot 
  Slot 3 has the motor for opening and closing the robotic arm*/
MeEncoderOnBoard Encoder_1(SLOT1);
MeEncoderOnBoard Encoder_2(SLOT2);
MeEncoderOnBoard Encoder_4(SLOT4);

/*Ultrasonic sensor on port 8*/
MeUltrasonicSensor ultrasonic_8(8);

/*Interrupt service routine for encoder 1*/
void isr_process_encoder1(void)
{
      if(digitalRead(Encoder_1.getPortB()) == 0){
            Encoder_1.pulsePosMinus();
      }else{
            Encoder_1.pulsePosPlus();
      }
}

/*Interrupt service routine for encoder 2*/
void isr_process_encoder2(void)
{
      if(digitalRead(Encoder_2.getPortB()) == 0){
            Encoder_2.pulsePosMinus();
      }else{
            Encoder_2.pulsePosPlus();
      }
}

/*Interrupt service routine for encoder 4*/
void isr_process_encoder4(void)
{
      if(digitalRead(Encoder_4.getPortB()) == 0){
            Encoder_4.pulsePosMinus();
      }else{
            Encoder_4.pulsePosPlus();
      }
}

/*Method to simplify moving
 1 -> Move Forward
 2 -> Move Backwards
 3 -> Turn Left
 4 -> Turn Right*/

void move(int direction, int speed)
{
      int leftSpeed = 0;
      int rightSpeed = 0;
      if(direction == 1){
            leftSpeed = -speed;
            rightSpeed = speed;
      }else if(direction == 2){
            leftSpeed = speed;
            rightSpeed = -speed;
      }else if(direction == 3){
            leftSpeed = speed;
            rightSpeed = speed;
      }else if(direction == 4){
            leftSpeed = -speed;
            rightSpeed = -speed;
      }
      Encoder_1.setTarPWM(rightSpeed);
      Encoder_2.setTarPWM(leftSpeed);
}



/*Setup function 
Programs for warehouse bots should have all their functionality defined here*/
void setup()
{
    //Set Pwm 8KHz - Necessary for MegaPi programs
    TCCR1A = _BV(WGM10);
    TCCR1B = _BV(CS11) | _BV(WGM12);
    TCCR2A = _BV(WGM21) | _BV(WGM20);
    TCCR2B = _BV(CS21);

    /*Attach Interrupts on the active encoders*/
    attachInterrupt(Encoder_1.getIntNum(), isr_process_encoder1, RISING);
    attachInterrupt(Encoder_2.getIntNum(), isr_process_encoder2, RISING);
    attachInterrupt(Encoder_4.getIntNum(), isr_process_encoder4, RISING);
    /*Actual Program*/
    move(1,80);
    while(!((ultrasonic_8.distanceCm()) < (9)))
    {
        loop_motors();
    }
    move(1,0);
    Encoder_4.setTarPWM(-50);
    wait_seconds(4);
    Encoder_4.setTarPWM(0);
    wait_seconds(1);
    move(2,80);
    wait_seconds(2);
    move(1,0);
    Encoder_4.setTarPWM(50);
    wait_seconds(4);
    Encoder_4.setTarPWM(0);
    
}

/*Loop Program
Should not contain any actual code as a new program should
get uploaded when this one finishes*/
void loop()
{
    loop_motors(); /*prevents arm from opnening itself until it breaks*/
}

/*custom delay method to keep motors running while waiting*/
void wait_seconds(float seconds)
{
    long endTime = millis() + seconds * 1000;
    while(millis() < endTime)loop_motors();
}

/*custom loop program to simulate multitasking*/
void loop_motors()
{
    Encoder_1.loop();
    Encoder_2.loop();
    
    Encoder_4.loop();
    
    
}
