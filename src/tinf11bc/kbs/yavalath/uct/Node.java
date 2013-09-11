package tinf11bc.kbs.yavalath.uct;

public class Node {
    public int wins=0;
    public int visits=0;

    public int playerID;
    public int move;

    //public Node parent; //optional
    public Node child;
    public Node sibling;

    public Node(int move) {
      this.move = move;
    }

    public void update(int val) {
      visits++;
      wins+=val;
    }

    public double getWinRate() {
        if (visits>0) {
        	return (double) wins / visits;
        }
        else {
        	return 0;
        }
    }

 }

