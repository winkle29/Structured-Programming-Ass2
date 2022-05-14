package comp1110.ass2.gui;

import comp1110.ass2.PiecesOrientation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * A very simple viewer for piece placements in the IQ-Fit game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    // where to find media assets
    private static final String URI_BASE = "assets/";


    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;

    public static String getURL(char character) {

        String output = "";

        switch (character) {

            case ('b'):
                output = "comp1110/ass2/gui/assets/B1.png";
                break;

            case ('B'):
                output = "comp1110/ass2/gui/assets/B2.png";
                break;

            case ('g'):
                output = "comp1110/ass2/gui/assets/G1.png";
                break;

            case ('G'):
                output = "comp1110/ass2/gui/assets/G2.png";
                break;

            case ('i'):
                output = "comp1110/ass2/gui/assets/I1.png";
                break;

            case ('I'):
                output = "comp1110/ass2/gui/assets/I2.png";
                break;

            case ('l'):
                output = "comp1110/ass2/gui/assets/L1.png";
                break;

            case ('L'):
                output = "comp1110/ass2/gui/assets/L2.png";
                break;

            case ('n'):
                output = "comp1110/ass2/gui/assets/N1.png";
                break;

            case ('N'):
                output = "comp1110/ass2/gui/assets/N2.png";
                break;

            case ('o'):
                output = "comp1110/ass2/gui/assets/O1.png";
                break;

            case('O'):
                output = "comp1110/ass2/gui/assets/O2.png";
                break;

            case ('p'):
                output = "comp1110/ass2/gui/assets/P1.png";
                break;

            case ('P'):
                output = "comp1110/ass2/gui/assets/P2.png";
                break;

            case ('r'):
                output = "comp1110/ass2/gui/assets/R1.png";
                break;

            case ('R'):
                output = "comp1110/ass2/gui/assets/R2.png";
                break;

            case ('s'):
                output = "comp1110/ass2/gui/assets/S1.png";
                break;

            case ('S'):
                output = "comp1110/ass2/gui/assets/S2.png";
                break;

            case ('y'):
                output = "comp1110/ass2/gui/assets/Y1.png";
                break;

            case ('Y'):
                output = "comp1110/ass2/gui/assets/Y2.png";
                break;

            case ('X'):
                output = "comp1110/ass2/gui/assets/BlackCircle.png";

        }

        return output;

    }


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */

    void makePlacement(String placement) {

        // board image
        Image image = new Image(Viewer.class.getResourceAsStream("assets/board.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(VIEWER_HEIGHT);
        imageView.setFitWidth(VIEWER_WIDTH);
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);

        // pieces
        int x;
        int y;
        int width;
        int height;
        Rotate rotate = new Rotate(0,0,0);


        for (int i = 0; i < placement.length(); i += 4) {


            // top left of a piece
            x = 60 + (Character.getNumericValue(placement.charAt(i + 1)) * SQUARE_SIZE);
            y = 30 + (Character.getNumericValue(placement.charAt(i + 2)) * SQUARE_SIZE);

            // center of rotation
            width = x + (SQUARE_SIZE * PiecesOrientation.getSpineChar(placement.charAt(i))) / 2;
            height = y + (SQUARE_SIZE);

            Image piece = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource
                    (Viewer.getURL(placement.charAt(i)))).toString(), true);
            ImageView pieceView = new ImageView(piece);

            switch (placement.charAt(i + 3)) {

                case ('N'):
                    rotate = new Rotate(0, width, height);
                    break;

                case ('E'):
                    width = x + SQUARE_SIZE;
                    rotate = new Rotate(90, width, height);
                    break;

                case ('S'):
                    rotate = new Rotate(180, width, height);
                    break;

                case ('W'):
                    height = y + (SQUARE_SIZE * PiecesOrientation.getSpineChar(placement.charAt(i))) / 2;
                    rotate = new Rotate(270, width, height);
                    break;

            }

            pieceView.getTransforms().add(rotate);
            pieceView.setX(x);
            pieceView.setY(y);
            pieceView.setFitWidth(SQUARE_SIZE * PiecesOrientation.getSpineChar(placement.charAt(i)));
            pieceView.setFitHeight(SQUARE_SIZE * 2);

            root.getChildren().add(pieceView);

    }


    }
    // FIXME Task 4: implement the simple placement viewer


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            makePlacement(textField.getText());
            textField.clear();
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FitGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
