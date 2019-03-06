package uk.ac.cam.tjd45.L312;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;

import org.jfugue.devices.MusicTransmitterToParserListener;

public class Sandbox {
	public static void main(String[] args){
	// You'll need some try/catches around this block. This is traditional Java Midi code.
		MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();
		if (devices.length == 0) {
		    System.out.println("No MIDI devices found");
		} else {
		    for (MidiDevice.Info dev : devices) {
		        System.out.println(dev);
		    }
		}
	System.out.println();
	MidiDevice device;
	try {
		System.out.println(devices[1]);
		device = MidiSystem.getMidiDevice(devices[1]);
	

	
	// Here comes the JFugue code
	MusicTransmitterToParserListener m = new MusicTransmitterToParserListener(device);
	m.addParserListener(new ChordParserListener());
	
	boolean done = false;
	int i = 0;
	// Choose either this option:
	m.startListening();

	
	
	while(!done){
		if(i>100000){
			done = true;
			
		}
		
	}
	
	m.stopListening();
//	m.startListening();
//	...do stuff...
//	m.stopListening();

	// Or choose this option:
	//m.listenForMillis(5000); // Listen for 5000 milliseconds (5 seconds)
	
	System.out.println("done");
	
	} catch (MidiUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} // You'll have to get the right device for your MIDI controller. 

	}

	

}
