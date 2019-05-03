package command;

import model.ICrosser;

import java.util.List;
import java.util.Stack;

public class Command {

    private Stack<Move> moves = new Stack<>();
    private Stack<Move> temp = new Stack<>();




    void saveMove(List<ICrosser> crossers, boolean fromLeftToRight) {
        temp = new Stack<>();
        moves.push(new Move(crossers, fromLeftToRight));
    }

    Move undoMove() {
        Move curr = moves.pop();
        temp.push(curr);
        return curr;
    }

    Move redoMove() {
        Move curr = temp.pop();
        moves.push(curr);
        return curr;
    }

    boolean canRedoMove() {
        return !temp.empty();
    }

    boolean canUndoMove() {
        return !temp.empty();
    }

    class Move {
        List<ICrosser> Crosser;
        boolean fromLeftToRight;

        public Move(List<ICrosser> crosser, boolean fromLeftToRight) {
            Crosser = crosser;
            this.fromLeftToRight = fromLeftToRight;
        }
    }

}
