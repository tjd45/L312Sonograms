package uk.ac.cam.tjd45.L312;

import javax.swing.JFrame;

public class LiveGraphicsTest{
	public static void main(String[] s) { //main method, creates the frame
		JFrame f = new JFrame();
		f.getContentPane().add(new Spring());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}