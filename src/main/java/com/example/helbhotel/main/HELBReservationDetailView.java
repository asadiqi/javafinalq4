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

    private static final double WINDOW_WIDTH = 700;
    private static final double WINDOW_HEIGHT = 500;
    private static final double PADDING_TOP_BOX = 10;
    private static final double BORDER_WIDTH_TOP_BOX = 2;
    private static final String BORDER_COLOR = "black";
    private static final double SPACING_HBOX = 10;
    private static final double SPACING_VBOX_CONTAINER = 20;
    private static final double PREF_WIDTH_LABELS = 150;
    private static final double PREF_WIDTH_TEXTFIELDS = 150;
    private static final double PREF_WIDTH_BUTTON_CONFIRM = 100;
    private static final String TOP_BOX_STYLE = String.format("-fx-border-color: %s; -fx-border-width: %dpx;", BORDER_COLOR, (int)BORDER_WIDTH_TOP_BOX);
    private static final String LEFT_BOX_STYLE = String.format("-fx-border-color: %s; -fx-border-width: 0px 0px 2px 2px;", BORDER_COLOR);
    private static final String RIGHT_BOX_STYLE = String.format("-fx-border-color: %s; -fx-border-width: 0px 2px 2px 2px;", BORDER_COLOR);
    private static final String LABEL_DETAIL_RESERVATION = "Détail de la Réservation";
    private static final String LABEL_RESERVATION_WINDOW = "Reservation Window";
    private static final String ROOM_PROPOSITION = "Room proposition:";
    private static final String BUTTON_COLOR_CONFIRM = "#34786F";
    private static final String BUTTON_COLOR_NOT_AVAILABLE = "#808080";
    private static final Insets TOP_BOX_MARGIN = new Insets(30, 20, 0, 20);
    private static final Insets BOTTOM_BOXES_PADDING = new Insets(0, 20, 20, 20);
    private static final String LABEL_NAME_SURNAME = "Name, Surname:";
    private static final String LABEL_NUMBER_PEOPLE = "Number of people (1 to 4):";
    private static final String LABEL_SMOKER = "Smoker/Non-smoker:";
    private static final String TEXT_SMOKER = "Smoker";
    private static final String TEXT_NON_SMOKER = "Non-smoker";
    private static final String LABEL_REASON_STAY = "Reason for stay:";
    private static final String CONFIRM = "Confirm";
    private static final String NOT_AVALIBALE = "Not available";
    private static final String LABEL_NUMBER_CHILDREN = "Number of children:";
    private static final int INITAIT_INDEX = 0;
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
        this.popupStage.setTitle(LABEL_DETAIL_RESERVATION);

        // Cadre principal
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(INITAIT_INDEX)); // pas de padding

        // Cadre horizontal en haut
        HBox topBoxe = new HBox();
        topBoxe.setAlignment(Pos.CENTER);
        topBoxe.setPadding(new Insets(PADDING_TOP_BOX));
        topBoxe.setStyle(TOP_BOX_STYLE);
        Label reservationLabel = new Label(LABEL_RESERVATION_WINDOW);
        topBoxe.getChildren().add(reservationLabel);
        BorderPane.setMargin(topBoxe, TOP_BOX_MARGIN);

        HBox bottomBoxes = new HBox();
        bottomBoxes.setAlignment(Pos.CENTER);
        bottomBoxes.setPadding(new Insets(INITAIT_INDEX));
        bottomBoxes.setSpacing(INITAIT_INDEX);

        this.leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(INITAIT_INDEX));
        leftBox.setStyle(LEFT_BOX_STYLE);
        leftBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        this.rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(INITAIT_INDEX));
        rightBox.setStyle(RIGHT_BOX_STYLE);
        rightBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(rightBox, Priority.ALWAYS);

        bottomBoxes.getChildren().addAll(leftBox, rightBox);
        bottomBoxes.setPadding(BOTTOM_BOXES_PADDING);

        rootLayout.setBottom(new Region());

        rootLayout.setTop(topBoxe);
        rootLayout.setCenter(bottomBoxes);

        Scene popupScene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.popupStage.setScene(popupScene);
    }

    private void setupReservationInfo() {

        Reservation reservation = this.roomAssignmentSuggestion.getReservation();

        // Name and surname
        HBox nameBox = new HBox(SPACING_HBOX);
        Label nameLabel = new Label(LABEL_NAME_SURNAME);
        TextField nameField = new TextField(reservation.getFullName());
        nameField.setEditable(false);
        nameField.setFocusTraversable(false);
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Number of people
        HBox peopleBox = new HBox(SPACING_HBOX);
        Label peopleLabel = new Label(LABEL_NUMBER_PEOPLE);
        TextField peopleField = new TextField(String.valueOf(reservation.getNumberOfPeople()));
        peopleField.setEditable(false);
        peopleField.setFocusTraversable(false);
        peopleBox.getChildren().addAll(peopleLabel, peopleField);

        // Smoker/Non-smoker
        HBox smokerBox = new HBox(SPACING_HBOX);
        Label smokerLabel = new Label(LABEL_SMOKER);
        TextField smokerField = new TextField(reservation.isSmoker() ? TEXT_SMOKER : TEXT_NON_SMOKER);
        smokerField.setEditable(false);
        smokerField.setFocusTraversable(false);
        smokerBox.getChildren().addAll(smokerLabel, smokerField);

        // Reason for stay
        HBox reasonBox = new HBox(SPACING_HBOX);
        Label reasonLabel = new Label(LABEL_REASON_STAY);
        TextField reasonField = new TextField(reservation.getStayPurpose());
        reasonField.setEditable(false);
        reasonField.setFocusTraversable(false);
        reasonBox.getChildren().addAll(reasonLabel, reasonField);

        // Number of children
        HBox childrenBox = new HBox(SPACING_HBOX);
        Label childrenLabel = new Label(LABEL_NUMBER_CHILDREN);
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
        nameField.setPrefWidth(PREF_WIDTH_LABELS);
        peopleField.setPrefWidth(PREF_WIDTH_LABELS);
        smokerField.setPrefWidth(PREF_WIDTH_LABELS);
        reasonField.setPrefWidth(PREF_WIDTH_LABELS);
        childrenField.setPrefWidth(PREF_WIDTH_LABELS);
    }

    private void setupBookingActions() {

        ComboBox<String> roomComboBox = new ComboBox<>();

        VBox container = new VBox();

        roomComboBox.getItems().addAll(controller.getRooms());
        roomComboBox.setValue(roomAssignmentSuggestion.getRoom().getName());

        HBox roomBox = new HBox(SPACING_HBOX);
        Label propositionLabel = new Label(ROOM_PROPOSITION);
        Button button = HELBHotelViewComponents.createButton(CONFIRM, BUTTON_COLOR_CONFIRM, false);
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
                secondButton = HELBHotelViewComponents.createButton(CONFIRM, BUTTON_COLOR_CONFIRM,
                        false);
                secondButton.setDisable(false);
            } else {
                secondButton = HELBHotelViewComponents.createButton(NOT_AVALIBALE,
                        BUTTON_COLOR_NOT_AVAILABLE, false);
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
            secondButton.setPrefWidth(PREF_WIDTH_BUTTON_CONFIRM);
            container.setAlignment(Pos.CENTER);
            container.getChildren().setAll(roomBox, secondButton);
        });

        container.getChildren().addAll(roomBox, button);
        container.setSpacing(SPACING_VBOX_CONTAINER);

        rightBox.getChildren().setAll(container);

        propositionLabel.setPrefWidth(PREF_WIDTH_TEXTFIELDS);
        roomComboBox.setPrefWidth(PREF_WIDTH_TEXTFIELDS);
        button.setPrefWidth(PREF_WIDTH_BUTTON_CONFIRM);
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