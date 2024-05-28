import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Moves {
	public int lives;
	private Stack<Move> moves;
	
	public Moves(int l) {
		moves = new Stack<>();
		lives = l;
	}
	
	public void makeMove(int x, int y, boolean r) {
		moves.add(new Move(x,y, r));
	}
	
	public Move lastMove() {
		return moves.pop();
	}
	
}
