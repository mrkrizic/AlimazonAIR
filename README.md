# AlimazonAIR
Alimazon AI&amp;Robotics


This is my implementation of the AI&amp;Robotics Subsystem for the Software Engineering Excerice.

The Java Program starts a Webservice located at http://localhost:8080/api/air/ as it's root.
To access methods, look up the mappings in the RobotController class.

For a nice overview of the API use the following URL: http://localhost:8080/swagger-ui.html#/robot-controller
This offers a clean overview of the robot API and gives predefined example data to try out each function.

To use this Program correctly you need:
A PC with Java and Git installed. Clone this repository on your machine.
A makeblock robot with a .ino file and the python script on a raspberry pi.

The Raspberry Pi and the Makeblock Board (In my case MegaPi/Arduino Mega M2560) should be connected 
with the adapter and per USB cable to transfer the .hex files. Additionally if motors or other power consuming
parts are being used for the .ino program, you will need to connect the Arduino Board with additional batteries.

To start the python script on boot type: sudo crontab -e and at the end of the file add following line:
@reboot sudo python /home/pi/Desktop/<pathToPythonFile>.py

Now, connect the Raspberry Pi and the Pc with the Java Program onto the same network and get both IP addresses.
In the warehousebot_program.py file, edit the two address variables so they match the current configuration.

My Testing Configuration:

Turn off the Raspberry Pi, connect the batteries and USB Cable to the Arduino board and turn it on.
Start the Java Program and wait until spring sets everything up. Once finished, power on the Raspberry Pi
and it should automatically register itself as a warehouse bot. (Check at http://localhost:8080/api/air/robots)

Once registered, you can use the API to modify information about the Robot or you can use the method scheduleTask
to pass the robot a task at a given warehouse. To avoid mistyping the warehouse id, just copy paste it from the robots
list. 
The Task is currently a string and does not affect the current Warehouse Bot program. Each Task can be performed by either 
a Drone or Warehouse Bot and has to be specified in the Task. Swagger provides templates for easy use.

Once the scheduleTask method is executed, it sends a request at http://<robotIP>/get_task/<taskString> if a Robot is available (Idle).
If successful, the Raspberry Pi calls avrdude and uploads the .ino file to the Arduino Board and the Program starts excecuting.

The other API calls have clear method names and should be self explanatory. The best way to play around with the API
is using Swagger, as it provides templates and information about each function.
