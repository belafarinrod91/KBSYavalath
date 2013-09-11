package tinf11bc.kbs.yavalath.logic;

import tinf11bc.kbs.yavalath.logic.RandomAI;
import tinf11bc.kbs.yavalath.logic.Player;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.gui.GuiFactory;

/**
 * @author Chris, Stephan
 *
 */
public class Yavalath {
	
	private static int[][] board;
	private static Player[] player = new Player[3];
	private static Integer numberOfMoves;
	private static Integer numberOfPlayers;	
	
	static int[][] setUpBoard(){
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
	
	public static int getNumberOfPlayer() {
		return numberOfPlayers;
	}
	
	public static int getNumberOfMoves() {
		return numberOfMoves;
	}
	
	public static void newGame(int[] newPlayers) throws YavalathException{

		numberOfPlayers = 0;	
		numberOfMoves = 0;
		
		for(int n = 0; n < 3; n++){
			switch(newPlayers[n]){
			case(1):
				player[n] = new Player(n+1);
				numberOfPlayers++;
				break;
			case(2):
				player[n] = new RandomAI(n+1);
				numberOfPlayers++;
				break;
			default:
				break;
			}
		}

		board = setUpBoard();
		drawBoard(board);
		
		int result = playGame(board, player);
		
		if(result == 10) {
			System.out.println("It's a draw!");
		}
		else {
			System.out.println("Player " + result + " won!");
		}
	}
	
	public static int playGame(int[][] board, Player[] player) throws YavalathException {
		int gameState = 0; 	//0: playing; 1: Player 1 won; 2: Player 2 won; 3: Player 3 won;	
		   					//10: draw; 11:Player 1 out; 12: Player 2 out; 13: Player 3 out; 
		
		while(gameState == 0){
			for(int i = 0; i < numberOfPlayers;i++){
				
				System.out.println("GameState: " + gameState + "\nNumber of Moves: " + numberOfMoves + "\nPlayer's Turn: " + player[i].getPlayerNumber());
				
				gameState = addStone(board, player[i].getPlayerNumber(), player[i].makeMove(board));
				numberOfMoves++;
				
				drawBoard(board);
				
				if(numberOfMoves == 61){
					gameState = 10;
					return gameState;
				}
				
				if(gameState > 10) {
					numberOfPlayers--;
					if(numberOfPlayers == 1) {
						if(gameState - 10 == player[0].getPlayerNumber()) {
							gameState = player[1].getPlayerNumber();
						}
						else {
							gameState = player[0].getPlayerNumber();
						}
						return gameState;
					}
					else {
						for(int j = 1; j < 3; j++) {
							if(player[j].getPlayerNumber() > gameState - 10) {
								player[j-1] = player[j];
							}
						}
						System.out.println("Player " + (gameState - 10) + " is out!");
						gameState = 0;
						i--;
					}
				}
			}
		}
		return gameState;
	}
	
	private static void drawBoard(int[][] board){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(board[i][j] == -1) {
					System.out.print("  ");
				}else{
					System.out.print(board[i][j]+" ");
				}
			}	
			System.out.println();
		}
	}
	
	private static int addStone(int[][] board, int playerNumber,int position) throws YavalathException{		
		if(board[position/10][position%10] == 0){
			board[position/10][position%10] = playerNumber;
		}else{
			throw new YavalathException();
		}
		 return checkGameState(board, playerNumber, position);
	}
	
	private static int checkGameState(int[][] board, int playerNumber, int position) throws YavalathException {
		
		int x = position/10;
		int y = position%10;
		for(int i = 0; i+2 < 9; i++){
				try{
					if(board[x][i] == playerNumber){						
						if(	i < 6 && board[x][i] == board[x][i+1] &&
							board[x][i] == board[x][i+2] &&
							board[x][i] == board[x][i+3]){
								return playerNumber;
						}else{
							if(board[x][i] == board[x][i+1] &&
								board[x][i] == board[x][i+2]){
								return playerNumber+10;
							}
						}
					}
					if(board[i][y] == playerNumber){
						if(	i < 6 && board[i][y] == board[i+1][y] &&
							board[i][y] == board[i+2][y] &&
							board[i][y] == board[i+3][y]){
								return playerNumber;
						}else{
							if(board[i][y] == board[i+1][y] &&
								board[i][y] == board[i+2][y]){
								return playerNumber+10;
							}
						}
					}
				}catch(Exception e){
					System.err.println("Error in Win/Lose detection (h/v)!");
					throw new YavalathException();
				}
			}
			
			int t = 0,r = x+y;
			if(r > 8){
				t = r - 8;
				r = 8;
			}
			try{
				for(; t < 8 && r-2 >= 0; t++,r--){
					if(r-3 <= 0 && t+3 <= 8 && board[t][r] > 0){
						if(	board[t][r] == board[t+1][r-1] &&
							board[t][r] == board[t+2][r-2]){
							if(r-3>=0 && board[t][r] == board[t+3][r-3]){
								return playerNumber;
							}else{
								return playerNumber+10;
							}
						}
					}
				}
			}catch(Exception e){
				System.err.println("Error in Win/Lose detection (diagonal)!");
				throw new YavalathException();
			}
			return 0;
	}

	public static int playRandomGame(int[][]board) throws YavalathException {
		Player[] randomAI = new Player[3];
		for(int n = 0; n < numberOfPlayers; n++) {
			randomAI[n] = new RandomAI(player[n].getPlayerNumber());
		}
		int movesBackup = numberOfMoves;
		int result = Yavalath.playGame(board, randomAI);
		numberOfMoves = movesBackup;
		return result;
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
			
			int[] players = {2,2,2};	// Player: 1
										// AI: 2
			newGame(players);
			playing = false;
		}
	}

}
