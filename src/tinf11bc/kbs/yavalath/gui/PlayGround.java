package tinf11bc.kbs.yavalath.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tinf11bc.kbs.yavalath.util.GameState;
import tinf11bc.kbs.yavalath.util.GameState.State;

public class PlayGround {
	private JLayeredPane mLayeredPane;
	
	private JFrame mRoot;
	private ImageIcon imgCourton = null;
	private ImageIcon imgTokenRed = null;
	private ImageIcon imgTokenBlue = null;
	private ImageIcon imgTokenGreen = null;
	
	JLabel mPlayerOnMove = new JLabel();

	
	
	private int clickedPosition = -1;
	
	public enum TokenColor
	{
	  RED, GREEN, BLUE
	}
	

	
	private List<Courton> mCourtonList = new LinkedList<Courton>();
	
	int[][] mInitBoard = { 
			{ -1, -1, -1, -1, 0, 0, 0, 0, 0 },
			{ -1, -1, -1, 0, 0, 0, 0, 0, 0 },
			{ -1, -1, 0, 0, 0, 0, 0, 0, 0 },
			{ -1, 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ -1, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ -1, -1, 0, 0, 0, 0, 0, 0, 0 },
			{ -1, -1, -1, 0, 0, 0, 0, 0, 0 },
			{ -1, -1, -1, -1, 0, 0, 0, 0, 0 } };
	protected boolean buttonClicked;

	public PlayGround() {
		mRoot = new JFrame("Yavalath-Game !");
		mRoot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mRoot.setSize(950,650);
		mRoot.setLocation(50,50);
		mRoot.setVisible(true);
		
		

		mLayeredPane = mRoot.getLayeredPane();
		
		imgCourton = new ImageIcon("res/courton.png");
		
		imgTokenRed = new ImageIcon("res/token_red.png");
		imgTokenBlue = new ImageIcon("res/token_blue.png");
		imgTokenGreen = new ImageIcon("res/token_green.png");
		
		drawPlayGroundOnRootFrame();
		
		
		mPlayerOnMove.setBounds(10, 80, 500, 100);
		mLayeredPane.add(mPlayerOnMove, new Integer(3));

	}
	
	

