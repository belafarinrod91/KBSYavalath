package tinf11bc.kbs.yavalath.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tinf11bc.kbs.yavalath.util.Settings;



public class SettingsGui {

	private final Settings mSettings;
	
	private JSlider mDifficultyUtcAi;

	
	
	public SettingsGui(Settings settings){
		mSettings = settings;
		
		String choices[] = {"Player", "RandomAI", "UTCAI", "None"};
		final JComboBox playerStylePlayer1 = new JComboBox(choices);
		final JComboBox playerStylePlayer2 = new JComboBox(choices);
		final JComboBox playerStylePlayer3 = new JComboBox(choices);
		
		
		mDifficultyUtcAi = new JSlider( 1, 3, 2 );
		mDifficultyUtcAi.setPaintTicks( true );
		
		mDifficultyUtcAi.setMajorTickSpacing(1);
		mDifficultyUtcAi.setMinorTickSpacing(1);
		mDifficultyUtcAi.setPaintTicks(true);
		mDifficultyUtcAi.setPaintLabels(true);
		
		
		
		
		
		
		
		int player1 = mSettings.getPlayerInformation()[0];
		int player2 = mSettings.getPlayerInformation()[1];
		int player3 = mSettings.getPlayerInformation()[2];
		
		
		
		playerStylePlayer1.setSelectedItem(returnStyleForInt(player1));
		playerStylePlayer2.setSelectedItem(returnStyleForInt(player2));
		playerStylePlayer3.setSelectedItem(returnStyleForInt(player3));
		mDifficultyUtcAi.setValue(getSettingForValue(mSettings.getDifficultyUTCAi()));
		
		
		final JFrame settingsFrame = new JFrame("Settings");
		
		settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settingsFrame.setSize(300,300);
		settingsFrame.setLocation(100, 100);
		
		JButton submitButton = new JButton("Submit");
		
		settingsFrame.setLayout( new GridLayout(7, 0 , 10, 10) );

		
		settingsFrame.add( new JLabel("Settings") );
		settingsFrame.add( new JLabel("") );

		
		
		
		settingsFrame.add( new JLabel("Player 1 :") );
		settingsFrame.add( playerStylePlayer1);
		
		settingsFrame.add( new JLabel("Player 2 :") );
		settingsFrame.add( playerStylePlayer2);
		
		settingsFrame.add( new JLabel("Player 3 :") );
		settingsFrame.add( playerStylePlayer3);
		
		settingsFrame.add( new JLabel("") );
		settingsFrame.add( new JLabel("") );
		
		settingsFrame.add( new JLabel("<html><body>Difficulty UTC AI<br>(1 = easy, 3 = hard)</body></html>") );
		settingsFrame.add( mDifficultyUtcAi );
		

		
		settingsFrame.add( new JLabel("") );
		settingsFrame.add(submitButton);

		settingsFrame.pack();
		
		
		ActionListener al = new ActionListener() {
		      public void actionPerformed( ActionEvent e ) {

		    	int player1 = returnIntForPlayerStyle(playerStylePlayer1.getSelectedItem());
		    	int player2 = returnIntForPlayerStyle(playerStylePlayer2.getSelectedItem());
		    	int player3 = returnIntForPlayerStyle(playerStylePlayer3.getSelectedItem());
		    	
		    	int[] result = {player1, player2, player3};	 
		    	
		    	if(checkIfResultArrayIsValid(result)){
		    		
		    		
		    		mSettings.setPlayerInformation(result);

		    		int difficulty = mDifficultyUtcAi.getValue();
		    		mSettings.setDifficultyUTCAi(difficulty);
		    		settingsFrame.dispose(); 
		    	}
		    	
		    	else {
		    		showWarning();
		    	}
		    	
		    	
		      }
		    	
		    };
		    submitButton.addActionListener(al);
		
		
		
		
        settingsFrame.setVisible(true);
	}
	
	
	public void showWarning(){
		 JOptionPane.showMessageDialog(null,
                 "This Game needs at least two players.",
                 "Not a valid choice",                                       
                 JOptionPane.WARNING_MESSAGE);
	}
	
	
	private boolean checkIfResultArrayIsValid(int[] style){
		boolean result = true; 
		
		int one = style[0];
		int two = style[1];
		int three = style[2];
		
		
		
		if(one == 0 && two == 0){
			result = false;
		}
		if(one == 0 && three == 0){
			result = false;
		}
		if(two == 0 && three == 0){
			result = false;
		}
		
		return result;
	}
	

	
	private int returnIntForPlayerStyle(Object style){
		int result = 0; 
		
		if(style.equals("Player")){
			result = 1;
		}
		if(style.equals("RandomAI")){
			result = 2;
		}
		if(style.equals("UTCAI")){
			result = 3;
		}
		if(style.equals("None")){
			result = 0;
		}
		
		return result;
	}
	
	private Object returnStyleForInt(int number){
		String result = "";
		
		switch(number){
			case 0 :
				result = "None";
				break;
			case 1: 
				result = "Player";
				break;
			case 2:
				result = "RandomAI";
				break;
			case 3 :
				result = "UTCAI";
				break;
		}
		return result;
		
	}

	
	private int getSettingForValue(int n){
		int result = 1;
		
		switch(n){
			case 1000:
				result = 1;
				break;
			case 5000:
				result = 2;
				break;
			case 50000:
				result = 3;
				break;
		}
		
		
		return result; 
	}
	
	
	
	
}



