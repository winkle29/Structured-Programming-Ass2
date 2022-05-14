package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;
import java.util.*;


public class Board extends Application {

    // dimensions
    private static final int GAME_WIDTH = 933;
    private static final int GAME_HEIGHT = 700;
    private static final double BOARD_SIZE = 434.7;
    private static final int SQUARE_SIZE = 37;
    private static final int BOARD_Y = 40;
    private static final int INDENT = (int) ((GAME_WIDTH/2)-(BOARD_SIZE/2));

    // groups of nodes
    private Group root = new Group();
    private Group controls = new Group();
    private Group solBoard = new Group();
    private Group pieces = new Group();
    private Scene scene;

    // class variables related to texts and strings
    private Text text;
    private Text text1;
    private Text text2;
    private Text text3;
    private Text text4;
    private Text playerText;
    private String name;
    private String placement = "";
    private String solution;
    private TextField textField = new TextField();
    private Label label;

    // class variables for timer
    private int tickTime=1; //sec
    private int timeElapsed=0;
    private int sec=0;
    private int min=0;
    private Text timerText;
    private Timeline timeline;

    // class variables for draggable piece
    int count=0;
    int multiply=0;
    int number = 0;
    double homeX, homeY;        // The position the tiles will snap back to if a movement is not valid.
    double mouseX, mouseY;      // The last known mouse positions (used when dragging)
    double objX, objY;
    int orientation;            // 0 - 3
    boolean mode;               // Boolean is true for mode 1 and false for mode 2
    public String currentPosition;
    public String blackPieces;
    public ArrayList<DraggablePiece> array;


    // FIXME Task 7: Implement a basic playable Fix Game in JavaFX that only allows pieces to be placed in valid places

    // FIXME Task 8: Implement challenges (you may use assets provided for you in comp1110.ass2.gui.assets)

    // FIXME Task 10: Implement hints (should become visible when the user presses '/' -- see gitlab issue for details)

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

//======================Author: Nik Nur Aisyah binti Amran ==============================================
    /**
     * Draw a placement in the window, removing any previously drawn one.
     *
     * @param placement A valid placement string
     */
    private void makePlacement(String placement) {

        hint(scene);

        controls.getChildren().clear();
        root.getChildren().remove(label);
        root.getChildren().remove(text1);
        root.getChildren().remove(text2);
        root.getChildren().remove(text3);
        root.getChildren().remove(text4);

        text3.setText("Rules & Instructions: ");
        text3.setFont(javafx.scene.text.Font.font(Font.DIALOG,18));
        text3.setX(10);
        text3.setY(130);
        root.getChildren().add(text3);
        text4.setText("- Double-click to rotate the piece \n- Use pieces of different colors " +
                "\n  to complete the board\n- For hint, press ' / ' ");
        text4.setFont(javafx.scene.text.Font.font(Font.DIALOG,16));
        text4.setX(10);
        text4.setY(160);
        root.getChildren().add(text4);

        // home button to go to the start screen
        Button home = new Button("Home");
        home.setOnAction(actionEvent -> {
            array.clear();
            pieces.getChildren().clear();
            solBoard.getChildren().clear();
            root.getChildren().clear();
            reset();
            resetPlacement();
            controls.getChildren().clear();
            timeline.stop();
            startScreen();
        });

        // refresh button to start the same challenge from the start
        Button refresh = new Button("Refresh");
        refresh.setOnAction(actionEvent -> {
            solBoard.getChildren().clear();
            pieces.getChildren().clear();
            root.getChildren().removeAll(pieces, solBoard);
            reset();
            timeline.stop();
            if (mode) {
                makePlacement(placement);
                freePieces(placement);
            } else {
                makePlacement(blackPieces);
                makePieces(solution);
            }
        });

        VBox vb = new VBox(home, refresh);
        vb.setLayoutX(GAME_WIDTH-100);
        vb.setLayoutY(20);
        controls.getChildren().add(vb);

        ArrayList<String> pieceOrder = AvailablePiecesArr(placement);

        Collections.sort(pieceOrder);


        // board image
        Image image = new Image(Viewer.class.getResourceAsStream("assets/board.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(BOARD_SIZE);
        imageView.setPreserveRatio(true);
        imageView.setX(INDENT+7);
        imageView.setY(3);
        root.getChildren().add(imageView);

        // player's name
        playerText = new Text(80, 250, "Player: " + name);
        playerText.setX(10);
        playerText.setY(35);
        playerText.setFill(Paint.valueOf("PINK"));
        playerText.setFont(javafx.scene.text.Font.font(Font.DIALOG, 20));
        root.getChildren().add(playerText);

        // starting the timer
        startTimer();

        // pieces
        for (int i = 0; i < placement.length(); i += 4) {

            // black pieces (for new challenges)
            if (placement.charAt(i) == 'X') {

                int xIndex = Character.getNumericValue(placement.charAt(i + 1)) * SQUARE_SIZE;
                int yIndex = Character.getNumericValue(placement.charAt(i + 2)) * SQUARE_SIZE;
                int x = INDENT + SQUARE_SIZE + xIndex;
                int y = 23 + yIndex;

                Image piece = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource
                        (Viewer.getURL(placement.charAt(i)))).toString(), true);
                ImageView pieceView = new ImageView(piece);

                pieceView.setX(x);
                pieceView.setY(y);
                pieceView.setFitHeight(SQUARE_SIZE);
                pieceView.setFitWidth(SQUARE_SIZE);
                pieceView.setPreserveRatio(true);

                solBoard.getChildren().add(pieceView);

            } else {

                // other pieces
                String piece = placement.substring(i, i + 4);
                makeOnePiece(piece);

            }

        }

        root.getChildren().add(solBoard);
    }


