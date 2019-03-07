package uk.ac.cam.tjd45.L312;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class wholePiano extends JPanel implements KeyListener, ActionListener {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double actualWidth = screenSize.getWidth();
	double actualHeight = screenSize.getHeight();
	private int screenHeight = 100;//(int) actualHeight - 70;
	private int screenWidth = 100;//(int) actualWidth - 100;
    private char f = 'a';
    private int a = 0, b = 0, c = 0;
    private int width = screenWidth/11;
    private int height = screenHeight/8;
    private Color[][] pianoColours = new Color[11][8];
    private int[][] pianoVolumes = new int[11][8];
    private ArrayList<String> pressedNotes = new ArrayList<String>();
    private ArrayList<String> sustainedNotes = new ArrayList<String>();
    private ArrayList<MyPair> hitNotes = new ArrayList<MyPair>();
    private HashMap<String,MyPair> notesToCoord = new HashMap<String,MyPair>();
    private HashMap<String,Integer> noteOrder = new HashMap<String,Integer>();
    private HashMap<String,Color> noteColours = new HashMap<String,Color>();
    private HashMap<String,Integer> noteVolume = new HashMap<String,Integer>();
    private HashMap<String,Long> noteOnset = new HashMap<String,Long>();
    private String[] octave = {"C","C#","D","Eb","E","F","F#","G","G#","A","Bb","B"};
    private boolean sustain = false;
    private int refreshRate = 1000;
    private final Timer timer = new Timer(refreshRate,  this);
    private float fadeGradient = 100/10000000000L;
    
    public wholePiano() {  //Constructor
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        resetPianoColours();
        addKeyListener(this);
        initialiseNoteCoords();
        initialiseNoteColours("G");
        noteToNumber("C7");
        timer.start();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	long curTime = System.nanoTime();
    	long duration = 0;
    	double sf = 10000000000L;
    	for(String st:pressedNotes){
    		int oldVolume = noteVolume.get(st);
    		long onset = noteOnset.get(st);
    	
    		int newVolume = calcNewVolume(oldVolume, curTime, onset);
    		
    		
    		System.out.println("note: "+st+" old volume: "+oldVolume+" new volume: "+newVolume);
    		noteVolume.put(st, newVolume);
    		//System.out.println(st+" initial volume: "+noteVolume.get(st)+" duration:"+duration/1000+" fade factor:"+fade + " new volume:"+newVolume);
    	}
    	for(String st:sustainedNotes){
    		int oldVolume = noteVolume.get(st);
    		long onset = noteOnset.get(st);
    		int newVolume = calcNewVolume(oldVolume, curTime, onset);
    		
    		noteVolume.put(st, newVolume);
    		//System.out.println(st+" initial volume: "+noteVolume.get(st)+" duration:"+duration/1000+" fade factor:"+fade + " new volume:"+newVolume);
    	}
        repaint();
    }
    
    public int noteToNumber(String wholeNote){
    	int octave = Integer.parseInt(wholeNote.substring(wholeNote.length()-1, wholeNote.length()));
    	String note = wholeNote.substring(0,wholeNote.length()-1);
    	int locNumber = noteOrder.get(note)+(octave*12);
    	return locNumber;
    }
    
    public int calcNewVolume(int oldVolume, long now, long onset){
    	long duration;
    	double sf = 0;
    	
    	duration = now - onset;
		double fade = (double) duration/sf;
		System.out.println(fadeGradient);
		int newVolume = (int) (oldVolume - (fadeGradient*refreshRate));
		newVolume = Math.max(0, newVolume);
    	
    	
    	return 0;
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
    			int loc = Math.min(noteToNumber(octave[j]+i),98);
    			int x = loc%11;
    			int y = 8-loc/11;
    			MyPair coords = new MyPair(x,y);
    			notesToCoord.put(octave[j]+i, coords);
    			System.out.println(octave[j]+i+","+x+","+y+","+loc);
    		}
    	}
    	
    	
    	System.out.println(notesToCoord.get("C0").key()+","+notesToCoord.get("C0").value());
    }
    
    public void setSustain(byte a, byte b) {
    	if(a==64) {
    		if(b==127) {
    			System.out.println("Sustain On");
    			sustain=true;
    		}else {
    			System.out.println("Sustain Off");
    			sustain=false;
    			sustainedNotes.clear();
    			//repaint();
    		}
    	}
    }
    
    public void initialiseNoteColours(String key){
    	
    	int index = -1;
    	for (int i=0;i<octave.length;i++) {
    		if (octave[i].equals(key)) {
    			index = i;
    			break;
    		}
    	}
    			
    	Color colour = new Color(54,253,50);
    	noteColours.put(octave[index], colour);
    	colour = new Color(99,254,90);
    	noteColours.put(octave[(index+7)%12], colour);
    	colour = new Color(111,252,111);
    	noteColours.put(octave[(index+4)%12], colour);
    	colour = new Color(186,252,80);
    	noteColours.put(octave[(index+11)%12], colour);
    	colour = new Color(232,252,67);
    	noteColours.put(octave[(index+9)%12], colour);
    	colour = new Color(246,233,67);
    	noteColours.put(octave[(index+2)%12], colour);
    	colour = new Color(247,196,89);
    	noteColours.put(octave[(index+5)%12], colour);
    	colour = new Color(245,165,50);
    	noteColours.put(octave[(index+10)%12], colour);
    	colour = new Color(243,121,60);
    	noteColours.put(octave[(index+3)%12], colour);
    	colour = new Color(242,84,39);
    	noteColours.put(octave[(index+8)%12], colour);
    	colour = new Color(242,58,34);
    	noteColours.put(octave[(index+1)%12], colour);
    	colour = new Color(239,14,29);
    	noteColours.put(octave[(index+6)%12], colour);

    }
    
    public void resetPianoColours(){
    	for(int i = 0; i<pianoColours.length; i++){
        	for(int j = 0; j<pianoColours[i].length; j++){
        		pianoColours[i][j]=new Color(255,255,255);
        	}
        }
    }
    
    public void resetPianoVolumes(){
    	for(int i = 0; i<pianoVolumes.length; i++){
        	for(int j = 0; j<pianoVolumes[i].length; j++){
        		pianoVolumes[i][j]=0 ;
        	}
        }
    }
    
    public void resetNoteColour(int x, int y){
    	pianoColours[x][y]=new Color(255,255,255);
    }
    
    public void setPianoColours(){
    	resetPianoColours();
    	hitNotes.clear();
    	int x,y;
    	for (String curInstance: pressedNotes) {
    
    		x = notesToCoord.get(curInstance).key();
    		y = notesToCoord.get(curInstance).value();
    		pianoColours[x][y]=noteColours.get(curInstance.substring(0,curInstance.length()-1));
    		
    		MyPair hit = new MyPair(x,y);
    		hitNotes.add(hit);
      	}
    	for (String curInstance: sustainedNotes) {
    		x = notesToCoord.get(curInstance).key();
    		y = notesToCoord.get(curInstance).value();
    		pianoColours[x][y]=noteColours.get(curInstance.substring(0,curInstance.length()-1));
    		System.out.println(curInstance);
    		System.out.println(x+","+y);
      	}
    }
    
    public void setPianoVolumes(){
    	resetPianoVolumes();
    	int x,y;
    	for (Map.Entry<String, Integer> entry : noteVolume.entrySet()) {
    		x = notesToCoord.get(entry.getKey()).key();
    		y = notesToCoord.get(entry.getKey()).value();
    		pianoVolumes[x][y]=entry.getValue();
		}
    }
    
    public void addNotify() { //the focus
        super.addNotify();
        requestFocus();
    }

    public void notePressed(String noteName, int vel){
    	pressedNotes.add(noteName);
    	noteVolume.put(noteName, vel);
    	noteOnset.put(noteName, System.nanoTime());
    	//repaint();
    }
    
    public void noteReleased(String noteName){
    	if(!sustain) {
    		pressedNotes.remove(noteName);
    		noteVolume.remove(noteName);
    		noteOnset.remove(noteName, System.nanoTime());
    	}else {
    		pressedNotes.remove(noteName);
    		sustainedNotes.add(noteName);
    	}
    	//repaint();
    }
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); //clear before next press
        setPianoColours();
        setPianoVolumes();
        int x = 0;
        int y = 0;
        
        for(int i=0; i<11; i++){
        	y=0;
        	for(int j = 0; j<8; j++){
        		Color color = new Color(0,0,0);
        		
        		g.setColor(pianoColours[i][j]);
        		int volume = pianoVolumes[i][j];
        		float ratio = (float) Math.min((float)volume/(float)100,1.0);
        		int newHeight = Math.round(height*ratio);
        		g.fillRect(x,y+((height-newHeight)/2),width,newHeight);
        		g.setColor(color);
        		
        		//g.drawRect(x,y,x+width,height);
        		y+=height;
        	}
        	x+=width;
        }
        g.setColor(Color.black);
        for(MyPair hit : hitNotes){
        	g.drawLine(hit.key()*width, hit.value()*height+(height/2), (hit.key()*width)+width, hit.value()*height+(height/2));
        	
        }
        
    }

    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) { //when key is typed, do something
        f = e.getKeyChar(); //modify value of f
        switch (f){
        case 'a': 
        	if(pressedNotes.contains("A")){
        		notePressed("A5",100);
        	}else{
        		notePressed("A5",100);
        	}
        	
            break;
        case 'b': 
        	if(pressedNotes.contains("B")){
        		notePressed("B5",100);
        	}else{
        		notePressed("B5",100);
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