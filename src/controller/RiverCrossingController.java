package controller;
import model.Crosser;
import model.ICrosser;
import strategies.ICrossingStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RiverCrossingController implements IRiverCrossingController {

    private ICrossingStrategy strategy;

    private List<ICrosser> rightBank;
    private List<ICrosser> leftBank;

    private int numberOfSales;
    private boolean boatOnTheLeftBank;


    @Override
    public void newGame(ICrossingStrategy gameStrategy) {
        strategy = gameStrategy;
        leftBank = new ArrayList<>();
        leftBank = strategy.getInitialCrossers();
        rightBank = new ArrayList<>();
        numberOfSales = 0;
        boatOnTheLeftBank = true;
    }

    @Override
    public void resetGame() {

    }

    @Override
    public String[] getInstructions() {
        return strategy.getInstructions();
    }

    @Override
    public List<ICrosser> getCrossersOnRightBank() {
        return rightBank;
    }

    @Override
    public List<ICrosser> getCrossersOnLeftBank() {
        return leftBank;
    }

    @Override
    public boolean isBoatOnTheLeftBank() {
        return boatOnTheLeftBank;
    }

    @Override
    public int getNumberOfSails() {
        return numberOfSales;
    }

    @Override
    public boolean canMove(List<ICrosser> crossers, boolean fromLeftToRightBank) {
        return strategy.isValid(rightBank, leftBank, crossers);
    }

    @Override
    public void doMove(List<ICrosser> crossers, boolean fromLeftToRightBank) {
        Iterator<ICrosser> iterator = crossers.iterator();
        if (fromLeftToRightBank) {
            while (iterator.hasNext()) {
                rightBank.add(iterator.next());
                iterator.remove();
            }
        } else {
            while (iterator.hasNext()) {
                leftBank.add(iterator.next());
                iterator.remove();
            }
        }
        boatOnTheLeftBank = !boatOnTheLeftBank;  //switch boat place in any move
        numberOfSales++;
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean canRedo() {
        return false;
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void saveGame() {

    }

    @Override
    public void loadGame() {

    }

    @Override
    public List<List<ICrosser>> solveGame() {
        return null;
    }
}