    /**
     * This method draws one piece on board.
     * @param piece piece placement in relative to board
     */
    private void makeOnePiece(String piece) {

        if (piece == null)
            return;
        if (piece.length() == 0)
            return;

        // shape of the piece
        char shape = piece.charAt(0);

        // top left of piece
        int x = INDENT + SQUARE_SIZE + (Character.getNumericValue(piece.charAt(1)) * SQUARE_SIZE);
        int y = 23 + (Character.getNumericValue(piece.charAt(2)) * SQUARE_SIZE);

        // center of rotation
        int width = x + (SQUARE_SIZE * PiecesOrientation.getSpineChar(shape) / 2);
        int height = y + SQUARE_SIZE;

        // initial rotation
        Rotate rotate = new Rotate(0, 0, 0);

        Image pieceImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource
                (Viewer.getURL(shape))).toString(), true);
        ImageView pieceView = new ImageView(pieceImage);

        switch (piece.charAt(3)) {

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
                height = y + (SQUARE_SIZE * PiecesOrientation.getSpineChar(shape)) / 2;
                rotate = new Rotate(270, width, height);
                break;

        }

        pieceView.getTransforms().add(rotate);
        pieceView.setX(x);
        pieceView.setY(y);
        pieceView.setFitWidth(SQUARE_SIZE * PiecesOrientation.getSpineChar(shape));
        pieceView.setFitHeight(SQUARE_SIZE * 2);

        solBoard.getChildren().add(pieceView);

    }
//================================================================================================

