package tinf11bc.kbs.yavalath.gui;

import javax.swing.JFrame;

public class GuiFactory {
	public JFrame mRootFrame;
	
	
	public GuiFactory(){
		mRootFrame = new JFrame("Yavalath-Game !");
		mRootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mRootFrame.setSize(950,550);
		mRootFrame.setLocation(50,50);
		mRootFrame.setVisible(true);
		
		createPlayField();
		
		
	}
	
	
	public void createPlayField(){
		PlayGround playground = new PlayGround(mRootFrame);
	}
	

	
}
