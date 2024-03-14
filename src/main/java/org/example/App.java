package org.example;

import gnu.io.*;

import java.io.OutputStream;
import java.util.Enumeration;

public class App {
    public static void main( String[] args ) {

       CommPortIdentifier commPortIdentifier = new App().find();

       new App().start(commPortIdentifier);
    }

    CommPortIdentifier find(){
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();

        CommPortIdentifier commPortIdentifier = null;

        while(portList.hasMoreElements()){
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
                System.out.println("PORT: " + portId.getName());
            }
            if (portId.getName().equals("COM4")){
                commPortIdentifier = portId;
            }
        }

        return commPortIdentifier;
    }

    void start(CommPortIdentifier portIdentifier) {
        try{
            SerialPort serialPort = (SerialPort) portIdentifier.open("WatchdogTimerControl", 10000);

            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            OutputStream out = serialPort.getOutputStream();
            out.write("SET_TIMEOUT=10".getBytes());

            serialPort.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }


    }
}
