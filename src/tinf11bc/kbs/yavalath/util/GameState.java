package tinf11bc.kbs.yavalath.util;

import tinf11bc.kbs.yavalath.logic.Player;
import tinf11bc.kbs.yavalath.logic.RandomAI;
import tinf11bc.kbs.yavalath.logic.Yavalath;
import tinf11bc.kbs.yavalath.logic.YavalathException;

public class GameState {
	
	private int[][] board;

	private static Player[] player = new Player[3];
	private int numberOfMoves;		// <= 61
	private int numberOfPlayers; 	// 2 OR 3
	private int playingPlayer; 		// 1, 2, 3

	public static enum State{PLAYING, PLAYER1WIN, PLAYER2WIN, PLAYER3WIN,
						DRAW, PLAYER1OUT, PLAYER2OUT, PLAYER3OUT}
	private State state;
	
	private static int[][] cleanboard = 
		                {{ -1, -1, -1, -1, 0, 0, 0, 0, 0},
							{ -1, -1, -1, 0, 0, 0, 0, 0, 0},
							  { -1, -1, 0, 0, 0, 0, 0, 0, 0},
                                 { -1, 0, 0, 0, 0, 0, 0, 0, 0},
                                    { 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                     { 0, 0, 0, 0, 0, 0, 0, 0, -1},
                                       { 0, 0, 0, 0, 0, 0, 0, -1, -1},
                                        { 0, 0, 0, 0, 0, 0, -1, -1, -1},
                                         { 0, 0, 0, 0, 0, -1, -1, -1, -1}};
	
	public static int[] tiles = 
		   {4,5,6,7,8,
		13,14,15,16,17,18,
	  22,23,24,25,26,27,28,
	 31,32,33,34,35,36,37,38,
	40,41,42,43,44,45,46,47,48,
	 50,51,52,53,54,55,56,57,
	  60,61,62,63,64,65,66,
	   70,71,72,73,74,75,
		 80,81,82,83,84};
	
	public GameState(int[] newPlayers){
		board = cleanboard;
		numberOfPlayers = 0;	
		numberOfMoves = 0;
		playingPlayer = 1;
		setState(State.PLAYING);
		
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
	}
	
	public void changePlayer(Player[] player) {
		this.player = player;
	}
	

	public void playMove(int position) throws YavalathException{
		if(position == -1) {
			position = player[playingPlayer-1].makeMove(board);
		}
		
		if(board[position/10][position%10] == 0){
			board[position/10][position%10] = playingPlayer;
		}else{
			throw new YavalathException();
		}
		checkGameState(position);
		playingPlayer = getNextPlayer();
		System.out.println("Next Player: " + playingPlayer);
	}
	
	private int checkGameState(int position) throws YavalathException {
		
		int x = position/10;
		int y = position%10;
		for(int i = 0; i+2 < 9; i++){
				try{
					if(board[x][i] == playingPlayer){						
						if(	i < 6 && board[x][i] == board[x][i+1] &&
							board[x][i] == board[x][i+2] &&
							board[x][i] == board[x][i+3]){
								changeState(false);
						}else{
							if(board[x][i] == board[x][i+1] &&
								board[x][i] == board[x][i+2]){
								changeState(true);;
							}
						}
					}
					if(board[i][y] == playingPlayer){
						if(	i < 6 && board[i][y] == board[i+1][y] &&
							board[i][y] == board[i+2][y] &&
							board[i][y] == board[i+3][y]){
							changeState(false);
						}else{
							if(board[i][y] == board[i+1][y] &&
								board[i][y] == board[i+2][y]){
								changeState(true);;
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
								changeState(false);
							}else{
								changeState(true);
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
	
	private void changeState(boolean out) throws YavalathException{
		
		if(out){
			if(state == State.PLAYING){
				if(numberOfPlayers == 2){
					switch(playingPlayer){
					case(1):
						state = State.PLAYER2WIN;
						break;
					case(2):
						state = State.PLAYER1WIN;
						break;				
					}
				}else{
					switch(playingPlayer){
					case(1):
						state = State.PLAYER1OUT;
						break;
					case(2):
						state = State.PLAYER2OUT;
						break;
					case(3):
						state = State.PLAYER3OUT;
						break;					
					}
				numberOfPlayers--;
				}
			}else{
				switch(state){
				case PLAYER1OUT:
					switch(playingPlayer){
					case(2):
						state = State.PLAYER3WIN;
						break;
					case(3):
						state = State.PLAYER2WIN;
						break;
					}
					break;
				case PLAYER2OUT:
					switch(playingPlayer){
					case(1):
						state = State.PLAYER3WIN;
						break;
					case(3):
						state = State.PLAYER1WIN;
						break;
					}
					break;
				case PLAYER3OUT:
					switch(playingPlayer){
					case(1):
						state = State.PLAYER2WIN;
						break;
					case(2):
						state = State.PLAYER1WIN;
						break;
					}
					break;
				default:
					throw new YavalathException("Change State Error!");
				}
			}
			
		}else{
			switch(playingPlayer){
			case(1):
				state = State.PLAYER1WIN;
				break;
			case(2):
				state = State.PLAYER2WIN;
				break;
			case(3):
				state = State.PLAYER3WIN;
				break;
			}
		}

	}

	public int getNextPlayer() {
		switch(state){
			case PLAYING:
				if(numberOfPlayers == 2) {
					return ((playingPlayer % 2) + 1);
				}
				else {
					return ((playingPlayer % 3) + 1);
				}
			case PLAYER1OUT:
				if(playingPlayer == 2) {
					return 3;
				}
				else {
					return 2;
				}
			case PLAYER2OUT:
				if(playingPlayer == 1) {
					return 3;
				}
				else {
					return 1;
				}
			case PLAYER3OUT:
				if(playingPlayer == 1) {
					return 2;
				}
				else {
					return 1;
				}
			default:
				return -1;
		}
	}
	
	public int[][] getBoard() {
		return board;
	}

	public static Player[] getPlayer() {
		return player;
	}

	public int getNumberOfMoves() {
		return numberOfMoves;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public int getPlayingPlayer() {
		return playingPlayer;
	}

	public static int[] getTiles() {
		return tiles;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public State getState(){
		return state;
	}
}
