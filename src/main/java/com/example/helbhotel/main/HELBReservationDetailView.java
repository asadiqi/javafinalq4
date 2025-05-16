package com.example.helbhotel.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HELBReservationDetailView {

    private Stage popupStage;
    private HELBHotelController controller;
    private VBox leftBox;
    private VBox rightBox;
    private RoomAssignmentSuggestion roomAssignmentSuggestion;
    private int roomAssignmentSuggestionIndex;
    private HELBHotelView view;

    public HELBReservationDetailView(HELBHotelController controller) {
        createScene();
        this.controller = controller;
    }

    private void createScene() {
        this.popupStage = new Stage();
        this.popupStage.initModality(Modality.APPLICATION_MODAL);
        this.popupStage.setTitle("Détail de la Réservation");

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

        Reservation reservation = this.roomAssignmentSuggestion.getReservation();

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

    private void setupBookingActions() {

        ComboBox<String> roomComboBox = new ComboBox<>();

        VBox container = new VBox();

        roomComboBox.getItems().addAll(controller.getRooms());
        roomComboBox.setValue(roomAssignmentSuggestion.getRoom().getName());

        HBox roomBox = new HBox(10);
        Label propositionLabel = new Label("Room proposition:");
        Button button = HELBHotelViewComponents.createButton("Confirm", "#34786F", false);
        button.setOnAction(e -> {
            controller.confirmReservation(roomAssignmentSuggestion, roomAssignmentSuggestionIndex);
            view.reloadData();
            popupStage.close();
        });
        roomBox.getChildren().addAll(propositionLabel, roomComboBox);

        roomComboBox.setOnAction(event -> {
            String roomName = roomComboBox.getValue();
            Button secondButton;

            if (controller.isRoomAvailable(roomName)) {
                secondButton = HELBHotelViewComponents.createButton("Confirm", "#34786F",
                        false);
                secondButton.setDisable(false);
            } else {
                secondButton = HELBHotelViewComponents.createButton("Not available",
                        "#808080", false);
                secondButton.setDisable(true);
            }

            Room room = controller.getRoomByName(roomName);
            RoomAssignmentSuggestion modifiedSuggestion = new RoomAssignmentSuggestion(room,
                    roomAssignmentSuggestion.getReservation());

            secondButton.setOnAction(e -> {
                controller.confirmReservation(modifiedSuggestion, roomAssignmentSuggestionIndex);
                view.reloadData();
                popupStage.close();
            });
            secondButton.setPrefWidth(100);
            container.setAlignment(Pos.CENTER);
            container.getChildren().setAll(roomBox, secondButton);
        });

        container.getChildren().addAll(roomBox, button);
        container.setSpacing(20);

        rightBox.getChildren().setAll(container);

        propositionLabel.setPrefWidth(150);
        roomComboBox.setPrefWidth(150);
        button.setPrefWidth(100);
        container.setAlignment(Pos.CENTER);
    }

    public void openView(RoomAssignmentSuggestion roomAssignmentSuggestion, HELBHotelView view,
                         int roomAssignmentSuggestionIndex) {
        this.roomAssignmentSuggestion = roomAssignmentSuggestion;
        this.view = view;
        this.roomAssignmentSuggestionIndex = roomAssignmentSuggestionIndex;
        setupReservationInfo();
        setupBookingActions();
        this.popupStage.show();
    }

}