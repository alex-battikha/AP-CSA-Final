import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Moves {
	Stack<Pose2d> moves = new Stack<>();
	
	public void makeMove(int x, int y) {
		moves.add(new Pose2d(x,y));
	}
	
	public Pose2d lastMove() {
		return moves.pop();
	}
	
}
