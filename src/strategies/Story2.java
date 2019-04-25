package strategies;

import model.ICrosser;

import java.util.ArrayList;
import java.util.List;

public class Story2 implements ICrossingStrategy {

    private static Story2 ourInstance = new Story2();

    public static Story2 getInstance() {
        return ourInstance;
    }

    private String[] Instructions = {"Four farmers and their animal need to cross a river in a small boat. The weights of the " +
            "farmers are 90 kg, 80 kg,60 kg and 40 kg respectively, and the weight of the animal is 20 kg.”"+ "\n" + "\n" +
            "1. The boat cannot bear a load heavier than 100 kg."+ "\n" +
            "2. All farmers can raft, while the animal cannot."+ "\n" + "\n" +
            "How can they all get to the other side with their animal?"};

    List<ICrosser> initialCrossers = new ArrayList<>();




    private Story2() {
    }

    @Override
    public boolean isValid(List<ICrosser> rightBankCrossers, List<ICrosser> leftBankCrossers, List<ICrosser> boatRiders) {
        return false;
    }

    @Override
    public List<ICrosser> getInitialCrossers() {
        return null;
    }

    @Override
    public String[] getInstructions() {
        return Instructions;
    }

}
