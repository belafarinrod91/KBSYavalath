package tinf11bc.kbs.yavalath.logic;

import tinf11bc.kbs.yavalath.logic.AI;
import tinf11bc.kbs.yavalath.logic.Player;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.gui.GuiFactory;

/**
 * @author Chris
 *
 */
public class Yavalath {
	static int[][] board = null;
	static int gameState = 0;
			
	static int[][] setUp(){
		int[][] board = {{ -1, -1, -1, -1, 0, 0, 0, 0, 0},
			 	            { -1, -1, -1, 0, 0, 0, 0, 0, 0},
				               { -1, -1, 0, 0, 0, 0, 0, 0, 0},
				                  { -1, 0, 0, 0, 0, 0, 0, 0, 0},
				                     { 0, 0, 0, 0, 0, 0, 0, 0, 0},
				                      { 0, 0, 0, 0, 0, 0, 0, 0, -1},
				                        { 0, 0, 0, 0, 0, 0, 0, -1, -1},
				                          { 0, 0, 0, 0, 0, 0, -1, -1, -1},
				                            { 0, 0, 0, 0, 0, -1, -1, -1, -1}};
		return board;
	}
	
	public static void game(int[] newPlayers) throws YavalathException{

		Player player[] = new Player[3];
		int numberOfPlayers = 0;
		gameState = 0; //0: playing; 1: Player 1 won; 2: Player 2 won; 3: Player 3 won;	
					   //10: draw; 11:Player 1 out; 12: Player 2 out; 13: Player 3 out; 	
		
		board = setUp();
		
		for(int n = 0; n <=2; n++){
			switch(newPlayers[n]){
			case(1):
				player[n] = new Player(n+1);
				numberOfPlayers++;
				break;
			case(2):
				player[n] = new AI(n+1);
				numberOfPlayers++;
				break;
			default:
				break;
			}
		}

		drawBoard(board);
		while(gameState == 0){
			for(int i = 0; i < numberOfPlayers;i++){
				if(gameState-10 == i)
					continue;
				
				addStone(i+1,player[i].makeMove(board));
				
				gameState = checkGameState(board, i+1);
				drawBoard(board);
				if(gameState != 0 && gameState <=10)
					break;
			}
		}
		System.out.println(gameState);
	}
	
	private static void drawBoard(int[][] board){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				System.out.print(board[i][j]+" ");
			}	
			System.out.println();
		}
		System.out.println("GameState: "+gameState);
	}
	
	private static void addStone(int playerNumber,int position) throws YavalathException{		
		if(board[position/10][position%10] == 0){
			board[position/10][position%10] = playerNumber;
		}else{
			System.err.println("NOOOOOOOOOO!");
			throw new YavalathException();
		}
			
	}
	
	private static int checkGameState(int[][] board, int playerNumber) {
		
		for(int j = 0; j < 9; j++){
			for(int i = 0; i < 9; i++){
				if(board[j][i] > 0){
					if(	board[j][i] == board[j][i+1] &&
						board[j][i] == board[j][i+2] &&
						board[j][i] == board[j][i+3] && i < 6){
								return playerNumber;
					}else{
						if(	board[j][i] == board[j][i+1] &&
							board[j][i] == board[j][i+2] && i < 7){
									return playerNumber+10;
						}
					}
				}
				if(board[i][j] > 0){
					if(	board[i][j] == board[i+1][j] &&
						board[i][j] == board[i+2][j] &&
						board[i][j] == board[i+3][j] && i < 6){
								return playerNumber;
					}else{
						if(	board[i][j] == board[i+1][j] &&
							board[i][j] == board[i+2][j] && i < 7){
									return playerNumber+10;
						}
					}
				}
			}	
		int t = 0,r = j;
		if(r >= 4 && r <= 12){	
			if(r > 8){
				t = r - 8;
				r = 8;
			}else{
				t = 0;
			}
			try{
				for(; t < r ; t++){
					if(board[r][t] > 0){
						if(	board[t][r] == board[t+1][r-1] &&
							board[t][r] == board[t+2][r-2] &&
							board[t][r] == board[t+3][r-3]){
									return playerNumber;
						}else{
							if(	board[t][r] == board[t+1][r-1] &&
								board[t][r] == board[t+2][r-2]){
										return playerNumber+10;
							}
						}
					}
				}
			}finally{
				continue;
			}
		}
		}
		return 0;
	}


	/**
	 * @param args
	 * @throws YavalathException 
	 */
	public static void main(String[] args) throws YavalathException {
		boolean playing = true;
		while(playing  == true){
			//menu with choosing:
			//					-how many players? (2/3)
			//					-player/AI 
			
			int[] players = {1,2,0};
			game(players);
			
		}
	}

}