//==============Author: Prabhjot Kaur Dhawan===========================================================

    // Methods for timer

    /**
     * Initializes the timer
     */
    private void setupTimer(){
        KeyFrame keyframe = new KeyFrame(Duration.millis(tickTime * 1000), (ActionEvent event) -> {
            root.getChildren().remove(timerText);
            update();

        });
        timeline=new Timeline(keyframe);
        timeline.setCycleCount(Animation.INDEFINITE);
    }


    /**
     * This method updates time with every second
     */
    private void update(){
        timeElapsed+=tickTime;
        if(timeElapsed%60==0){
            sec=0;
        }else if(timeElapsed>60){
            sec++;
        }
        else
            sec=timeElapsed;

        min=(int)Math.floor((double)timeElapsed /60);
        int hour=(int)Math.floor((double)timeElapsed /3600);
        timerText=new Text();
        timerText.setFont(javafx.scene.text.Font.font(Font.DIALOG, 20));
        timerText.setX(10);
        timerText.setY(60);
        timerText.setFill(Color.ANTIQUEWHITE);
        String hourS=String.format("%02d",hour);
        String minS=String.format("%02d",min);
        String secS=String.format("%02d",sec);
        timerText.setText(hourS+" : "+minS+" : "+secS);
        root.getChildren().add(timerText);

    }


    public void startTimer(){
        tickTime=1;
        setupTimer();
        timeline.play();

    }


    public void stopTimer(){
       //timeline.stop();
       timeline.pause();
       timeElapsed=0;
       sec=0;
       tickTime=0;
       root.getChildren().remove(timerText);

    }

    /**
     * This method creates an Array of all the available pieces/ not placed pieces
     * on game
     * @param placement which is the objective string of the game
     * @return Array of all the available pieces
     */
   public static ArrayList<String> AvailablePiecesArr(String placement){

       String whole = PiecesOrientation.getAvailablePieces(placement);
       ArrayList<String> All;
       All=Pieces.getPieces(whole);
       return All;

   }
//===================================================================================================
//==================== Author: Prabhjot Kaur Dhawan & Nik Nur Aisyah binti Amran ====================
    /**
     * This inner class is used to place available pieces
     *  and also let us drag and rotate.
     *  Rotation occurs by DOUBLE-CLICKING the mouse
     *
     *  On each DOUBLE-CLICK piece is rotated by 90 degree.
     */
    class DraggablePiece extends ImageView{

        private final int index = number;

        double prevTopX;
        double prevTopY;

        double prevX;
        double prevY;

        double currentX;
        double currentY;

        double topLeftX;
        double topLeftY;

        char shape;
        int rotation = 0;

        double distanceX;
        double distanceY;

        String direction = "N";
        String position;


 //=========================Author:Prabhjot Kaur Dhawan=========================================

        /**
         * This constructor is used to create free draggable pieces
         *
         * @param piecePlacement piece placement is taken as input. For example "B00N"
         */

       DraggablePiece(String piecePlacement){

           if (piecePlacement.equals(""))
               return;

           char piece=piecePlacement.charAt(0);
           this.shape = piece;
           orientation=Pieces.getOrientation(piecePlacement);

           Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource
                   (Viewer.getURL(piece))).toString(), true);
           setImage(image);

            // Available piece placements
           if(count==0){
               homeX=objX=0;
               homeY=objY=269;
               multiply++;
           }
           else if(count>0 && count<=4){
               homeX=objX=190*multiply;
               homeY=objY=269;
               multiply++;
               if(count==4)
                   multiply=0;
           }
           else if(count>4 && count<=9){
               homeX=objX=190*multiply;
               homeY=objY=377;
               multiply++;
               if(count==9)
                   multiply=0;
           }
           else if(count>9 && count<=14){
               homeX=objX=190*multiply;
               homeY=objY=485;
               multiply++;
               if(count==14)
                   multiply=0;
           }
           else if(count>14 && count<=19){
               homeX=objX=190*multiply;
               homeY=objY=593;
               multiply++;
           }
           setFitWidth(SQUARE_SIZE * PiecesOrientation.getSpineChar(piece));
           setFitHeight(SQUARE_SIZE * 2);
           setX(homeX);
           setY(homeY);

           topLeftX = homeX;
           topLeftY = homeY;
           prevTopX = homeX;
           prevTopY = homeY;

           // double-click makes the piece rotate by 90 degree
           setOnMouseClicked(mouseEvent -> {

               if(mouseEvent.getClickCount()%2==0) {
                   setRotate(getRotate() + 90);
//=======================================================================================
//==============Author: Nik Nur Aisyah binti Amran=======================================
                   rotation++;

                   switch (rotation % 4) {
                       case (0):
                           direction = "N";
                           break;
                       case (1):
                           direction = "E";
                           break;
                       case (2):
                           direction = "S";
                           break;
                       case (3):
                           direction = "W";
                           break;
                   }

                   // to update piece position and snap
                   if (direction.equals("E") || direction.equals("W")) {

                       if (PiecesOrientation.getSpineChar(shape) == 3) {
                           setTranslateX(SQUARE_SIZE/2.0);
                           setTranslateY(-SQUARE_SIZE/2.0);

                       }
                       topLeftX += SQUARE_SIZE;
                       topLeftY -= SQUARE_SIZE;
                       prevTopX += SQUARE_SIZE;
                       prevTopY -= SQUARE_SIZE;

                   } else {

                       if (PiecesOrientation.getSpineChar(shape) == 3) {
                           setTranslateX(0);
                           setTranslateY(0);
                       }
                       topLeftX -= SQUARE_SIZE;
                       topLeftY += SQUARE_SIZE;
                       prevTopX -= SQUARE_SIZE;
                       prevTopY += SQUARE_SIZE;

                   }
                   setCurrentPosition();

               }

           });
