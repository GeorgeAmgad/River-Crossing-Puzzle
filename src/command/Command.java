package command;

import model.ICrosser;

import java.util.List;
import java.util.Stack;

public class Command {

    private Stack<Move> moves = new Stack<>();
    private Stack<Move> temp = new Stack<>();




    public void saveMove(List<ICrosser> crossers, boolean fromLeftToRight) {
        temp = new Stack<>();
        moves.push(new Move(crossers, fromLeftToRight));
    }

    public Move undoMove() {
        Move curr = moves.pop();
        temp.push(curr);
        return curr;
    }

    public Move redoMove() {
        Move curr = temp.pop();
        moves.push(curr);
        return curr;
    }

    public boolean canRedoMove() {
        return !temp.empty();
    }

    public boolean canUndoMove() {
        return !moves.empty();
    }



}
