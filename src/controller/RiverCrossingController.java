package controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import model.ICrosser;
import strategies.ICrossingStrategy;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
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
        try {
            FileOutputStream file = new FileOutputStream(new File("./saved_game.xml"));
            XStream xStream = new XStream(new DomDriver());


            // this line avoids security warnings
            xStream.allowTypesByRegExp(new String[] { ".*" });


            xStream.toXML(this,file);


            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadGame() {
        try {
            FileInputStream file = new FileInputStream(new File("./saved_game.xml"));
            XStream xStream = new XStream(new DomDriver());

            // this line avoids security warnings
            xStream.allowTypesByRegExp(new String[] { ".*" });

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
