import flask
import requests
import os
import time
import socket

app = flask.Flask(__name__)
ipv4_address = "192.168.0.31"
pc_address = "192.168.0.11"

#@app.route("/test", methods=["GET"]) Used to test connection manually
#def home():
#    return "Test passed!"
    
@app.route('/get_task/<task>', methods=["GET"])
def get_task(task):
    print(task)
    os.system("/home/pi/Desktop/arduino-1.8.8/hardware/tools/avr/bin/avrdude "+
          "-C/home/pi/Desktop/arduino-1.8.8/hardware/tools/avr/etc/avrdude.conf "+
          "-v -patmega2560 -cwiring -P/dev/ttyUSB0 -b115200 -D "+
          "-Uflash:w:/tmp/arduino_build_89723/warehousebot_program.ino.hex:i ")
    time.sleep(10) #Timeout simulation for testing robot status
    return "Task completed"

if __name__=='__main__':
    r = requests.post("http://"+pc_address+":8080/api/air/create/warehouseBot",
                  data={"capacity":5.0,
                        "ipAddress":ipv4_address,
                        "location":"Linz",
                        "robotType":"WAREHOUSE_BOT",
                        "status":"IDLE",
                        "warehouseId": "LNZ42"
                  })
    print(r)
    app.run("0.0.0.0", port=5000)
    
    