import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/RiverCrossingView.fxml"));
        primaryStage.setTitle("River Crossing Puzzle");
        primaryStage.setScene(new Scene(root, 540, 372));
        primaryStage.getIcons().add(new Image("resources/64px/001-pond.png"));
        primaryStage.show();
    }
}
