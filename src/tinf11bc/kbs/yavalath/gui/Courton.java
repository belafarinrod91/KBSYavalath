package tinf11bc.kbs.yavalath.gui;

import javax.swing.JLabel;

public class Courton {
	private int mCoordX;
	private int mCoordY;
	private int mRow;
	private int mColumn;
	private boolean mOccupied;
	
	private tinf11bc.kbs.yavalath.gui.PlayGround.TokenColor mColor;
	private enum TokenColor {
		RED, BLUE, GREEN
	}
	
	
	private JLabel mToken;
	
	public Courton(int coordX, int coordY, int row, int column){
		mCoordX = coordX;
		mCoordY = coordY;
		mRow = row;
		mColumn = column;
		
	}
	
	
	
	public void setPlayerColor(tinf11bc.kbs.yavalath.gui.PlayGround.TokenColor color){
		mColor = color;
	}
	
	public tinf11bc.kbs.yavalath.gui.PlayGround.TokenColor getPlayerColor(){
		return mColor;
	}
	

	public JLabel getToken() {
		return mToken;
	}




	public void setToken(JLabel mToken) {
		this.mToken = mToken;
	}




	public int[] getRowAndColumn(){
		int result[] = {mRow, mColumn};
		return result;
	}
	 

	public int getRow() {
		return mRow;
	}


	public int getColumn() {
		return mColumn;
	}


	public int getCoordX() {
		return mCoordX;
	}


	public int getCoordY() {
		return mCoordY;
	}

	public boolean isOccupied() {
		return mOccupied;
	}

	public void setOccupied(boolean mOccupied) {
		this.mOccupied = mOccupied;
	}
	
	
	
	
	
	
	
	
}