//========================================================================================
//==================Author:Prabhjot Kaur Dhawan===========================================

           // mouse press indicates begin of drag
           setOnMousePressed(event -> {
              mouseX = event.getSceneX();
              mouseY = event.getSceneY();

              prevX = mouseX;
              prevY = mouseY;

           });

           // mouse is being dragged
           setOnMouseDragged(event -> {
               toFront();
               double movementX = event.getSceneX() - mouseX;
               double movementY = event.getSceneY() - mouseY;
               setLayoutX(getLayoutX() + movementX);
               setLayoutY(getLayoutY() + movementY);
               mouseX = event.getSceneX();
               mouseY = event.getSceneY();
               event.consume();

           });

           // mouse is being released
           setOnMouseReleased(event -> {

               currentX = event.getSceneX();
               currentY = event.getSceneY();

               distanceX = currentX - prevX;
               distanceY = currentY - prevY;

               topLeftX += distanceX;
               topLeftY += distanceY;

               setCurrentPosition();

           });
       }
//========================================================================================
//==============Author: Nik Nur Aisyah binti Amran========================================
        /**
         * This method applies other methods to get the board's current placement.
         */
       private void setCurrentPosition() {

           position = getPosition();

           if (mode) {

               if (FitGame.isPlacementValid(updateCurrentPosition(currentPosition, position))) {
                   snapToGrid();
               } else {
                   snapToHome();
               }

           } else {

               if (FitGame.isPlacementValidNew(position, currentPosition, solution)) {
                   snapToGrid();
               } else {
                   snapToHome();
               }
           }

           if (currentPosition.equals(solution)) {
               finalScreen();
           }

       }


        /**
         * This method snaps piece to closest grid if it is valid and snaps to origin if it is invalid.
         */
       private void snapToGrid() {

           ArrayList<Double> gridX = new ArrayList<>(
                   Arrays.asList(286.15, 323.15, 360.15, 395.15, 432.15, 469.15, 506.15, 543.15, 580.15)
           );

           ArrayList<Integer> gridY = new ArrayList<>(
                   Arrays.asList(23, 60, 97, 134, 171)
           );

           double newX = 0;
           for (double x : gridX) {

               double distance = Math.abs(topLeftX - x);
               if (distance < SQUARE_SIZE / 2.0) {
                   newX = x - prevTopX;
               }
           }

           double newY = 0;
           for (int y : gridY) {

               double distance = Math.abs(topLeftY - y);
               if (distance < SQUARE_SIZE / 2.0) {
                   newY = y - prevTopY;
               }
           }

           if (newX == 0 || newY == 0){
               snapToHome();

           } else {

               setLayoutX(newX);
               setLayoutY(newY);

               DraggablePiece pair;

               if (index < array.size()/2) {
                   pair = array.get(index + array.size()/2);
               } else {
                   pair = array.get(index - array.size()/2);
               }

               pieces.getChildren().remove(pair);

           }
           currentPosition = updateCurrentPosition(currentPosition, position);

       }


        /**
         * This method snaps piece to its origin.
         */
       private void snapToHome() {

           topLeftX = prevTopX;
           topLeftY = prevTopY;
           setLayoutX(0);
           setLayoutY(0);

           DraggablePiece pair;

           if (index < array.size()/2) {
               pair = array.get(index + array.size()/2);
           } else {
               pair = array.get(index - array.size()/2);
           }

           currentPosition = removePosition(currentPosition, position);

           pieces.getChildren().remove(pair);
           pieces.getChildren().add(pair);

       }

        /**
         * This method gets the position of piece in relative to board placement.
         * @return piece's current position
         */
        public String getPosition() {
           int x = (int) Math.round((topLeftX - INDENT - SQUARE_SIZE) / SQUARE_SIZE);
           int y = (int) Math.round((topLeftY - 25) / SQUARE_SIZE);
           return String.valueOf(shape) + x + y + direction;
        }
   }


    /**
     * This method gets the current placement of the board, by adding the piece's new position.
     * @param currentPosition board's current placement
     * @param position new piece position
     * @return board's current placement
     */
    public static String updateCurrentPosition(String currentPosition, String position) {

        // extreme cases
        if (currentPosition == null || position == null)
            return "";
        if (position.length() == 0)
            return currentPosition;
        if (currentPosition.length() == 0)
            return position;

        char shape = position.charAt(0);
        if (!FitGame.isPiecePlacementWellFormed(position)) { return removePosition(currentPosition, position); }

        String output;
        if (currentPosition.contains(String.valueOf(shape))) {
            int start = currentPosition.indexOf(shape);
            int end = start;
            for (int i = start + 1; i < currentPosition.length()
                    && !Character.isLetter(currentPosition.charAt(i)); i++) {
                end = i + 2;
            }

            StringBuilder sb = new StringBuilder(currentPosition);
            sb.delete(start, end);
            output = arrangeCurrentPlacement(sb.toString(), position);

        } else {

            output = arrangeCurrentPlacement(currentPosition, position);
        }

        return output;
    }

    /**
     * This method removes the piece's position from board's placement if it is snapped to its origin.
     * @param currentPosition board's current placement
     * @param position new piece position
     * @return board's current placement
     */
    public static String removePosition(String currentPosition, String position) {

        if (currentPosition == null || currentPosition.length() == 0)
            return "";

        String output = currentPosition;
        char shape = position.charAt(0);

        if (currentPosition.contains(String.valueOf(shape))) {
            int start = currentPosition.indexOf(shape);
            int end = start;

            for (int i = start + 1; i < currentPosition.length()
                    && !Character.isLetter(currentPosition.charAt(i)); i++) {
                end = i + 2;
            }

            StringBuilder sb = new StringBuilder(currentPosition);
            sb.delete(start, end);
            output = sb.toString();

        }
        return output;
    }

    /**
     * This method add the piece's position according to its order in board's placement.
     * @param placement board's current placement
     * @param position new piece position
     * @return arranged board's current placement
     */
    public static String arrangeCurrentPlacement(String placement, String position) {

        String output;
        char shape = position.charAt(0);

        PiecesName piece = PiecesName.getName(Character.toUpperCase(shape));
        assert piece != null;
        int place = piece.ordinal();
        int index = 0;
        int split = 0;

        if (placement.length() >= 4) {

            PiecesName lastPiece = PiecesName.getName(Character.toUpperCase(placement.charAt(placement.length()-4)));
            assert lastPiece != null;

            if (place > lastPiece.ordinal()) {
                output = placement.concat(position);

            } else {

                for (int i = 0; i < placement.length() && place > index; i += 4) {

                    PiecesName name = PiecesName.getName(Character.toUpperCase(placement.charAt(i)));
                    assert name != null;
                    index = name.ordinal();
                    split = i;
                }

                output = placement.substring(0, split).concat(position).concat(placement.substring(split));

            }

        } else {

            output = position;
        }

        return output;

    }
