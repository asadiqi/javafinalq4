package com.example.helbhotel.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HELBVerifyCodeView {

    private static final double WINDOW_WIDTH = 700;
    private static final double WINDOW_HEIGHT = 500;
    private static final double SPACING_VBOX = 10;
    private static final String LABEL_VERIFY_CODE = "Verify code!";
    private static final Insets TOP_BOX_MARGIN = new Insets(30, 20, 0, 20);
    private static final String LABEL_10_CHARS = "Please enter the code (10 chars)";
    private static final String VALIDATE = "Validate ";
    private static final String DISCOUNT = "Your discount in is ";
    private static final String NOTE_VALIDE = "Code not valid";
    private static final int INITAIT_INDEX = 0;
    private Stage popupStage;
    private HELBHotelController controller;
    private RoomAssignmentSuggestion roomAssignmentSuggestion;
    private HELBHotelCheckoutView checkoutView;
    private HBox topBox;
    private HBox bottomBox;

    public HELBVerifyCodeView(HELBHotelController controller) {
        this.controller = controller;
        createScene();
    }

    public void openView() {
        setupCodeVerification();
        this.popupStage.show();
    }


    private void createScene() {
        this.popupStage = new Stage();
        this.popupStage.initModality(Modality.APPLICATION_MODAL);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(INITAIT_INDEX)); // pas de padding

        topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(SPACING_VBOX ));
        topBox.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        BorderPane.setMargin(topBox, TOP_BOX_MARGIN );

        bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(INITAIT_INDEX));
        bottomBox.setSpacing(INITAIT_INDEX);

        rootLayout.setBottom(new Region());
        rootLayout.setTop(topBox);
        rootLayout.setCenter(bottomBox);

        Scene popupScene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.popupStage.setScene(popupScene);
    }

    private void setupCodeVerification() {
        Label verificationLabel = new Label(LABEL_VERIFY_CODE);
        topBox.getChildren().setAll(verificationLabel);

        VBox box = new VBox(SPACING_VBOX);
        Label infoCodeLabel = new Label(LABEL_10_CHARS);
        TextField codeField = new TextField();
        codeField.setPromptText("***********");

        Button btn = HELBHotelViewComponents.createButton(VALIDATE, null, false);
        box.getChildren().addAll(infoCodeLabel, codeField, btn);


        btn.setOnAction(e -> {
            try {
                int discount = controller.getDiscount(codeField.getText()); // this code could return null and in this
                // case an error happens
                Label discountLabel = new Label(DISCOUNT.concat(String.valueOf(discount)).concat("%"));
                box.getChildren().setAll(discountLabel);
            } catch (Exception err) {
                Label notValidLabel = new Label(NOTE_VALIDE);
                box.getChildren().setAll(notValidLabel);
            }
        });

        bottomBox.getChildren().setAll(box);
    }


}
