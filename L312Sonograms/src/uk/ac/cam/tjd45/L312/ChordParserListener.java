package uk.ac.cam.tjd45.L312;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.theory.Note;

public class ChordParserListener extends ParserListenerAdapter {
    List<Note> notes = new ArrayList<Note>();
    wholePiano wp = new wholePiano();
    
    public ChordParserListener() {
    	JFrame f = new JFrame();
    	
    	f.getContentPane().add(wp);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	f.pack();
    	f.setVisible(true);
    	
    	
    	
    }
    @Override public void onNotePressed(Note note) {
        notes.add(note);
        System.out.println(note.toStringWithoutDuration());
        String vel = note.getVelocityString();
        wp.notePressed(note.toStringWithoutDuration(),Integer.parseInt(vel.substring(1, vel.length())));
        
    }

    @Override public void onNoteReleased(Note note) {
    	//System.out.println(note.toString());
    	wp.noteReleased(note.toString());
        // Remove the note from the list, might not be as easy as notes.remove(note)
    }
    
    @Override public void onControllerEventParsed(byte a, byte b) {
    	//System.out.println(note.toString());
    	wp.setSustain(a, b);
        // Remove the note from the list, might not be as easy as notes.remove(note)
    }
}