//====================================================================================================

//=========Author: Nik Nur Aisyah binti Amran & Prabhjot Kaur Dhawan==================================
    /**
     * This method takes the objective string and from that gets available pieces.
     * String for each piece placement is saved in an array and then used in DraggablePiece inner class
     *
     * @param placement string for given objective
     */
   private void freePieces(String placement){
       currentPosition = placement;
       ArrayList<String> availablePieces = AvailablePiecesArr(placement);

       array = new ArrayList<>();

       for (int i = 0; i < availablePieces.size()/2; i ++) {
           array.add(new DraggablePiece((availablePieces.get(i))));
           count++;
           number++;
       }

       count = 10;
       multiply = 0;

       for (int i = availablePieces.size()/2; i < availablePieces.size(); i ++){
           array.add(new DraggablePiece(availablePieces.get(i)));
           count++;
           number++;
       }

       pieces.getChildren().addAll(array);
       root.getChildren().add(pieces);

   }
//========================================================================================

//==========Author: Nik Nur Aisyah binti Amran============================================
    /**
     * This method creates new draggable pieces given a string of draggable pieces.
     * @param placement string of draggable pieces
     */
   private void makePieces(String placement){
       currentPosition = "";
       String listOne = placement.toUpperCase();
       String listTwo = placement.toLowerCase();

       array = new ArrayList<>();

       for (int i = 0; i < listOne.length(); i += 4){
           String sub = listOne.substring(i, i + 4);
           array.add(new DraggablePiece(sub));
           count++;
           number++;
       }

       count = 10;
       multiply = 0;

       for (int i = 0; i < listTwo.length(); i += 4){
           String sub = listTwo.substring(i, i + 4);
           array.add(new DraggablePiece(sub));
           count++;
           number++;
       }

       pieces.getChildren().addAll(array);
       root.getChildren().add(pieces);

   }
