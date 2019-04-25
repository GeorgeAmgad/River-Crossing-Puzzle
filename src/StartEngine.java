import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import strategies.ICrossingStrategy;
import strategies.Story1;
import strategies.Story2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StartEngine implements Initializable {


    public ChoiceBox<String> levelList;
    public Button start;
    public AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelList.getItems().add("Level 1");
        levelList.getItems().add("Level 2");

        levelList.setValue("Level 1");
    }

    public void startGame(ActionEvent actionEvent) throws IOException {
        GameEngine.setStrategy(getChosenLevel());
        AnchorPane root = FXMLLoader.load(getClass().getResource("resources/RiverCrossingView.fxml"));
        pane.getChildren().setAll(root);
    }

    private ICrossingStrategy getChosenLevel() {
        String chosenLevel = levelList.getValue();
        switch (chosenLevel) {
            case "Level 1":
                return Story1.getInstance();

            case "Level 2":
                return Story2.getInstance();
            default:
                System.out.println("couldn't define level. level 1 set as default");
                return Story1.getInstance();
        }

    }

}
