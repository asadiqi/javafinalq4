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

    public HELBHotelCheckoutView(HELBHotelController controller) {
        this.controller = controller;
        createScene();
    }

    private void createScene() {
        this.popupStage = new Stage();
        this.popupStage.initModality(Modality.APPLICATION_MODAL);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(0)); // pas de padding

        topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));
        topBox.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        BorderPane.setMargin(topBox, new Insets(30, 20, 0, 20));

        bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(0));
        bottomBox.setSpacing(0);

        rootLayout.setBottom(new Region());
        rootLayout.setTop(topBox);
        rootLayout.setCenter(bottomBox);

        Scene popupScene = new Scene(rootLayout, 700, 500);
        this.popupStage.setScene(popupScene);
    }

    private void confirmCheckout() {
        Label reservationLabel = new Label("Confirm free room");
        topBox.getChildren().setAll(reservationLabel);

        VBox box = new VBox(10);
        Label checkoutLabel = new Label("Are you sure you want to free the room?");
        Button button = HELBHotelViewComponents.createButton("confirm action", "#FF0000", false);
        Button button2 = HELBHotelViewComponents.createButton("dismiss action", "#B0B0B0", false);
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
        Label topLabel = new Label("Rate us");
        topBox.getChildren().setAll(topLabel);

        VBox box = new VBox(10);

        Label checkoutLabel = new Label("Rate us from 1 to 5");
        Label validationLabel = new Label("Invalid value");
        validationLabel.setVisible(false);

        TextField rateField = new TextField();
        rateField.setPromptText("5");
        rateField.setOnKeyPressed(value -> {
            try {
                // Stop event when keypress is a shift and num_lock as it could already trigger
                // invalidity
                if (value.getCode() == KeyCode.SHIFT || value.getCode() == KeyCode.NUM_LOCK)
                    return;

                int rate = Integer.parseInt(value.getText());

                if (rate < 1 || rate > 5) {
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
        if (ticket.getTicketColor() == "bronze") {
            setupBronzeGame();
        } else if (ticket.getTicketColor() == "silver") {
            setupSilverGame();
        }
        else {
            setupBronzeGame();
        }
    }

    public void setupBronzeGame() {
        Label topLabel = new Label("Game with doors!");
        topBox.getChildren().setAll(topLabel);

        VBox vbox = new VBox();
        Label gameLabel = new Label("Game with doors!");

        HBox box = new HBox(20);
        Button leftDoor = HELBHotelViewComponents.createButton("Left door", null, false);
        Button rightDoor = HELBHotelViewComponents.createButton("Right door", null, false);

        Random random = new Random();
        int randomIndex = random.nextInt(2);

        leftDoor.setOnAction(e -> {
            lastScreen(randomIndex == 0);
        });

        rightDoor.setOnAction(e -> {
            lastScreen(randomIndex == 1);
        });

        box.getChildren().addAll(leftDoor, rightDoor);
        vbox.getChildren().addAll(gameLabel, box);
        bottomBox.getChildren().setAll(vbox);
    }

    public void setupSilverGame() {
        Label reservationLabel = new Label("Game with words!");
        topBox.getChildren().setAll(reservationLabel);

        VBox box = new VBox(10);
        Label infoWordLabel = new Label("Guess word");
        Label wordLabel = new Label("srac");

        TextField wordField = new TextField();
        wordField.setPromptText("word");

        Button btn = HELBHotelViewComponents.createButton("Validate", null, false);
        btn.setOnAction(e -> {
            lastScreen(wordField.getText().equals("cars"));
        });

        box.getChildren().addAll(infoWordLabel, wordLabel, wordField, btn);
        bottomBox.getChildren().setAll(box);
    }

    public void lastScreen(Boolean showcode) {
        Label topLabel = new Label("Bye!");
        topBox.getChildren().setAll(topLabel);

        VBox containerBox = new VBox(10);

        Button btn = HELBHotelViewComponents.createButton("OK", "", false);

        btn.setOnAction(e -> {
            this.popupStage.close();
            this.view.reloadData();
        });

        if (showcode) {
            VBox codeBox = new VBox(10);
            Label codeInfoLabel = new Label("Here is the code for your ");
            String code = controller.generateCode(this.ticket);
            TextField codeLabel = new TextField(code);
            codeLabel.setEditable(false);
            codeBox.getChildren().addAll(codeInfoLabel, codeLabel);
            containerBox.getChildren().addAll(codeBox, btn);
        } else {

            VBox lostBox = new VBox(10);
            Label lostLabel = new Label("Ah damn you didn't win!");
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