//===========================================================================================

//=======Author: Prabhjot Kaur Dhawan & Nik Nur Aisyah binti Amran ==========================
    /**
     * This method generates hint after clicking on slash key.
     * @param scene current scene
     */

    private void hint(Scene scene){
        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()==KeyCode.SLASH){
                if (currentPosition == null || solution == null )
                    return;
                if (solution.length() == 0)
                    return;

                String nextPiece = Pieces.getNextPiece(currentPosition, solution);
                currentPosition = updateCurrentPosition(currentPosition, nextPiece);
                if (currentPosition.equals(solution)){
                    finalScreen();
                }

                makeOnePiece(nextPiece);
                removePairs(nextPiece);

                keyEvent.consume();

            }
        });
    }
//=========================================================================================

//===========Author: Nik Nur Aisyah binti Amran============================================
    private void removePairs (String piecePlacement) {
        if (piecePlacement == null || piecePlacement.length() == 0)
            return;

        char colour = piecePlacement.charAt(0);
        for(DraggablePiece d : array) {
            if (d.shape == Character.toUpperCase(colour) || d.shape == Character.toLowerCase(colour))
                pieces.getChildren().remove(d);
        }
    }
//=========================================================================================

//============Author: Prabhjot Kaur Dhawan=================================================
    /**
     * This method resets the game after clicking home/refresh button.
     * It resets time and piece count for available piece as well
     */
   private void reset(){
       homeX=objX=0;
       homeY=objY=0;
       sec=0;
       multiply=0;
       count=0;
       timeElapsed=0;
       number = 0;
   }

   private void resetPlacement(){
       currentPosition = "";
       placement = "";
       solution = "";
       blackPieces = "";
   }

    /**
     * Second screen after clicking on Mode 1 button (basic challenges), consists of a slider to
     * determine the game's difficulty.
     */
    private void makeScreenOne() {

        controls.getChildren().clear();
        root.getChildren().remove(text);

        Button home = new Button("Home");
        home.setOnAction(actionEvent -> {

            root.getChildren().clear();
            controls.getChildren().clear();
            startScreen();

        });

        home.setLayoutX(GAME_WIDTH-100);
        home.setLayoutY(30);
        controls.getChildren().add(home);

        // Slider
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(5);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(1);
        slider.setSnapToTicks(true);
        slider.valueProperty().addListener((observableValue, old_val, new_val) -> {
            int check = new_val.intValue();
            Games game = Games.newGame(check);
            placement = game.getInitialState();
            solution = game.getSolution();

        });

        slider.setLayoutX(GAME_WIDTH/2.0 );
        slider.setLayoutY(GAME_HEIGHT -(BOARD_Y+50));
        controls.getChildren().add(slider);

        label = new Label("Difficulty:");
        label.setTextFill(Color.PINK);
        label.setLayoutX(GAME_WIDTH/2.0-100);
        label.setLayoutY(GAME_HEIGHT - (BOARD_Y+50));

        controls.getChildren().add(label);

        // Instructions
        text1 = new Text(255, 100, "Please select a difficulty level: ");
        text1.setFill(Paint.valueOf("PINK"));
        text1.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 38));
        root.getChildren().add(text1);

        text2 = new Text();
        text2.setText(" 1-Starter\n 2-Junior\n 3-Expert\n 4-Master\n 5-Wizard");
        text2.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 20));
        text2.setFill(Color.PINK);
        text2.setX(GAME_WIDTH/2.0-50);
        text2.setY(160);
        root.getChildren().add(text2);

        text3 = new Text(280, 490, "Rules & Instructions: ");
        text3.setFill(Color.ANTIQUEWHITE);
        text3.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 35));
        root.getChildren().add(text3);

        text4 = new Text(280, 530, "- Double-click to rotate the piece \n- Use pieces of different colors " +
                "to complete the board\n- For hint, press ' / ' ");
        text4.setFill(Color.ANTIQUEWHITE);
        text4.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 20));
        root.getChildren().add(text4);


        // Button to start the game
        Button button = new Button("Let's Start!");
        button.setOnAction(actionEvent -> {

            mode = true;
            name = textField.getText();
            makePlacement(placement);
            freePieces(placement);

        });

        HBox hb = new HBox();
        hb.getChildren().addAll(label, slider,button);
        hb.setSpacing(10);
        hb.setLayoutX(330);
        hb.setLayoutY(355);
        controls.getChildren().add(hb);

    }
