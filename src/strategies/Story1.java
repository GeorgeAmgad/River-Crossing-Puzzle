package strategies;

import model.Crosser;
import model.ICrosser;
import model.visuals.Chicken;
import model.visuals.Dog;
import model.visuals.Farmer;
import model.visuals.Veggies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Story1 implements ICrossingStrategy {

    private static Story1 ourInstance;

    static {
        try {
            ourInstance = new Story1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Story1 getInstance() {
        return ourInstance;
    }

    private String[] Instructions = {"“ A farmer wants to cross a river and take with him a dog, a chicken and a basket of veggies.” " + "\n" + "\n" +
            "1. The farmer is the only one who can sail the boat. He can only take one passenger, in addition to himself. " + "\n" +
            "2. The dog can harm the chicken and the chicken can eat the veggies " + "\n" +
            "3. You can not leave any two crossers on the same bank if they can harm each other " + "\n" + "\n" +
            "How can the farmer get across the river with all the 2 animals and the plant without any losses?"};


    private List<ICrosser> initialCrossers;


    private Story1() throws IOException {

        initialCrossers = new ArrayList<>();
        initialCrossers.add(new Crosser(new Farmer(), -1, true, Crosser.FARMER, "Farmer"));
        initialCrossers.add(new Crosser(new Dog(), -1, false, Crosser.CARNIVOROUS, "Dog"));
        initialCrossers.add(new Crosser(new Chicken(), -1, false, Crosser.HERBIVOROUS, "Chicken"));
        initialCrossers.add(new Crosser(new Veggies(), -1, false, Crosser.PLANT, "Plant"));
    }


    @Override
    public boolean isValid(List<ICrosser> rightBankCrossers, List<ICrosser> leftBankCrossers, List<ICrosser> boatRiders) {
        boolean hasRider = false;
        for (ICrosser rider : boatRiders) {
            if (rider.canSail()) {
                hasRider = true;
                break;
            }
        }
        boolean validLeft = true;
        for (ICrosser currentLeft : leftBankCrossers) {
            if (currentLeft.getEatingRank() == ICrosser.CARNIVOROUS) {
                for (ICrosser others : leftBankCrossers) {
                    if (others.getEatingRank() == ICrosser.HERBIVOROUS) {
                        validLeft = false;
                    }
                }
            }
            if (currentLeft.getEatingRank() == ICrosser.HERBIVOROUS) {
                for (ICrosser others : leftBankCrossers) {
                    if (others.getEatingRank() == ICrosser.PLANT) {
                        validLeft = false;
                    }
                }
            }
        }
        boolean validRight = true;
        for (ICrosser currentLeft : rightBankCrossers) {
            if (currentLeft.getEatingRank() == ICrosser.CARNIVOROUS) {
                for (ICrosser others : rightBankCrossers) {
                    if (others.getEatingRank() == ICrosser.HERBIVOROUS) {
                        validRight = false;
                    }
                }
            }
            if (currentLeft.getEatingRank() == ICrosser.HERBIVOROUS) {
                for (ICrosser others : rightBankCrossers) {
                    if (others.getEatingRank() == ICrosser.PLANT) {
                        validRight = false;
                    }
                }
            }
        }
        return hasRider && validLeft && validRight;
    }

    @Override
    public List<ICrosser> getInitialCrossers() {
        List<ICrosser> clonedCrossers = new ArrayList<>();
        for (ICrosser current : initialCrossers) {
            clonedCrossers.add(current.makeCopy());
        }
        return clonedCrossers;
    }

    @Override
    public String[] getInstructions() {
        return Instructions;
    }

}
