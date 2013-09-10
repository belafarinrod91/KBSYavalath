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
	
	public Player(){
	}
	
	public int makeMove(int[][] board){
	    InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    
		String eingabe = null;
		try {
			System.out.println("mache zug:");
			eingabe = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int move = Integer.parseInt(eingabe);
		  
		return move;
	}
}
