package tinf11bc.kbs.yavalath.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import tinf11bc.kbs.yavalath.util.Settings;



public class SettingsGui {

	private final Settings mSettings;
	
	
	public SettingsGui(Settings settings){
		mSettings = settings;
		
		String choices[] = {"Player", "RandomAI", "UTCAI", "None"};
		final JComboBox playerStylePlayer1 = new JComboBox(choices);
		final JComboBox playerStylePlayer2 = new JComboBox(choices);
		final JComboBox playerStylePlayer3 = new JComboBox(choices);
		
		
		
		
		final JFrame settingsFrame = new JFrame("Settings");
		
		settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settingsFrame.setSize(300,300);
		settingsFrame.setLocation(100, 100);
		
		JButton submitButton = new JButton("Submit");
		
		settingsFrame.setLayout( new GridLayout(5, 4, 10, 10) );

		
		settingsFrame.add( new JLabel("Settings") );
		settingsFrame.add( new JLabel("") );

		
		
		
		settingsFrame.add( new JLabel("Player 1 :") );
		settingsFrame.add( playerStylePlayer1);
		
		settingsFrame.add( new JLabel("Player 2 :") );
		settingsFrame.add( playerStylePlayer2);
		
		settingsFrame.add( new JLabel("Player 3 :") );
		settingsFrame.add( playerStylePlayer3);
		
		
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
	
}
