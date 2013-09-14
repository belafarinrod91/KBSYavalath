package tinf11bc.kbs.yavalath.uct;

import tinf11bc.kbs.yavalath.logic.Player;
import tinf11bc.kbs.yavalath.logic.RandomAI;
import tinf11bc.kbs.yavalath.logic.Yavalath;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.util.GameState;

public class UCTAI {
    
	private GameState gameState;
	private GameState tempGameState;
	
    private Node root = null;
    private static final double UCTK = 0.44; // 0.44 = sqrt(1/5)
    // Larger values give uniform search
    // Smaller values give very selective search

	// generate a move, using the uct algorithm
    public int UCTSearch(int numsim, GameState gameState) throws YavalathException {
        root = new Node(gameState.getPlayingPlayer(), -1); //init uct tree
        createChildren(root);
        this.gameState = gameState;
        Player[] player = new Player[3];
        for(int n = 0; n < 3; n++){
        	player[n] = new RandomAI(n + 1);
        }
        
        this.gameState.changePlayer(player);

        for (int i=0; i<numsim; i++) {
        	tempGameState = this.gameState;
            playSimulation(root);
        }

        Node bestChild = getBestChild(root);
        return bestChild.move;
    }
    
    // expand children in Node
    private void createChildren(Node parent) {
      int childPlayerID = tempGameState.getNextPlayer();
      Node last = parent;
      for(int move : GameState.tiles) {
    	  if(gameState.getBoard()[move/10][move%10] == 0) {
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
    
 // return 0=lose 1=win for current player to move
    private int playSimulation(Node n) throws YavalathException {
        int randomresult = -1;
        if (n.child == null && n.visits < 10) { // 10 simulations until children are expanded (saves memory)
            randomresult = playRandomGame(n.playerID);
        }
        else {
            if (n.child == null) {
            	createChildren(n);
            }

            Node next = UCTSelect(n); // select a move
            if (next == null) {
            	System.err.println("Error in UCT Algorithm!");
            	throw new YavalathException();
            }
            
            tempGameState.playMove(next.move);

            randomresult = playSimulation(next);
        }

        n.update(randomresult); //update node (Node-wins are associated with moves in the Nodes)
        return randomresult;
    }
	
	// child with highest number of visits is used (not: best winrate)
    private Node getBestChild(Node root) {
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
    
    // return 0=lose 1=win for current player to move
    int playRandomGame(int playerID) throws YavalathException {
		Yavalath.playGame(tempGameState);
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
		  	  throw new YavalathException("Play Random Game Error!");
		}
    }
    
}
