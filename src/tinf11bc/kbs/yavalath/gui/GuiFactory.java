package tinf11bc.kbs.yavalath.gui;

import javax.swing.JFrame;

public class GuiFactory {
	public JFrame mRootFrame;
	
	
	public GuiFactory(){
		createRootFrame();
		
	}
	
	
	public void createRootFrame(){
		mRootFrame = new JFrame("Yavalath-Game !");
		
		mRootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mRootFrame.setSize(500,500);
		mRootFrame.setLocation(50,50);
		mRootFrame.setVisible(true);
	}
	
}
