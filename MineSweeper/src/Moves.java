import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Moves {
	Stack<Pose2d> moves = new Stack<>();
	public int undos;
	
	public Moves(int undoAmount) {
		undos = undoAmount;
	}
	
	public void makeMove(int x, int y) {
		moves.add(new Pose2d(x,y));
	}
	
	public Pose2d undo() {
		undos--;
		return moves.pop();
	}
	
}