//=======================================================================================



//==============Author: Nik Nur Aisyah binti Amran=======================================
    /**
     * Second screen after clicking on Mode 2 button (interesting challenges).
     *
     */
    private void makeScreenTwo() {

        controls.getChildren().clear();
        root.getChildren().remove(text);

        text3 = new Text(280, 410, "Rules & Instructions: ");
        text3.setFill(Color.ANTIQUEWHITE);
        text3.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 35));
        root.getChildren().add(text3);

        text4 = new Text(280, 450, "- Double-click to rotate the piece \n- Use pieces of different colors " +
                "to complete the board\n- For hint, press ' / ' ");
        text4.setFill(Color.ANTIQUEWHITE);
        text4.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 20));
        root.getChildren().add(text4);

        Button home = new Button("Home");
        home.setOnAction(actionEvent -> {

            root.getChildren().clear();
            controls.getChildren().clear();
            startScreen();
            reset();
            resetPlacement();

        });

        home.setLayoutX(GAME_WIDTH-100);
        home.setLayoutY(30);
        controls.getChildren().add(home);

        Button button = new Button("Let's Start!");
        button.setOnAction(actionEvent -> {

            mode = false;
            currentPosition = "";

            Random random = new Random();
            int next = random.nextInt(10);

            NewChallenges newChallenges = NewChallenges.getNewChallenge(next);
            blackPieces = newChallenges.getInitialState();
            solution = newChallenges.getPlacement();

            makePlacement(blackPieces);
            makePieces(solution);

            String str = newChallenges.getShape();
            Text text = new Text(10, 90, "Shape: " + str);
            text.setFill(Paint.valueOf("BLACK"));
            text.setFont(javafx.scene.text.Font.font(Font.DIALOG, 19));
            root.getChildren().add(text);


        });

        HBox hb = new HBox();
        hb.getChildren().addAll(button);
        hb.setSpacing(10);
        hb.setLayoutX(430);
        hb.setLayoutY(250);
        controls.getChildren().add(hb);

    }

    /**
     * This method is the controls for the user to enter their name and to choose game's mode.
     */
    private void makeControls() {

        Label label = new Label("Enter your name");
        textField.setPrefWidth(250);

        // button for basic challenges
        Button button1 = new Button("Mode 1");
        button1.setOnAction(actionEvent -> {
            name = textField.getText();
            makeScreenOne();
        });

        // button for interesting challenges
        Button button2 = new Button("Mode 2");
        button2.setOnAction(actionEvent -> {
            name = textField.getText();
            makeScreenTwo();
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(label, textField, button1, button2);
        hb.setSpacing(10);
        hb.setLayoutX(200);
        hb.setLayoutY(400);
        controls.getChildren().add(hb);

    }

    /**
     * This method is the main method for when the game starts.
     * @param primaryStage main stage
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("FitGame Board");
        scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Paint.valueOf("GRAY"));
        startScreen();

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * This method creates the starting screen.
     */
    public void startScreen() {

        // starting text
        text = new Text(80, 250, "Welcome to FitGame");
        text.setFill(Paint.valueOf("PINK"));
        text.setFont(javafx.scene.text.Font.font(Font.SANS_SERIF, FontWeight.EXTRA_BOLD, 80));
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);
        root.getChildren().remove(timerText);
        root.getChildren().add(controls);
        makeControls();

    }
//========================================================================================


//=============Author: Nik Nur Aisyah binti Amran & Prabhjot Kaur Dhawan==================
    public void finalScreen() {

        String timeTaken = timerText.getText();
        String player = name;
        controls.getChildren().clear();
        root.getChildren().removeAll(text3, text4, playerText, timerText);
        reset();
        stopTimer();

        // home button
        Button home = new Button("Home");
        home.setOnAction(actionEvent -> {

            array.clear();
            pieces.getChildren().clear();
            solBoard.getChildren().clear();
            root.getChildren().clear();
            controls.getChildren().clear();
            reset();
            resetPlacement();
            startScreen();

        });

        home.setLayoutX(GAME_WIDTH-100);
        home.setLayoutY(20);
        controls.getChildren().add(home);
        root.getChildren().remove(timerText);

        text = new Text(250, 400, "Well Done! "+player);
        text.setFill(Paint.valueOf("PINK"));
        text.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 60));
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);

        text = new Text(310, 480, "Board Completed!");
        text.setFill(Paint.valueOf("PINK"));
        text.setFont(javafx.scene.text.Font.font(Font.SANS_SERIF, FontWeight.SEMI_BOLD, 35));
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);

        if(sec<60 && min<1 )
            text = new Text(160, 550, " You took " + timeTaken + " seconds to finish the game" );
        else
            text = new Text(160, 550, " You took " + timeTaken + " minutes to finish the game" );
        text.setFill(Color.ANTIQUEWHITE);
        text.setFont(javafx.scene.text.Font.font("Apple Chancery", FontWeight.EXTRA_BOLD, 30));
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);

    }

    public static void main(String[] args) {
        launch(args);

    }
}
//============================================================================================
