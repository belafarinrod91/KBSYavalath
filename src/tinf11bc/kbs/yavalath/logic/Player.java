/**
 * 
 */
package tinf11bc.kbs.yavalath.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chris
 *
 */

public class Player {

int playerNumber;
	
	public Player(int playerNumber){
		this.playerNumber = playerNumber;
	}
	
	public int makeMove(int[][] board){
		int move = forcedMove(board);
		if(move != 0)
			return move;
		
	    InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    
		String eingabe = null;
		try {
			System.out.println("mache zug:");
			eingabe = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		move = Integer.parseInt(eingabe);
		  
		return move;
	}
	
	private int forcedMove(int[][]board){
		int[] x = {0,0};
		int[] y	= {0,0};
		
		for(int j = 0; j < 9; j++){
			for(int i = 0; i < 9; i++){
				if(board[j][i] > 0){
					if(x[0] != board[j][i]){
						x[0] = board[j][i];
						x[1] = 1;
						if(x[0] == board[j][i+2] && x[0] == board[j][i+3])
							return j*10+i+1;
					}else{
						if(x[0] == board[j][i]){
							x[1]++;
							if(x[0] == board[j][i+2])
								return j*10+i+1;
						}else{
							x[0] =0;
						}
					}
						
				}else{
					x[1]=0;
				}
				
				if(board[i][j] > 0){
					if(y[0] != board[i][j]){
						y[0] = board[i][j];
						y[1] = 1;
						if(x[0] == board[i][j+2] && x[0] == board[i][j+3])
							return i*10+j+1;
					}else{
						if(y[0] == board[i][j]){
							y[1]++;
							if(y[0] == board[i][j+2])
								return i*10+j+1;
						}else{
							y[0] =0;
						}
					}
						
				}else{
					y[1]=0;
				}
				
			}			
		}
		
		return 0;
		
	}
}
