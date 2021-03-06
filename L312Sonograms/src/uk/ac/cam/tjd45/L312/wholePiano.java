package uk.ac.cam.tjd45.L312;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

class wholePiano extends JPanel implements KeyListener {
    private char f = 'a';
    private int a = 0, b = 0, c = 0;
    private int width = 880/11;
    private int height = 640/8;
    private Color[][] pianoColours = new Color[11][8];
    private ArrayList<String> pressedNotes = new ArrayList<String>();
    private HashMap<String,MyPair> notesToCoord = new HashMap<String,MyPair>();
    private HashMap<String,Integer> noteOrder = new HashMap<String,Integer>();
    private HashMap<String,Color> noteColours = new HashMap<String,Color>();
    private String[] octave = {"C","C#","D","Eb","E","F","F#","G","G#","A","Bb","B"};
    
    public wholePiano() {  //Constructor
        this.setPreferredSize(new Dimension(880, 640));
        resetPianoColours();
        addKeyListener(this);
        initialiseNoteCoords();
        initialiseNoteColours();
        noteToNumber("C7");
    }
    
    public int noteToNumber(String wholeNote){
    	int octave = Integer.parseInt(wholeNote.substring(wholeNote.length()-1, wholeNote.length()));
    	String note = wholeNote.substring(0,wholeNote.length()-1);
    	int locNumber = noteOrder.get(note)+(octave*12);
    	return locNumber;
    }
    
    public void initialiseNoteCoords(){
    	noteOrder.put("C", 0);
    	noteOrder.put("C#", 1);
    	noteOrder.put("D", 2);
    	noteOrder.put("Eb", 3);
    	noteOrder.put("E", 4);
    	noteOrder.put("F", 5);
    	noteOrder.put("F#", 6);
    	noteOrder.put("G", 7);
    	noteOrder.put("G#", 8);
    	noteOrder.put("A", 9);
    	noteOrder.put("Bb", 10);
    	noteOrder.put("B", 11);
    	
    	for(int i = 0; i< 8; i++){
    		for(int j = 0; j<octave.length; j++){
    			int loc = Math.min(noteToNumber(octave[j]+i),87);
    			int x = loc%11;
    			int y = loc/11;
    			MyPair coords = new MyPair(x,y);
    			notesToCoord.put(octave[j]+i, coords);
    		}
    	}
    	
//    	Integer[] entry = new Integer[2];
//    	entry[0]=6;
//    	entry[1]=3;
//    	notesToCoord.put("C", entry);
//    	Integer[] entry2 = new Integer[2];
//    	entry2[0]=7;
//    	entry2[1]=3;
//    	notesToCoord.put("C#", entry2);
//    	Integer[] entry3 = new Integer[2];
//    	entry3[0]=8;
//    	entry3[1]=3;
//    	notesToCoord.put("D", entry3);
//    	Integer[] entry4 = new Integer[2];
//    	entry4[0]=9;
//    	entry4[1]=3;
//    	notesToCoord.put("Eb", entry4);
//    	Integer[] entry5 = new Integer[2];
//    	entry5[0]=10;
//    	entry5[1]=3;
//    	notesToCoord.put("E", entry5);
//    	Integer[] entry6 = new Integer[2];
//    	entry6[0]=0;
//    	entry6[1]=4;
//    	notesToCoord.put("F", entry6);
//    	Integer[] entry7 = new Integer[2];
//    	entry7[0]=1;
//    	entry7[1]=4;
//    	notesToCoord.put("F#", entry7);
//    	Integer[] entry8 = new Integer[2];
//    	entry8[0]=2;
//    	entry8[1]=4;
//    	notesToCoord.put("G", entry8);
//    	Integer[] entry9 = new Integer[2];
//    	entry9[0]=3;
//    	entry9[1]=4;
//    	notesToCoord.put("G#", entry9);
//    	Integer[] entry10 = new Integer[2];
//    	entry10[0]=3;
//    	entry10[1]=3;
//    	notesToCoord.put("A", entry10);
//    	Integer[] entry11 = new Integer[2];
//    	entry11[0]=4;
//    	entry11[1]=3;
//    	notesToCoord.put("Bb", entry11);
//    	Integer[] entry12 = new Integer[2];
//    	entry12[0]=5;
//    	entry12[1]=3;
//    	notesToCoord.put("B", entry12);
    	
    }
    
