package tinf11bc.kbs.yavalath.uct;

import tinf11bc.kbs.yavalath.logic.Player;
import tinf11bc.kbs.yavalath.logic.YavalathException;
import tinf11bc.kbs.yavalath.util.Board;

public class UCTAI {
    
	private Player[] player;
	private int numberOfPlayers;
	private int numberOfMoves;
	private int[][] currBoard;
	
    private Node root = null;
    private static final double UCTK = 0.44; // 0.44 = sqrt(1/5)
    // Larger values give uniform search
    // Smaller values give very selective search

	// generate a move, using the uct algorithm
    public int UCTSearch(int numsim, int[][] board, Player[] player, int numberOfPlayers, int numberOfMoves) throws YavalathException {
        root = new Node(-1); //init uct tree
        createChildren(root);

        for (int i=0; i<numsim; i++) {
        	currBoard = board;
        	this.player = player;
        	this.numberOfPlayers = numberOfPlayers;
        	this.numberOfMoves = numberOfMoves;
            playSimulation(root);
        }

        Node bestChild = getBestChild(root);
        return bestChild.move;
    }
    
    // expand children in Node
    private void createChildren(Node parent) {
      Node last = parent;
      for(int move : Board.tiles) {
    	  if(currBoard[move/10][move%10] == 0) {
    		  Node node = new Node(move);
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
        int randomresult = 0;
        if (n.child == null && n.visits < 10) { // 10 simulations until chilren are expanded (saves memory)
            //randomresult = playRandomGame();
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
            
            //makeMove();

            int res = playSimulation(next);
            randomresult = 1 - res;
        }

        n.update(1 - randomresult); //update node (Node-wins are associated with moves in the Nodes)
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
        Node res=null;
        Node next = node.child;
        double best_uct=0;

        while (next!=null) { // for all children
            double uctvalue;
            if (next.visits > 0) {
                double winrate=next.getWinRate();
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

    /*
    public void makeRandomMove() {
      int x=0;
      int y=0;
      while (true) {
        x=rand.nextInt(BOARD_SIZE);
        y=rand.nextInt(BOARD_SIZE);
        if (f[x][y]==0 && isOnBoard(x,y)) break;
      }
      makeMove(x,y);
    }

    // return 0=lose 1=win for current player to move
    int playRandomGame() {
      int cur_player1=cur_player;
      while (!isGameOver()) {
        makeRandomMove();
      }
      return getWinner()==curplayer1 ? 1 : 0;
    }
    */
}
