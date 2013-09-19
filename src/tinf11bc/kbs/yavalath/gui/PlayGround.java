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
import javax.swing.JPanel;

import tinf11bc.kbs.yavalath.util.GameState;

public class PlayGround {
	private JFrame mRoot;
	private JLayeredPane mLayeredPane;
	
	
	private ImageIcon imgCourton = null;
	private ImageIcon imgTokenRed = null;
	private ImageIcon imgTokenBlue = null;
	private ImageIcon imgTokenGreen = null;
	
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

	public PlayGround(JFrame root) {
		mRoot = root;
		mLayeredPane = root.getLayeredPane();
		
		imgCourton = new ImageIcon("res/courton.png");
		
		imgTokenRed = new ImageIcon("res/token_red.png");
		imgTokenBlue = new ImageIcon("res/token_blue.png");
		imgTokenGreen = new ImageIcon("res/token_green.png");
		
		drawPlayGroundOnRootFrame();

	}
	
	

	private void drawPlayGroundOnRootFrame() {
		JLabel labelCourton = new JLabel(imgCourton);
		
		int imgHeight = imgCourton.getIconHeight();
		int imgWidth = imgCourton.getIconWidth();
		
		int posX = 200;
		int posY = 20;
		
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
	
	
	
	
	

}
