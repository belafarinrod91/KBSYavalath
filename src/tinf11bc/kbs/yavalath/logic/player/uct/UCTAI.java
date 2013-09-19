package tinf11bc.kbs.yavalath.logic.player.uct;

import tinf11bc.kbs.yavalath.logic.Yavalath;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.logic.player.Player;
import tinf11bc.kbs.yavalath.logic.player.RandomAI;
import tinf11bc.kbs.yavalath.util.GameState;

public class UCTAI extends Player{
    
	private GameState gameState;
	private GameState tempGameState;
	
    private Node root = null;
    private static final double UCTK = 0.44; // 0.44 = sqrt(1/5)
    private int numberOfSimulations;
    // Larger values give uniform search
    // Smaller values give very selective search

    public UCTAI(int playerNumber, int numsim) {
    	super(playerNumber);
    	numberOfSimulations = numsim;
    }
    
    @Override
    public int makeMove(GameState gameState) throws YavalathException {
    	int move = forcedMove(gameState.getBoard());
    	if(move != 0){
    		System.out.println("UCTAI Forced Move: " + move);
			return move;
    	}
    	move = UCTSearch(numberOfSimulations, gameState);
    	System.out.println("UCTAI Move: " + move);
    	return move;
    }
    
	// generate a move, using the uct algorithm
    public int UCTSearch(int numsim, GameState gameState) throws YavalathException {
    	this.gameState = new GameState(gameState);
    	if(this.gameState.getPlayingPlayer() != this.getPlayerNumber()) {
    		throw new YavalathException("UCTAI Error");
    	}
        root = new Node(this.gameState.getPlayingPlayer(), -1); //init uct tree
        Player[] player = new Player[3];
        for(int n = 0; n < 3; n++){
        	player[n] = new RandomAI(n + 1);
        }
        this.gameState.changePlayer(player);
        tempGameState = new GameState(this.gameState);
        createChildren(root);
        
        for (int i=0; i<numsim; i++) {
        	tempGameState = new GameState(this.gameState);
            playSimulation(root);
        }
        
        Node out = root.child;
        while(out != null) {
            System.out.println(out.move + ", " + out.visits + ", " + out.wins + ", " + (double)out.wins/out.visits);
            out = out.sibling;
        }
        Node bestChild = getBestChild(root);
        System.out.println("Win Chance: " + (double) bestChild.wins / bestChild.visits);
        return bestChild.move;
    }
    
    // expand children in Node
    private void createChildren(Node parent) throws YavalathException {
      int childPlayerID = tempGameState.getPlayingPlayer();
      Node last = parent;
      int forced = forcedMove(tempGameState.getBoard());
      if(forced != 0) {
    	  last.child = new Node(childPlayerID, forced);
      }
      else{
	      for(int move : GameState.tiles) {
	    	  if(tempGameState.getBoard()[move/10][move%10] == 0) {
	    		  Node node = new Node(childPlayerID, move);
	              if (last == parent) {
	            	  last.child = node;
	              }
	              else {
	            	  last.sibling = node;
	              }
	              last = node;
	    	  }
	      }
      }
    }
    
 // return 0=lose 1=win for current player to move
    private int playSimulation(Node n) throws YavalathException {
        int randomresult = stateID();
        if(randomresult == -1) {
        	if (n.child == null && n.visits < 10) { // 10 simulations until children are expanded (saves memory)
            	randomresult = playRandomGame();
            }
            else {
                if (n.child == null) {
                	createChildren(n);
                }

                Node next = UCTSelect(n); // select a move
                if (next == null) {
                	randomresult = 0; //DRAW
                	//System.err.println("Error in UCT Algorithm!");
                	//throw new YavalathException();
                }
                else {
                	tempGameState.playMove(next.move);
                    randomresult = playSimulation(next);
                }
            }
        }
        
        n.update(randomresult); //update node (Node-wins are associated with moves in the Nodes)
        return randomresult;
    }
	
	// child with highest number of visits is used (not: best winrate)
    private Node getBestChild(Node root){
        Node child = root.child;
        Node best_child = null;
        int  best_visits= -1;
        while (child!=null) { // for all children
            if (child.visits>best_visits) {
                best_child=child;
                best_visits=child.visits;
            }
            child = child.sibling;
        }
        return best_child;
    }

    public Node UCTSelect(Node node) {
        Node res = null;
        Node next = node.child;
        double best_uct=0;

        while (next != null) { // for all children
            double uctvalue;
            if (next.visits > 0) {
                double winrate = next.getWinRate();
                double uct = UCTK * Math.sqrt( Math.log(node.visits) / next.visits );
                uctvalue = winrate + uct;
            }
            else {
                // Always play a random unexplored move first
                uctvalue = 10000 + 1000*Math.random();
            }

            if (uctvalue > best_uct) { // get max uctvalue of all children
                    best_uct = uctvalue;
                    res = next;
            }

            next = next.sibling;
        }
        return res;
    }
    
    private int playRandomGame() throws YavalathException {
		Yavalath.playGame(tempGameState);
		int res = stateID();
		if(res != -1) {
			return res;
		}
		else {
			throw new YavalathException("Play Random Game Error!");
		}
    }
    
    private int stateID() throws YavalathException {
    	switch(tempGameState.getState()){
			case DRAW:
				return 0;
		    case PLAYER1WIN:
		    	return 1;
		    case PLAYER2WIN:
		    	return 2;
		    case PLAYER3WIN:
		    	return 3;
		    default:
		  	  return -1;
    	}

    }
    
}
