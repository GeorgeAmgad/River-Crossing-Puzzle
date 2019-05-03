import controller.RiverCrossingController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Crosser;
import model.ICrosser;
import strategies.ICrossingStrategy;
import strategies.Story1;
import strategies.Story2;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameEngine implements Initializable {

    public Button saveGame;
    public Button newGame;
    public Button undo;
    public Button reset;
    public Button redo;
    public Button instructions;
    public Button solve;
    public Button move;
    public Label moves;
    public Label story;

    public Canvas canvas;

    private static ICrossingStrategy strategy;
    public AnchorPane pane;
    public Label message;

    //positions and images on the canvas
    private GraphicsContext gc;
    private Image world = new Image("resources/River.jpg");
    private List<Position> leftPositions = new ArrayList<>();
    private List<Position> rightPositions = new ArrayList<>();
    private List<Position> leftRaftPositions = new ArrayList<>(2);
    private List<Position> rightRaftPositions = new ArrayList<>(2);
    private Position raftLeft = new Position(84, 64);
    private Position raftRight = new Position(197, 92);
    private Image raft = new Image("resources/raft.png");

    //dummy character
//    Image character = new Image("resources/64px/001-work-mirror.png");
//    Image character0 = new Image("resources/32px/004-dog-mirror.png");
//    Image character1 = new Image("resources/32px/003-hen-mirror.png");
//    Image character2 = new Image("resources/32px/005-harvest.png");


    private RiverCrossingController controller = new RiverCrossingController();

    private List<ICrosser> rightBank;
    private List<ICrosser> leftBank;
    private List<ICrosser> crossers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        solve.setDisable(true);
        controller.newGame(GameEngine.strategy);
        story.setText(getLevelName(strategy));
        moves.setText(String.valueOf(controller.getNumberOfSails()));
        leftBank = new ArrayList<>();
        leftBank = controller.getCrossersOnLeftBank();
        rightBank = controller.getCrossersOnRightBank();
        crossers = new ArrayList<>();

        gc = canvas.getGraphicsContext2D();

        // the following codes regard the static positions on the canvas
        leftPositions.add(new Position(15, 78));
        leftPositions.add(new Position(56, 54));
        leftPositions.add(new Position(99, 38));
        leftPositions.add(new Position(146, 25));
        leftPositions.add(new Position(11, 4));
        leftPositions.add(new Position(71, 2));

        leftRaftPositions.add(new Position(167, 84));
        leftRaftPositions.add(new Position(131, 120));

        rightRaftPositions.add(new Position(278.0, 117.0));
        rightRaftPositions.add(new Position(245.0, 144.0));

        rightPositions.add(new Position(294.0, 202.0));
        rightPositions.add(new Position(336.0, 169.0));
        rightPositions.add(new Position(373.0, 138.0));
        rightPositions.add(new Position(413.0, 109.0));
        rightPositions.add(new Position(451.0, 82.0));
        rightPositions.add(new Position(405.0, 209.0));
        rightPositions.add(new Position(443.0, 159.0));
        // end of applying positions to canvas


        render();
    }

    public void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("level" + getLevelName(strategy));
        alert.setX(500);
        alert.setContentText(strategy.getInstructions()[0]);
        alert.showAndWait();
    }

    static void setStrategy(ICrossingStrategy strategy) {
        GameEngine.strategy = strategy;
    }

    public void newGame() {
        message.setText("");

        List<String> choices = new ArrayList<>();
        choices.add("Level 1");
        choices.add("Level 2");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Level 1", choices);
        dialog.setTitle("River Crossing Game");
        dialog.setHeaderText("please Choose Level");
        dialog.setContentText("level:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> {

                    String levelString = result.get();
                    ICrossingStrategy chosenLevel;
                    switch (levelString) {
                        case "Level 1":
                            chosenLevel = Story1.getInstance();
                            break;

                        case "Level 2":
                            chosenLevel = Story2.getInstance();
                            break;
                        default:
                            System.out.println("couldn't define level. level 1 set as default");
                            chosenLevel = Story1.getInstance();
                    }
                    controller.newGame(chosenLevel);
                    GameEngine.setStrategy(chosenLevel);
                    story.setText(getLevelName(strategy));
                    moves.setText(String.valueOf(controller.getNumberOfSails()));
                    leftBank = new ArrayList<>();
                    leftBank = controller.getCrossersOnLeftBank();
                    rightBank = new ArrayList<>();
                    rightBank = controller.getCrossersOnRightBank();
                    crossers = new ArrayList<>();
                }
        );
        move.setDisable(false);
        render();
    }

    private String getLevelName(ICrossingStrategy strategy) {
        if (strategy instanceof Story1) {
            return "1";
        }
        if (strategy instanceof Story2) {
            return "2";
        }
        return "unknown";
    }

    public void clickSprite(MouseEvent mouseEvent) {

        message.setText("");
//        System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());
//        System.out.println();
        double x = mouseEvent.getX();   //get clicked mouse position X
        double y = mouseEvent.getY();   //get clicked mouse position Y

//        manage clicks on crossers to put on the raft
        if (controller.isBoatOnTheLeftBank() && crossers.size() < 2) {
            for (int i = 0; i < leftBank.size(); i++) {

                double currentX = leftPositions.get(i).x - (leftBank.get(i).getEatingRank() == Crosser.FARMER ? 16.0 : 0);
                double currentY = leftPositions.get(i).y - (leftBank.get(i).getEatingRank() == Crosser.FARMER ? 32.0 : 0);

                double offsetX = leftPositions.get(i).x + 32;
                double offsetY = leftPositions.get(i).y + 32;

                if ((x >= currentX && x < offsetX) && (y >= currentY && y < offsetY)) {
                    crossers.add(leftBank.remove(i));
                    break;
                }
            }
        } else if (!controller.isBoatOnTheLeftBank() && crossers.size() < 2 && !leftBank.isEmpty()) {
            for (int i = 0; i < rightBank.size(); i++) {

                double currentX = rightPositions.get(i).x - (rightBank.get(i).getEatingRank() == Crosser.FARMER ? 16.0 : 0);
                double currentY = rightPositions.get(i).y - (rightBank.get(i).getEatingRank() == Crosser.FARMER ? 32.0 : 0);

                double offsetX = rightPositions.get(i).x + 32;
                double offsetY = rightPositions.get(i).y + 32;

                if ((x >= currentX && x < offsetX) && (y >= currentY && y < offsetY)) {
                    crossers.add(rightBank.remove(i));
                    break;
                }
            }
        }



        //manage clicks on crossers to put back on the shore
        if (controller.isBoatOnTheLeftBank() && crossers.size() > 0) {
            for (int i = 0; i < crossers.size(); i++) {
                double currentX = leftRaftPositions.get(i).x - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 16.0 : 0);
                double currentY = leftRaftPositions.get(i).y - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 32.0 : 0);

                double offsetX = leftRaftPositions.get(i).x + 32;
                double offsetY = leftRaftPositions.get(i).y + 32;

                if ((x >= currentX && x < offsetX) && (y >= currentY && y < offsetY)) {
                    leftBank.add(crossers.remove(i));
                    break;
                }
            }
        } else if (!controller.isBoatOnTheLeftBank() && crossers.size() > 0) {
            for (int i = 0; i < crossers.size(); i++) {
                double currentX = rightRaftPositions.get(i).x - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 16.0 : 0);
                double currentY = rightRaftPositions.get(i).y - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 32.0 : 0);

                double offsetX = rightRaftPositions.get(i).x + 32;
                double offsetY = rightRaftPositions.get(i).y + 32;

                if ((x >= currentX && x < offsetX) && (y >= currentY && y < offsetY)) {
                    rightBank.add(crossers.remove(i));
                    break;
                }
            }
        }

        render();
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.drawImage(world, 0, 0);
        if (controller.isBoatOnTheLeftBank()) {
            gc.drawImage(raft, raftLeft.x, raftLeft.y);
        } else {
            gc.drawImage(raft, raftRight.x, raftRight.y);
        }

        for (int i = 0; i < leftBank.size(); i++) {
            gc.drawImage(SwingFXUtils.toFXImage(leftBank.get(i).getImages()[0], null),
                    leftPositions.get(i).x - (leftBank.get(i).getEatingRank() == Crosser.FARMER && i < 4 ? 16.0 : 0),
                    leftPositions.get(i).y - (leftBank.get(i).getEatingRank() == Crosser.FARMER && i < 4 ? 32.0 : 0));

            gc.fillText(leftBank.get(i).getLabelToBeShown(),
                    leftPositions.get(i).x ,
                    leftPositions.get(i).y + 40 + (leftBank.get(i).getEatingRank() == Crosser.FARMER && i > 4 ? 16 : 0));
        }

        for (int i = 0; i < rightBank.size(); i++) {
            gc.drawImage(SwingFXUtils.toFXImage(rightBank.get(i).getImages()[1], null),
                    rightPositions.get(i).x - (rightBank.get(i).getEatingRank() == Crosser.FARMER ? 16.0 : 0),
                    rightPositions.get(i).y - (rightBank.get(i).getEatingRank() == Crosser.FARMER ? 32.0 : 0));

            gc.fillText(rightBank.get(i).getLabelToBeShown(),
                    rightPositions.get(i).x ,
                    rightPositions.get(i).y + 40 + (rightBank.get(i).getEatingRank() == Crosser.FARMER && i > 4 ? 16 : 0));
        }

        if (controller.isBoatOnTheLeftBank()) {
            for (int i = 0; i < crossers.size(); i++) {
                gc.drawImage(SwingFXUtils.toFXImage(crossers.get(i).getImages()[0], null),
                        leftRaftPositions.get(i).x - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 16.0 : 0),
                        leftRaftPositions.get(i).y - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 32.0 : 0));

                gc.fillText(crossers.get(i).getLabelToBeShown(),
                        leftRaftPositions.get(i).x ,
                        leftRaftPositions.get(i).y + 40 + (crossers.get(i).getEatingRank() == Crosser.FARMER && i > 4 ? 16 : 0));
            }
        } else {
            for (int i = 0; i < crossers.size(); i++) {
                gc.drawImage(SwingFXUtils.toFXImage(crossers.get(i).getImages()[1], null),
                        rightRaftPositions.get(i).x - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 16.0 : 0),
                        rightRaftPositions.get(i).y - (crossers.get(i).getEatingRank() == Crosser.FARMER ? 32.0 : 0));

                gc.fillText(crossers.get(i).getLabelToBeShown(),
                        rightRaftPositions.get(i).x ,
                        rightRaftPositions.get(i).y + 40 + (crossers.get(i).getEatingRank() == Crosser.FARMER && i > 4 ? 16 : 0));
            }
        }

    }

    public void initiateMovement() {
        if (controller.canMove(crossers, controller.isBoatOnTheLeftBank())) {
            controller.doMove(crossers, controller.isBoatOnTheLeftBank());
            update();
            render();
            if (leftBank.isEmpty() && crossers.isEmpty()) {
                message.setText("Well done!");
                move.setDisable(true);
            }
        } else {
            message.setText("Invalid move!");
        }
    }

    private void update() {
        leftBank = controller.getCrossersOnLeftBank();
        rightBank = controller.getCrossersOnRightBank();
        moves.setText(String.valueOf(controller.getNumberOfSails()));

        redo.setDisable(!controller.canRedo());
        undo.setDisable(!controller.canUndo());
    }

    public void saveGame() {
        controller.setTempCrossers(crossers);
        controller.saveGame();
        message.setText("Saved game successfully!");
    }

    public void loadGame() {
        move.setDisable(false);
        controller.loadGame();
        crossers = controller.getTempCrossers();
        message.setText("Loaded game successfully!");
        update();
        render();
    }

    public void undo() {
        if (controller.canUndo()) {
            controller.undo();
        }
        update();
        render();
    }

    public void redo() {
        if (controller.canRedo()) {
            controller.redo();
        }
        update();
        render();
    }

    class Position {
        double x;
        double y;

        Position(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }


}
