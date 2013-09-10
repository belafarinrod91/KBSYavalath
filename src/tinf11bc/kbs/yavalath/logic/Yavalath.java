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
			System.out.println("GameState: "+gameState);
		}
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
		int[] x = {0,0};
		int[] y	= {0,0};
		
		for(int j = 0; j < 9; j++){
			for(int i = 0; i < 9; i++){
				if(board[j][i] >= 0){
					if(x[0] != board[j][i]){
						if(x[1] == 3)
							return 10+playerNumber;
						x[0] = board[j][i];
						x[1] = 1;
					}else{
						x[1]++;
						if(x[1] >= 4)
							return x[0];
					}
				}else{
					x[1]=0;
				}
				
				if(board[i][j] >= 0){
					if(y[0] != board[i][j]){
						if(y[1] == 3)
							return -1;
						y[0] = board[i][j];
						y[1] = 1;
					}else{
						y[1]++;
						if(y[1] >= 4)
							return x[0];
					}
				}else{
					x[1]=0;
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
