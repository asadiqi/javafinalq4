package com.example.helbhotel.main;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HELBHotelCheckoutView {

    private Stage popupStage;
    private HELBHotelController controller;
    private HBox topBox;
    private HBox bottomBox;
    private Room room;
    private Ticket ticket;
    private HELBHotelView view;

    private final String BORDER_STYLE = "-fx-border-color: black; -fx-border-width: 2px;";
    private final Insets TOP_BOX_MARGIN = new Insets(30, 20, 0, 20);
    private final int SCENE_WIDTH = 700;
    private final int SCENE_HEIGHT = 500;

    private final String CONFIRM_LABEL_TEXT = "Confirm free room";
    private final String CONFIRM_QUESTION_TEXT = "Are you sure you want to free the room?";
    private final String CONFIRM_BUTTON_TEXT = "confirm action";
    private final String CONFIRM_BUTTON_COLOR = "#FF0000";
    private final String DISMISS_BUTTON_TEXT = "dismiss action";
    private final String DISMISS_BUTTON_COLOR = "#B0B0B0";

    private final String RATE_US_LABEL = "Rate us";
    private final String RATE_PROMPT = "5";
    private final String RATE_INSTRUCTION = "Rate us from 1 to 5";
    private final String INVALID_VALUE_TEXT = "Invalid value";

    private final String BRONZE_GAME_LABEL = "Game with doors!";
    private final String LEFT_DOOR_LABEL = "Left door";
    private final String RIGHT_DOOR_LABEL = "Right door";

    private final String SILVER_GAME_LABEL = "Game with words!";
    private final String GUESS_WORD_LABEL = "Guess word";
    private final String SCRAMBLED_WORD = "srac";
    private final String CORRECT_WORD = "cars";
    private final String VALIDATE_BUTTON_TEXT = "Validate";

    private final String BYE_LABEL = "Bye!";
    private final String OK_BUTTON_TEXT = "OK";
    private final String WIN_CODE_LABEL = "Here is the code for your ";
    private final String LOST_TEXT = "Ah damn you didn't win!";

    private final int VBOX_SPACING = 10;
    private final int HBOX_SPACING = 20;
    private final int TOPBOX_PADDING = 10;
    private final int BOTTOMBOX_PADDING = 0;
    private final int RATE_MIN = 1;
    private final int RATE_MAX = 5;
    private final int RANDOM_BOUND = 2;
    private final int MIN_PADDING = 0;
    private final int RANDOMINDEX_MIN = 0;
    private final int RANDOMINDEX_MAX = 1;
    private final String BRONZE_LABEL =  "bronze";
    private final String SILVER_LABEL =  "silver";




    public HELBHotelCheckoutView(HELBHotelController controller) {
        this.controller = controller;
        createScene();
    }

    private void createScene() {
        this.popupStage = new Stage();
        this.popupStage.initModality(Modality.APPLICATION_MODAL);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(MIN_PADDING)); // pas de padding

        topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(TOPBOX_PADDING));
        topBox.setStyle(BORDER_STYLE);
        BorderPane.setMargin(topBox, TOP_BOX_MARGIN);

        bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(MIN_PADDING));
        bottomBox.setSpacing(MIN_PADDING);

        rootLayout.setBottom(new Region());
        rootLayout.setTop(topBox);
        rootLayout.setCenter(bottomBox);

        Scene popupScene = new Scene(rootLayout, SCENE_WIDTH, SCENE_HEIGHT);
        this.popupStage.setScene(popupScene);
    }

    private void confirmCheckout() {
        Label reservationLabel = new Label(CONFIRM_LABEL_TEXT);
        topBox.getChildren().setAll(reservationLabel);

        VBox box = new VBox(VBOX_SPACING);
        Label checkoutLabel = new Label(CONFIRM_QUESTION_TEXT);
        Button button = HELBHotelViewComponents.createButton(CONFIRM_BUTTON_TEXT, CONFIRM_BUTTON_COLOR, false);
        Button button2 = HELBHotelViewComponents.createButton(DISMISS_BUTTON_TEXT, DISMISS_BUTTON_COLOR, false);
        box.setAlignment(Pos.CENTER);

        button.setOnAction(e -> {
            controller.freeRoom(room);
            setupRate();
        });

        button2.setOnAction(e -> {
            popupStage.close();
        });

        box.getChildren().addAll(checkoutLabel, button, button2);
        bottomBox.getChildren().setAll(box);
    }

    public void setupRate() {
        Label topLabel = new Label(RATE_US_LABEL);
        topBox.getChildren().setAll(topLabel);

        VBox box = new VBox(VBOX_SPACING);

        Label checkoutLabel = new Label(RATE_INSTRUCTION);
        Label validationLabel = new Label(INVALID_VALUE_TEXT);
        validationLabel.setVisible(false);

        TextField rateField = new TextField();
        rateField.setPromptText(RATE_PROMPT);
        rateField.setOnKeyPressed(value -> {
            try {
                // Stop event when keypress is a shift and num_lock as it could already trigger
                // invalidity
                if (value.getCode() == KeyCode.SHIFT || value.getCode() == KeyCode.NUM_LOCK)
                    return;

                int rate = Integer.parseInt(value.getText());

                if (rate < RATE_MIN  || rate > RATE_MAX) {
                    rateField.setText(null);
                    validationLabel.setVisible(true);
                    return;
                }
                validationLabel.setVisible(false);
                this.ticket = controller.getTicket(room.getRoomType(), rate);
                System.out.println("this.ticket.getTicketColor()");
                System.out.println(this.ticket.getTicketColor());
                setupGame();
            } catch (NumberFormatException e) {
                rateField.setText(null);
                validationLabel.setVisible(true);
            }
        });
        box.setAlignment(Pos.CENTER);
        box.getChildren().setAll(checkoutLabel, rateField, validationLabel);
        bottomBox.getChildren().setAll(box);

    }

    public void setupGame() {
        if (ticket.getTicketColor() == BRONZE_LABEL) {
            setupBronzeGame();
        } else if (ticket.getTicketColor() == SILVER_LABEL) {
            setupSilverGame();
        }
        else {
            setupBronzeGame();
        }
    }

    public void setupBronzeGame() {
        Label topLabel = new Label(BRONZE_GAME_LABEL);
        topBox.getChildren().setAll(topLabel);

        VBox vbox = new VBox();
        Label gameLabel = new Label(BRONZE_GAME_LABEL);

        HBox box = new HBox(HBOX_SPACING);
        Button leftDoor = HELBHotelViewComponents.createButton(LEFT_DOOR_LABEL, null, false);
        Button rightDoor = HELBHotelViewComponents.createButton(RIGHT_DOOR_LABEL, null, false);

        Random random = new Random();
        int randomIndex = random.nextInt(RANDOM_BOUND);

        leftDoor.setOnAction(e -> {
            lastScreen(randomIndex == RANDOMINDEX_MIN);
        });

        rightDoor.setOnAction(e -> {
            lastScreen(randomIndex == RANDOMINDEX_MAX);
        });

        box.getChildren().addAll(leftDoor, rightDoor);
        vbox.getChildren().addAll(gameLabel, box);
        bottomBox.getChildren().setAll(vbox);
    }

    public void setupSilverGame() {
        Label reservationLabel = new Label(SILVER_GAME_LABEL);
        topBox.getChildren().setAll(reservationLabel);

        VBox box = new VBox(VBOX_SPACING);
        Label infoWordLabel = new Label(GUESS_WORD_LABEL);
        Label wordLabel = new Label(SCRAMBLED_WORD);

        TextField wordField = new TextField();
        wordField.setPromptText("word");

        Button btn = HELBHotelViewComponents.createButton(VALIDATE_BUTTON_TEXT, null, false);
        btn.setOnAction(e -> {
            lastScreen(wordField.getText().equals(CORRECT_WORD));
        });

        box.getChildren().addAll(infoWordLabel, wordLabel, wordField, btn);
        bottomBox.getChildren().setAll(box);
    }

    public void lastScreen(Boolean showcode) {
        Label topLabel = new Label(BYE_LABEL);
        topBox.getChildren().setAll(topLabel);

        VBox containerBox = new VBox(VBOX_SPACING);

        Button btn = HELBHotelViewComponents.createButton(OK_BUTTON_TEXT, "", false);

        btn.setOnAction(e -> {
            this.popupStage.close();
            this.view.reloadData();
        });

        if (showcode) {
            VBox codeBox = new VBox(VBOX_SPACING);
            Label codeInfoLabel = new Label(WIN_CODE_LABEL);
            String code = controller.generateCode(this.ticket);
            TextField codeLabel = new TextField(code);
            codeLabel.setEditable(false);
            codeBox.getChildren().addAll(codeInfoLabel, codeLabel);
            containerBox.getChildren().addAll(codeBox, btn);
        } else {

            VBox lostBox = new VBox(VBOX_SPACING);
            Label lostLabel = new Label(LOST_TEXT);
            containerBox.getChildren().addAll(lostBox, lostLabel, btn);
        }

        this.bottomBox.getChildren().setAll(containerBox);

    }

    public void openView(Room room, HELBHotelView view) {
        this.room = room;
        this.view = view;
        confirmCheckout();
        this.popupStage.show();
    }
}