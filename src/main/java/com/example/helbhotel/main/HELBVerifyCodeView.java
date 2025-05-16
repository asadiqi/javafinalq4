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
    private Stage popupStage;
    private HELBHotelController controller;
    private HBox topBox;
    private HBox bottomBox;

    public HELBVerifyCodeView(HELBHotelController controller) {
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

    private void setupCodeVerification() {
        Label verificationLabel = new Label("Verify code!");
        topBox.getChildren().setAll(verificationLabel);

        VBox box = new VBox(10);
        Label infoCodeLabel = new Label("Please enter the code (10 chars)");
        TextField codeField = new TextField();
        codeField.setPromptText("***********");

        Button btn = HELBHotelViewComponents.createButton("Validate", null, false);
        box.getChildren().addAll(infoCodeLabel, codeField, btn);

        Image imageNotValidCode = new Image(getClass().getResourceAsStream("/assets/not_valid.png"));
        ImageView noValidView = new ImageView(imageNotValidCode);

        Image imageValidCode = new Image(getClass().getResourceAsStream("/assets/valid.png"));
        ImageView validView = new ImageView(imageValidCode);

        btn.setOnAction(e -> {
            try {
                int discount = controller.getDiscount(codeField.getText()); // this code could return null and in this
                // case an error happens
                Label discountLabel = new Label("Your discount in is ".concat(String.valueOf(discount)).concat("%"));
                box.getChildren().setAll(discountLabel, validView);
            } catch (Exception err) {
                Label notValidLabel = new Label("Code not valid");
                box.getChildren().setAll(notValidLabel, noValidView);
            }
        });

        bottomBox.getChildren().setAll(box);
    }

    public void openView() {
        setupCodeVerification();
        this.popupStage.show();
    }
}
