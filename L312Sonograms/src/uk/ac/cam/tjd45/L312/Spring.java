package uk.ac.cam.tjd45.L312;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Spring extends JPanel implements KeyListener {
    private char f = 'a';
    private int a = 0, b = 0, c = 0;
    private int width = 800/4;
    private int height = 600/3;
    private Color[][] colours = new Color[11][8];
    private Color[][] octaveColours = new Color[4][3];
    private ArrayList<String> pressedNotes = new ArrayList<String>();
    private HashMap<String,Integer[]> notesToCoord = new HashMap<String,Integer[]>();
    private HashMap<String,Color> noteColours = new HashMap<String,Color>();
    
    public Spring() {  //Constructor
        this.setPreferredSize(new Dimension(800, 600));
        resetColours();
        resetOctaveColours();
        addKeyListener(this);
        initialiseNoteCoords();
        initialiseNoteColours();
    }
    
    public void initialiseNoteCoords(){
    	Integer[] entry = new Integer[2];
    	entry[0]=0;
    	entry[1]=0;
    	notesToCoord.put("C", entry);
    	Integer[] entry2 = new Integer[2];
    	entry2[0]=1;
    	entry2[1]=0;
    	notesToCoord.put("C#", entry2);
    	Integer[] entry3 = new Integer[2];
    	entry3[0]=2;
    	entry3[1]=0;
    	notesToCoord.put("D", entry3);
    	Integer[] entry4 = new Integer[2];
    	entry4[0]=3;
    	entry4[1]=0;
    	notesToCoord.put("Eb", entry4);
    	Integer[] entry5 = new Integer[2];
    	entry5[0]=0;
    	entry5[1]=1;
    	notesToCoord.put("E", entry5);
    	Integer[] entry6 = new Integer[2];
    	entry6[0]=1;
    	entry6[1]=1;
    	notesToCoord.put("F", entry6);
    	Integer[] entry7 = new Integer[2];
    	entry7[0]=2;
    	entry7[1]=1;
    	notesToCoord.put("F#", entry7);
    	Integer[] entry8 = new Integer[2];
    	entry8[0]=3;
    	entry8[1]=1;
    	notesToCoord.put("G", entry8);
    	Integer[] entry9 = new Integer[2];
    	entry9[0]=0;
    	entry9[1]=2;
    	notesToCoord.put("G#", entry9);
    	Integer[] entry10 = new Integer[2];
    	entry10[0]=1;
    	entry10[1]=2;
    	notesToCoord.put("A", entry10);
    	Integer[] entry11 = new Integer[2];
    	entry11[0]=2;
    	entry11[1]=2;
    	notesToCoord.put("Bb", entry11);
    	Integer[] entry12 = new Integer[2];
    	entry12[0]=3;
    	entry12[1]=2;
    	notesToCoord.put("B", entry12);
    	
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
    
    public void resetColours(){
    	for(int i = 0; i<colours.length; i++){
        	for(int j = 0; j<colours[i].length; j++){
        		colours[i][j]=new Color(i*5,j*5,0);
        	}
        }
    }
    
    public void resetOctaveColours(){
    	for(int i = 0; i<octaveColours.length; i++){
    		for(int j = 0; j<octaveColours[i].length; j++){
    			octaveColours[i][j]=new Color(255,255,255);
    		}
    	}
    }

    public void resetNoteColour(int x, int y){
    	octaveColours[x][y]=new Color(255,255,255);
    }
    
    public void setOctaveColours(){
    	resetOctaveColours();
    	int x,y;
    	for (String curInstance: pressedNotes) {
    		x = notesToCoord.get(curInstance)[0];
    		y = notesToCoord.get(curInstance)[1];
    		octaveColours[x][y]=noteColours.get(curInstance);
    		System.out.println(curInstance);
    		System.out.println(x+","+y);
      	}
    }
    
    public void addNotify() { //the focus
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); //clear before next press
        setOctaveColours();
        int x = 0;
        int y = 0;
        
        for(int i=0; i<4; i++){
        	y=0;
        	for(int j = 0; j<3; j++){
        		g.setColor(octaveColours[i][j]);
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
        	if(pressedNotes.contains("C")){
        		pressedNotes.remove("C");
        	}else{
        		pressedNotes.add("C");
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