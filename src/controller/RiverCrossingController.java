package controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import command.Command;
import command.Move;
import model.ICrosser;
import strategies.ICrossingStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RiverCrossingController implements IRiverCrossingController {

    private ICrossingStrategy strategy;

    private List<ICrosser> rightBank;
    private List<ICrosser> leftBank;

    // this list is for saving and loading
    private List<ICrosser> tempCrossers;

    private int numberOfSales;
    private boolean boatOnTheLeftBank;

    private Command command = new Command();


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
        List<ICrosser> savedCrossers = new ArrayList<>(crossers);
        command.saveMove(savedCrossers, fromLeftToRightBank);
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
        return command.canUndoMove();
    }

    @Override
    public boolean canRedo() {
        return command.canRedoMove();
    }

    @Override
    public void undo() {
        Move move = command.undoMove();

        if (move.isFromLeftToRight()) {
            for (ICrosser crosser : move.getCrosser()) {
                leftBank.add(crosser); // inverted from the move method
                rightBank.remove(crosser);
            }
        } else {
            for (ICrosser crosser : move.getCrosser()) {
                rightBank.add(crosser); // inverted from the move method
                leftBank.remove(crosser);
            }
        }
        boatOnTheLeftBank = move.isFromLeftToRight();
        numberOfSales--;
    }

    @Override
    public void redo() {
        Move move = command.redoMove();

        if (move.isFromLeftToRight()) {
            for (ICrosser crosser : move.getCrosser()) {
                rightBank.add(crosser); // same as move method
                leftBank.remove(crosser);
            }
        } else {
            for (ICrosser crosser : move.getCrosser()) {
                leftBank.add(crosser); // same as move method
                rightBank.remove(crosser);
            }
        }
        boatOnTheLeftBank = !move.isFromLeftToRight();  //switch boat place in any move
        numberOfSales++;
    }

    @Override
    public void saveGame() {
        try {
            FileOutputStream file = new FileOutputStream(new File("saved_game.xml"));
            XStream xStream = new XStream(new DomDriver());

            // this line avoids security warnings
            xStream.allowTypesByRegExp(new String[]{".*"});

            xStream.toXML(this, file);

            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadGame() {
        try {
            FileInputStream file = new FileInputStream(new File("saved_game.xml"));
            XStream xStream = new XStream(new DomDriver());

            // this line avoids security warnings
            xStream.allowTypesByRegExp(new String[]{".*"});

            RiverCrossingController fetchedController = (RiverCrossingController) xStream.fromXML(file);
            strategy = fetchedController.strategy;
            rightBank = fetchedController.rightBank;
            leftBank = fetchedController.leftBank;
            numberOfSales = fetchedController.numberOfSales;
            boatOnTheLeftBank = fetchedController.boatOnTheLeftBank;
            tempCrossers = fetchedController.tempCrossers;


            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTempCrossers(List<ICrosser> tempCrossers) {
        this.tempCrossers = tempCrossers;
    }

    public List<ICrosser> getTempCrossers() {
        return tempCrossers;
    }

    @Override
    public List<List<ICrosser>> solveGame() {
        return null;
    }

}