	private void drawPlayGroundOnRootFrame() {
		JLabel labelCourton = new JLabel(imgCourton);
		
		int imgHeight = imgCourton.getIconHeight();
		int imgWidth = imgCourton.getIconWidth();
		
		int posX = 200;
		int posY = 120;
		
		for (int i = 0; i < mInitBoard.length; i++) {
			posX = 200;
			for (int j = 0; j < mInitBoard[i].length; j++) {
				labelCourton = new JLabel(imgCourton);
				
				if (mInitBoard[i][j] == 0) {
					final int id = j;
					drawCourtonField(posX, posY, labelCourton, i, j);
					posX = posX + imgWidth;
				}
				else {
					posX = (int) (posX + 0.5*imgWidth);
				}
			}
			posY = (int) (posY + 0.75*imgHeight);
			
			
			
			mRoot.repaint();
		}
		
		
		
	}
	
	
	public void drawCourtonField(final int posX, final int posY, JLabel labelCourton, final int row, final int column){
		Courton courton = new Courton(posX, posY, row, column);
		labelCourton.setBounds(posX, posY,
				imgCourton.getIconWidth(),
				imgCourton.getIconHeight());

		labelCourton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int r = row,c = column;
				if(r >= 5)
					c = c - r + 4;
				clickedPosition = (r*10)+c;
			}
		});
		mLayeredPane.add(labelCourton, new Integer(1));
		
		mCourtonList.add(courton);
	}
	
	public void setToken(int row, int column, int playingPlayer){

		if(row >= 5)
			column = column + row - 4;
		
		Courton courton = getCourtonByIntAndRow(row, column);
		int xPos = courton.getCoordX();
		int yPos = courton.getCoordY();
		JLabel labelTokenColor = null ;
		
		
		TokenColor color = TokenColor.RED;
		
		if(playingPlayer == 1)
			color = TokenColor.RED;
		if(playingPlayer == 2)
			color = TokenColor.BLUE;
		if(playingPlayer == 3)
			color = TokenColor.GREEN;
		//TODO : Get a signal from logic, which player has to set a token.

		
		switch(color){ 
        case RED: 
        	labelTokenColor = new JLabel(imgTokenRed);
            break; 
        case BLUE: 
        	labelTokenColor = new JLabel(imgTokenBlue); 
            break; 
        case GREEN: 
        	labelTokenColor = new JLabel(imgTokenGreen);
            break; 
        default: 
            System.out.println("non defined color !"); 
        } 
		
		
		
		if(!courton.isOccupied()) {
			labelTokenColor.setBounds(xPos-19, yPos-15, 100, 100);
			courton.setOccupied(true);
			courton.setPlayerColor(color);
			courton.setToken(labelTokenColor);
			
			
			mLayeredPane.add(labelTokenColor, new Integer(2));
			mRoot.repaint();
		}
		else {
//			removeToken(row, column);
		
		}
		
	}
	
	private void removeToken(int row, int column){
		Courton courton = getCourtonByIntAndRow(row, column);
		courton.setOccupied(false);
		mLayeredPane.remove(courton.getToken());
		mRoot.repaint();
	}
	
	
	
	private Courton getCourtonByIntAndRow(int row, int column){
		Courton courton = null;
		int compareTo[] = {row, column};
		for(int cnt = 0; cnt < mCourtonList.size(); cnt++){
			courton = mCourtonList.get(cnt);
					
			if(Arrays.equals(compareTo, courton.getRowAndColumn())){
				courton = mCourtonList.get(cnt);
				break;
			}
			
		}
		return courton;
	}

	public int checkMove() {
		clickedPosition = -1;
	    while(clickedPosition == -1){          
            try {
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
           
        } 
		return clickedPosition; 
	}
	
	
	public void setGameInformation(String message){
		
		
		JLabel gameInformation = new JLabel();
		gameInformation.setText(message);
		gameInformation.setBounds(10, -30, 500, 100);
		mLayeredPane.add(gameInformation, new Integer(1));
		
	}
	
	
	public void showSettings(int[] players){
		
		
		
		
		String player1 = "-", player2 = "-", player3 ="-";
		
		switch(players[0]){
			case 1:
				player1 = "You";
				break;
			case 2:
				player1 = "RandomAI";
				break;
			case 3:
				player1 = "UCTAI";
				break;
			default:
				player1 = "-";
				break;
		}
		
		switch(players[1]){
		case 1:
			player2 = "You";
			break;
		case 2:
			player2 = "RandomAI";
			break;
		case 3:
			player2 = "UCTAI";
			break;
		default:
			player2 = "-";
			break;
		}
		
		switch(players[2]){
		case 1:
			player3 = "You";
			break;
		case 2:
			player3 = "RandomAI";
			break;
		case 3:
			player3 = "UCTAI";
			break;
		default:
			player3 = "-";
			break;
		}
		
				
		
		
		JLabel settingsInformation = new JLabel();
		settingsInformation.setText(
				"<html>"
				+"<body>"
				+"<hr>"
				+"Player 1: "+player1+"<br>"
				+"Player 2: "+player2+"<br>"
				+"Player 3: "+player3+"<br>"
				+"</body></html>");
		settingsInformation.setBounds(10, 10, 500, 100);
		mLayeredPane.add(settingsInformation, new Integer(1));
	}
	
	public void showPlayer(int player, State state){
		mPlayerOnMove.setText(
				"<html>"
				+"<body>"
				+"<hr>"
				+"Player 1: "+player+"<br>"
				+"</body></html>");
	}
	
	
	
	

}
