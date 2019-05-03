package strategies;

import model.Crosser;
import model.ICrosser;
import model.visuals.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Story2 implements ICrossingStrategy {

    private static Story2 ourInstance;

    static {
        try {
            ourInstance = new Story2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Story2 getInstance() {
        return ourInstance;
    }

    private String[] Instructions = {"Four farmers and their animal need to cross a river in a small boat. The weights of the " +
            "farmers are 90 kg, 80 kg,60 kg and 40 kg respectively, and the weight of the animal is 20 kg.”"+ "\n" + "\n" +
            "1. The boat cannot bear a load heavier than 100 kg."+ "\n" +
            "2. All farmers can raft, while the animal cannot."+ "\n" + "\n" +
            "How can they all get to the other side with their animal?"};

    private List<ICrosser> initialCrossers;




    private Story2() throws IOException {
        initialCrossers = new ArrayList<>();
        initialCrossers.add(new Crosser(new Farmer(), 90, true, Crosser.FARMER, "90 kg"));
        initialCrossers.add(new Crosser(new Farmer2(), 60, true, Crosser.FARMER, "60 kg"));
        initialCrossers.add(new Crosser(new Farmer(), 80, true, Crosser.FARMER, "80 kg"));
        initialCrossers.add(new Crosser(new Farmer2(), 40, true, Crosser.FARMER, "40 kg"));
        initialCrossers.add(new Crosser(new Sheep(), 20, false, Crosser.HERBIVOROUS, "20 kg"));

    }

    @Override
    public boolean isValid(List<ICrosser> rightBankCrossers, List<ICrosser> leftBankCrossers, List<ICrosser> boatRiders) {
        double weight = 0;
        boolean hasRider = false;
        for (ICrosser crosser : boatRiders) {
            weight += crosser.getWeight();
            if (crosser.canSail()) {
                hasRider = true;
            }
        }
        return !(weight > 100) && hasRider;
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