    public void initialiseNoteColours(){
    	Color colour = new Color(54,253,50);
    	noteColours.put("C", colour);
    	colour = new Color(99,254,90);
    	noteColours.put("G", colour);
    	colour = new Color(111,252,111);
    	noteColours.put("E", colour);
    	colour = new Color(186,252,80);
    	noteColours.put("B", colour);
    	colour = new Color(232,252,67);
    	noteColours.put("A", colour);
    	colour = new Color(246,233,67);
    	noteColours.put("D", colour);
    	colour = new Color(247,196,89);
    	noteColours.put("F", colour);
    	colour = new Color(245,165,50);
    	noteColours.put("Bb", colour);
    	colour = new Color(243,121,60);
    	noteColours.put("Eb", colour);
    	colour = new Color(242,84,39);
    	noteColours.put("G#", colour);
    	colour = new Color(242,58,34);
    	noteColours.put("C#", colour);
    	colour = new Color(239,14,29);
    	noteColours.put("F#", colour);
    	
    }
    
    public void resetPianoColours(){
    	for(int i = 0; i<pianoColours.length; i++){
        	for(int j = 0; j<pianoColours[i].length; j++){
        		pianoColours[i][j]=new Color(255,255,255);
        	}
        }
    }
    
    public void resetNoteColour(int x, int y){
    	pianoColours[x][y]=new Color(255,255,255);
    }
    
    public void setPianoColours(){
    	resetPianoColours();
    	int x,y;
    	for (String curInstance: pressedNotes) {
    		x = notesToCoord.get(curInstance).key();
    		y = notesToCoord.get(curInstance).value();
    		pianoColours[x][y]=noteColours.get(curInstance.substring(0,curInstance.length()-1));
    		System.out.println(curInstance);
    		System.out.println(x+","+y);
      	}
    }
    
    public void addNotify() { //the focus
        super.addNotify();
        requestFocus();
    }

    public void notePressed(String noteName){
    	pressedNotes.add(noteName);
    	repaint();
    }
    
    public void noteReleased(String noteName){
    	pressedNotes.remove(noteName);
    	repaint();
    }
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); //clear before next press
        setPianoColours();
        int x = 0;
        int y = 0;
        
        for(int i=0; i<11; i++){
        	y=0;
        	for(int j = 0; j<8; j++){
        		g.setColor(pianoColours[i][j]);
        		g.fillRect(x,y,x+width,y+height);
        		y+=height;
        	}
        	x+=width;
        }
        
    }

    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) { //when key is typed, do something
        f = e.getKeyChar(); //modify value of f
        switch (f){
        case 'a': 
        	if(pressedNotes.contains("A")){
        		pressedNotes.remove("A");
        	}else{
        		pressedNotes.add("A");
        	}
        	
            break;
        case 'b': 
        	if(pressedNotes.contains("B")){
        		pressedNotes.remove("B");
        	}else{
        		pressedNotes.add("B");
        	}
        	
            break;
        case 'c': 
        	if(pressedNotes.contains("C5")){
        		pressedNotes.remove("C5");
        	}else{
        		pressedNotes.add("C5");
        	}
        	
            break;
        case 'e': 
        	if(pressedNotes.contains("E")){
        		pressedNotes.remove("E");
        	}else{
        		pressedNotes.add("E");
        	}
        	
            break;
        case 'g': 
        	if(pressedNotes.contains("G")){
        		pressedNotes.remove("G");
        	}else{
        		pressedNotes.add("G");
        	}
        	
            break;
        case 'd': 
        	if(pressedNotes.contains("D")){
        		pressedNotes.remove("D");
        	}else{
        		pressedNotes.add("D");
        	}
        	
            break;
        case 'f': 
        	if(pressedNotes.contains("F")){
        		pressedNotes.remove("F");
        	}else{
        		pressedNotes.add("F");
        	}
        	
            break;
        case 'F': 
        	if(pressedNotes.contains("F#")){
        		pressedNotes.remove("F#");
        	}else{
        		pressedNotes.add("F#");
        	}
        	
            break;
        case 'C': 
        	if(pressedNotes.contains("C#")){
        		pressedNotes.remove("C#");
        	}else{
        		pressedNotes.add("C#");
        	}
        	
            break;
        case 'G': 
        	if(pressedNotes.contains("G#")){
        		pressedNotes.remove("G#");
        	}else{
        		pressedNotes.add("G#");
        	}
        	
            break;
        case 'A': 
        	if(pressedNotes.contains("Bb")){
        		pressedNotes.remove("Bb");
        	}else{
        		pressedNotes.add("Bb");
        	}
        	
            break;
        case 'D': 
        	if(pressedNotes.contains("Eb")){
        		pressedNotes.remove("Eb");
        	}else{
        		pressedNotes.add("Eb");
        	}
        	
            break;
        }
        repaint();
    }


}