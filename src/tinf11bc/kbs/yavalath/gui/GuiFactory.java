package tinf11bc.kbs.yavalath.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;


public class GuiFactory {
	public JFrame mRootFrame;
	
	
	public GuiFactory(){
		mRootFrame = new JFrame("Yavalath-Game !");
		mRootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mRootFrame.setSize(950,650);
		mRootFrame.setLocation(50,50);
		mRootFrame.setVisible(true);
		
		
		
		
		
		
	}
	
	
	public PlayGround createPlayField(){
		PlayGround playground = new PlayGround(mRootFrame);
		return playground;
	}
	
	

	
	

	
}
