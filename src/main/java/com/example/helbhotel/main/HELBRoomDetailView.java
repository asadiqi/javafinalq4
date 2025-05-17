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

    private static final double WINDOW_WIDTH = 700;
    private static final double WINDOW_HEIGHT = 500;
    private static final double BORDER_WIDTH_TOP_BOX = 2;
    private static final String BORDER_COLOR = "black";
    private static final double SPACING_HBOX = 10;
    private static final double SPACING_VBOX_CONTAINER = 20;
    private static final double PREF_WIDTH_TEXTFIELDS = 150;
    private static final double PREF_WIDTH_BUTTON = 100;
    private static final String LABEL_RESERVATION = "Room Reserved";
    private static final Insets TOP_BOX_MARGIN = new Insets(30, 20, 0, 20);
    private static final Insets BOTTOM_BOXES_PADDING = new Insets(0, 20, 20, 20);
    private static final String LABEL_NAME_SURNAME = "Name, Surname:";
    private static final String LABEL_NUMBER_PEOPLE = "Number of people (1 to 4):";
    private static final String LABEL_SMOKER = "Smoker/Non-smoker:";
    private static final String TEXT_SMOKER = "Smoker";
    private static final String TEXT_NON_SMOKER = "Non-smoker";
    private static final String LABEL_REASON_STAY = "Reason for stay:";
    private static final String LABEL_NUMBER_CHILDREN = "Number of children:";
    private static final String FREE_ROOM = "Free room";
    private static final String FREE_ROOM_COLOR =  "#34786F";
    private static final int INITAIT_INDEX = 0;
    private Stage popupStage;
    private HELBHotelController controller;
    private VBox leftBox;
    private VBox rightBox;
    private RoomAssignmentSuggestion roomAssignmentSuggestion;
    private int roomAssignmentSuggestionIndex;
    private HELBHotelView view;
    private Room room;
    private HELBHotelCheckoutView checkoutView;



    public HELBRoomDetailView(HELBHotelController controller) {
        createScene();
        this.controller = controller;

    }

    public void openView(Room room, HELBHotelView view) {
        this.view = view;
        this.room = room;
        setupReservationInfo();
        setupFreeRoom();
        this.popupStage.show();
    }

    private void createScene() {
        this.popupStage = new Stage();
        this.popupStage.initModality(Modality.APPLICATION_MODAL);
        this.popupStage.setTitle(LABEL_RESERVATION);

        // Cadre principal
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(INITAIT_INDEX)); // pas de padding

        // Cadre horizontal en haut
        HBox topBoxe = new HBox();
        topBoxe.setAlignment(Pos.CENTER);
        topBoxe.setPadding(new Insets(SPACING_HBOX));
        topBoxe.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        Label reservationLabel = new Label("Reservation Window");
        topBoxe.getChildren().add(reservationLabel);
        BorderPane.setMargin(topBoxe, TOP_BOX_MARGIN);

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
        bottomBoxes.setPadding(BOTTOM_BOXES_PADDING);

        rootLayout.setBottom(new Region());

        rootLayout.setTop(topBoxe);
        rootLayout.setCenter(bottomBoxes);

        Scene popupScene = new Scene(rootLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.popupStage.setScene(popupScene);
    }

    private void setupReservationInfo() {

        Reservation reservation = this.room.getReservation();

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
        nameField.setPrefWidth(PREF_WIDTH_TEXTFIELDS);
        peopleField.setPrefWidth(PREF_WIDTH_TEXTFIELDS);
        smokerField.setPrefWidth(PREF_WIDTH_TEXTFIELDS);
        reasonField.setPrefWidth(PREF_WIDTH_TEXTFIELDS);
        childrenField.setPrefWidth(PREF_WIDTH_TEXTFIELDS);
    }

    private void setupFreeRoom() {

        VBox container = new VBox();

        Button button = HELBHotelViewComponents.createButton(FREE_ROOM, FREE_ROOM_COLOR, false);
        button.setOnAction(e -> {
            checkoutView = new HELBHotelCheckoutView(controller);
            checkoutView.openView(room, view);
            popupStage.close();
            // controller.freeRoom(this.room);
            // view.reloadData();
        });

        container.getChildren().addAll(button);
        container.setSpacing(SPACING_VBOX_CONTAINER);

        rightBox.getChildren().setAll(container);

        button.setPrefWidth(PREF_WIDTH_BUTTON);
        container.setAlignment(Pos.CENTER);
    }


}