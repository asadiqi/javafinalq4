package com.example.helbhotel.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HELBRoomDetailView {
    private Stage popupStage;
    private HELBHotelController controller;
    private VBox leftBox;
    private VBox rightBox;
    private Room room;
    private HELBHotelView view;
    private HELBHotelCheckoutView checkoutView;

    public HELBRoomDetailView(HELBHotelController controller) {
        createScene();
        this.controller = controller;
    }

    private void createScene() {
        this.popupStage = new Stage();
        this.popupStage.initModality(Modality.APPLICATION_MODAL);
        this.popupStage.setTitle("Chambre réservé");

        // Cadre principal
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(0)); // pas de padding

        // Cadre horizontal en haut
        HBox topBoxe = new HBox();
        topBoxe.setAlignment(Pos.CENTER);
        topBoxe.setPadding(new Insets(10));
        topBoxe.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        Label reservationLabel = new Label("Reservation Window");
        topBoxe.getChildren().add(reservationLabel);
        BorderPane.setMargin(topBoxe, new Insets(30, 20, 0, 20));

        HBox bottomBoxes = new HBox();
        bottomBoxes.setAlignment(Pos.CENTER);
        bottomBoxes.setPadding(new Insets(0));
        bottomBoxes.setSpacing(0);

        this.leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(0));
        leftBox.setStyle("-fx-border-color: black; -fx-border-width: 0px 0px 2px 2px;");
        leftBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        this.rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(0));
        rightBox.setStyle("-fx-border-color: black; -fx-border-width: 0px 2px 2px 2px;");
        rightBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(rightBox, Priority.ALWAYS);

        bottomBoxes.getChildren().addAll(leftBox, rightBox);
        bottomBoxes.setPadding(new Insets(0, 20, 20, 20));

        rootLayout.setBottom(new Region());

        rootLayout.setTop(topBoxe);
        rootLayout.setCenter(bottomBoxes);

        Scene popupScene = new Scene(rootLayout, 700, 500);
        this.popupStage.setScene(popupScene);
    }

    private void setupReservationInfo() {

        Reservation reservation = this.room.getReservation();

        // Name and surname
        HBox nameBox = new HBox(10);
        Label nameLabel = new Label("Name, Surname:");
        TextField nameField = new TextField(reservation.getFullName());
        nameField.setEditable(false);
        nameField.setFocusTraversable(false);
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Number of people
        HBox peopleBox = new HBox(10);
        Label peopleLabel = new Label("Number of people (1 to 4):");
        TextField peopleField = new TextField(String.valueOf(reservation.getNumberOfPeople()));
        peopleField.setEditable(false);
        peopleField.setFocusTraversable(false);
        peopleBox.getChildren().addAll(peopleLabel, peopleField);

        // Smoker/Non-smoker
        HBox smokerBox = new HBox(10);
        Label smokerLabel = new Label("Smoker/Non-smoker:");
        TextField smokerField = new TextField(reservation.isSmoker() ? "Smoker" : "Non-smoker");
        smokerField.setEditable(false);
        smokerField.setFocusTraversable(false);
        smokerBox.getChildren().addAll(smokerLabel, smokerField);

        // Reason for stay
        HBox reasonBox = new HBox(10);
        Label reasonLabel = new Label("Reason for stay:");
        TextField reasonField = new TextField(reservation.getStayPurpose());
        reasonField.setEditable(false);
        reasonField.setFocusTraversable(false);
        reasonBox.getChildren().addAll(reasonLabel, reasonField);

        // Number of children
        HBox childrenBox = new HBox(10);
        Label childrenLabel = new Label("Number of children:");
        TextField childrenField = new TextField(String.valueOf(reservation.getNumberOfChildren()));
        childrenField.setEditable(false);
        childrenField.setFocusTraversable(false);
        childrenBox.getChildren().addAll(childrenLabel, childrenField);

        // Add all component rows to the VBox
        leftBox.getChildren().setAll(
                nameBox,
                peopleBox,
                smokerBox,
                reasonBox,
                childrenBox);

        // Set preferred width for all text fields
        nameField.setPrefWidth(150);
        peopleField.setPrefWidth(150);
        smokerField.setPrefWidth(150);
        reasonField.setPrefWidth(150);
        childrenField.setPrefWidth(150);
    }

    private void setupFreeRoom() {

        VBox container = new VBox();

        Button button = HELBHotelViewComponents.createButton("Free room", "#34786F", false);
        button.setOnAction(e -> {
            checkoutView = new HELBHotelCheckoutView(controller);
            checkoutView.openView(room, view);
            popupStage.close();
            // controller.freeRoom(this.room);
            // view.reloadData();
        });

        container.getChildren().addAll(button);
        container.setSpacing(20);

        rightBox.getChildren().setAll(container);

        button.setPrefWidth(100);
        container.setAlignment(Pos.CENTER);
    }

    public void openView(Room room, HELBHotelView view) {
        this.view = view;
        this.room = room;
        setupReservationInfo();
        setupFreeRoom();
        this.popupStage.show();
    }
}