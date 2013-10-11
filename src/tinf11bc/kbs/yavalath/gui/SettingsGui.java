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

import tinf11bc.kbs.yavalath.logic.player.Player;
import tinf11bc.kbs.yavalath.util.Settings;

public class SettingsGui {

	private final Settings mSettings;
	final JFrame settingsFrame;

	private JSlider mDifficultyUtcAiOne;
	private JSlider mDifficultyUtcAiTwo;
	private JSlider mDifficultyUtcAiThree;
	
	private boolean isActivatedOne;
	private boolean isActivatedTwo;
	private boolean isActivatedThree;
	
	
	
	final JComboBox mPlayerStylePlayer1;
	final JComboBox mPlayerStylePlayer2;
	final JComboBox mPlayerStylePlayer3;
	
	
	

	public SettingsGui(Settings settings) {
		mSettings = settings;

		String choices[] = { "Player", "RandomAI", "UTCAI"};
		String choices2[] = { "Player", "RandomAI", "UTCAI", "None" };
		mPlayerStylePlayer1 = new JComboBox(choices);
		mPlayerStylePlayer2 = new JComboBox(choices);
		mPlayerStylePlayer3 = new JComboBox(choices2);

		mDifficultyUtcAiOne = new JSlider(1, 3, 2);
		mDifficultyUtcAiOne.setPaintTicks(true);
		mDifficultyUtcAiOne.setMajorTickSpacing(1);
		mDifficultyUtcAiOne.setMinorTickSpacing(1);
		mDifficultyUtcAiOne.setPaintTicks(true);
		mDifficultyUtcAiOne.setPaintLabels(true);

		mDifficultyUtcAiTwo = new JSlider(1, 3, 2);
		mDifficultyUtcAiTwo.setPaintTicks(true);
		mDifficultyUtcAiTwo.setMajorTickSpacing(1);
		mDifficultyUtcAiTwo.setMinorTickSpacing(1);
		mDifficultyUtcAiTwo.setPaintTicks(true);
		mDifficultyUtcAiTwo.setPaintLabels(true);

		mDifficultyUtcAiThree = new JSlider(1, 3, 2);
		mDifficultyUtcAiThree.setPaintTicks(true);
		mDifficultyUtcAiThree.setMajorTickSpacing(1);
		mDifficultyUtcAiThree.setMinorTickSpacing(1);
		mDifficultyUtcAiThree.setPaintTicks(true);
		mDifficultyUtcAiThree.setPaintLabels(true);

		int player1 = mSettings.getPlayerInformation()[0];
		int player2 = mSettings.getPlayerInformation()[1];
		int player3 = mSettings.getPlayerInformation()[2];

		mPlayerStylePlayer1.setSelectedItem(returnStyleForInt(player1));
		mPlayerStylePlayer2.setSelectedItem(returnStyleForInt(player2));
		mPlayerStylePlayer3.setSelectedItem(returnStyleForInt(player3));

		mDifficultyUtcAiOne.setValue(getSettingForValue(mSettings
				.getDifficultyUTCAiOne()));
		mDifficultyUtcAiTwo.setValue(getSettingForValue(mSettings
				.getDifficultyUTCAiTwo()));
		mDifficultyUtcAiThree.setValue(getSettingForValue(mSettings
				.getDifficultyUTCAiThree()));

		initJSlider();

		settingsFrame = new JFrame("Settings");

		settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settingsFrame.setSize(300, 300);
		settingsFrame.setLocation(100, 100);

		JButton submitButton = new JButton("Submit");

		settingsFrame.setLayout(new GridLayout(10, 0, 10, 10));

		settingsFrame.add(new JLabel("Settings"));
		settingsFrame.add(new JLabel(""));

		settingsFrame.add(new JLabel("Player 1 :"));
		settingsFrame.add(mPlayerStylePlayer1);
		settingsFrame
				.add(new JLabel(
						"<html><body>Difficulty UTC AI<br>(1 = easy, 3 = hard)</body></html>"));
		settingsFrame.add(mDifficultyUtcAiOne);

		settingsFrame.add(new JLabel("Player 2 :"));
		settingsFrame.add(mPlayerStylePlayer2);
		settingsFrame
				.add(new JLabel(
						"<html><body>Difficulty UTC AI<br>(1 = easy, 3 = hard)</body></html>"));
		settingsFrame.add(mDifficultyUtcAiTwo);

		settingsFrame.add(new JLabel("Player 3 :"));
		settingsFrame.add(mPlayerStylePlayer3);
		settingsFrame
				.add(new JLabel(
						"<html><body>Difficulty UTC AI<br>(1 = easy, 3 = hard)</body></html>"));
		settingsFrame.add(mDifficultyUtcAiThree);

		settingsFrame.add(new JLabel(""));
		settingsFrame.add(new JLabel(""));

		settingsFrame.add(new JLabel(""));
		settingsFrame.add(submitButton);

		settingsFrame.pack();

		mPlayerStylePlayer1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initJSlider();
			}
		});

		mPlayerStylePlayer2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initJSlider();
			}
		});

		mPlayerStylePlayer3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initJSlider();
				settingsFrame.repaint();
			}
		});

		
		
		
		ActionListener al = new ActionListener() {
		      public void actionPerformed( ActionEvent e ) {

		    	int player1 = returnIntForPlayerStyle(mPlayerStylePlayer1.getSelectedItem());
		    	int player2 = returnIntForPlayerStyle(mPlayerStylePlayer2.getSelectedItem());
		    	int player3 = returnIntForPlayerStyle(mPlayerStylePlayer3.getSelectedItem());
		    	
		    	int[] result = {player1, player2, player3};	 
		    	
		    	if(checkIfResultArrayIsValid(result)){
		    		int[] playerInformation = {player1, player2, player3};
		    		mSettings.setPlayerInformation(playerInformation);
		    		
		    		//------
		    		if(!isActivatedOne){
		    			mSettings.setDifficultyUTCAiOne(getSettingForValue(mSettings
		    					.getDifficultyUTCAiOne()), false);
		    		}
		    		else {
		    			int difficulty = mDifficultyUtcAiOne.getValue();
			    		mSettings.setDifficultyUTCAiOne(difficulty, true);
		    		}
		    		//------
		    		if(!isActivatedTwo){
		    			mSettings.setDifficultyUTCAiTwo(getSettingForValue(mSettings
		    					.getDifficultyUTCAiTwo()), false);
		    		}
		    		else {
		    			int difficulty = mDifficultyUtcAiTwo.getValue();
			    		mSettings.setDifficultyUTCAiTwo(difficulty, true);
		    		}
		    		//------
		    		if(!isActivatedThree){
		    			mSettings.setDifficultyUTCAiThree(getSettingForValue(mSettings
		    					.getDifficultyUTCAiThree()), false);
		    		}
		    		else {
		    			int difficulty = mDifficultyUtcAiThree.getValue();
			    		mSettings.setDifficultyUTCAiThree(difficulty, true);
		    		}
		    		
		    		
		    		
		    		
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

	public void initJSlider() {
		if (returnIntForPlayerStyle(mPlayerStylePlayer1.getSelectedItem()) != 3) {
			mDifficultyUtcAiOne.setEnabled(false);
			isActivatedOne = false;
			
		} else {
			mDifficultyUtcAiOne.setEnabled(true);
			isActivatedOne = true;
		}

		if (returnIntForPlayerStyle(mPlayerStylePlayer2.getSelectedItem()) != 3) {
			mDifficultyUtcAiTwo.setEnabled(false);
			isActivatedTwo = false;
		} else {
			mDifficultyUtcAiTwo.setEnabled(true);
			isActivatedTwo = true;
		}

		if (returnIntForPlayerStyle(mPlayerStylePlayer3.getSelectedItem()) != 3) {
			mDifficultyUtcAiThree.setEnabled(false);
			isActivatedThree = false;
		} else {
			mDifficultyUtcAiThree.setEnabled(true);
			isActivatedThree = true;
		}

	}

	public void showWarning() {
		JOptionPane
				.showMessageDialog(
						null,
						"This Game needs at least two players // OR // Player one can't be none !",
						"Not a valid choice", JOptionPane.WARNING_MESSAGE);
	}

	private boolean checkIfResultArrayIsValid(int[] style) {
		boolean result = true;

		int one = style[0];
		int two = style[1];
		int three = style[2];

		if (one == 0) {
			result = false;
		}

		if (one == 0 && two == 0) {
			result = false;
		}
		if (one == 0 && three == 0) {
			result = false;
		}
		if (two == 0 && three == 0) {
			result = false;
		}

		return result;
	}

	private int returnIntForPlayerStyle(Object style) {
		int result = 0;

		if (style.equals("Player")) {
			result = 1;
		}
		if (style.equals("RandomAI")) {
			result = 2;
		}
		if (style.equals("UTCAI")) {
			result = 3;
		}
		if (style.equals("None")) {
			result = 0;
		}

		return result;
	}

	private Object returnStyleForInt(int number) {
		String result = "";

		switch (number) {
		case 0:
			result = "None";
			break;
		case 1:
			result = "Player";
			break;
		case 2:
			result = "RandomAI";
			break;
		case 3:
			result = "UTCAI";
			break;
		}
		return result;

	}

	private int getSettingForValue(int n) {
		int result = 1;

		switch (n) {
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
