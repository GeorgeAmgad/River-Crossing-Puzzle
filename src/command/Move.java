package command;

import model.ICrosser;

import java.util.List;

public class Move {

    private List<ICrosser> Crosser;
    private boolean fromLeftToRight;

    public Move(List<ICrosser> crosser, boolean fromLeftToRight) {
        Crosser = crosser;
        this.fromLeftToRight = fromLeftToRight;
    }

    public List<ICrosser> getCrosser() {
        return Crosser;
    }

    public boolean isFromLeftToRight() {
        return fromLeftToRight;
    }

}
