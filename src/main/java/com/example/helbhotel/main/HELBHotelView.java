package com.example.helbhotel.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HELBHotelView {

    private final double WINDOW_WIDTH = 900;
    private final double WINDOW_HEIGHT = 650;
    private final double PADDING_GENERAL = 20;
    private final double SPACING_MAIN_WRAPPER = 20;
    private final double SPACING_MAIN_CONTENT = 20;
    private final double SPACING_LEGEND_BOX = 30;
    private final double SPACING_BUTTON_PANEL = 10;
    private final double PADDING_MAIN_CONTENT = 10;
    private final double PADDING_LEGEND = 10;
    private final double PANEL_MIN_WIDTH = 200;
    private final double PANEL_PREF_HEIGHT = 400;
    private final double SCROLLPANE_PREF_HEIGHT = 400;
    private final String COLOR_BACKGROUND = "#F8F8F8";
    private final String COLOR_BORDER = "black";
    private final int BORDER_WIDTH = 2;
    private final int BORDER_RADIUS = 25;
    private final int LABEL_WIDTH = 110;
    private final double BUTTON_PREF_WIDTH = 100;
    private final double BUTTON_PREF_HEIGHT = 60;

        private HELBHotelController controller;
        public Scene scene;
        private VBox root;
        private VBox mainWrapper;
        private HBox mainContent;
        public VBox leftPanel;
        private VBox rightPanel;
        private VBox buttonPanel;
        private HBox topBox;
        private ComboBox<String> floorSelector;
        private ComboBox<String> reservationModeSelector;
        private HELBReservationDetailView HELBReservationDetailView;
        private HELBRoomDetailView HELBRoomDetailView;
        private HELBVerifyCodeView HELBVerifyCodeView;
        private ComboBox<String> sortComboBox;

        public HELBHotelView(HELBHotelController controller) {
            this.controller = controller;
            initiateViews();
            setupLegend();
            setupRooms("A");
            setupFloorSelector();
            setupReservationActionBoxes();
            setupSuggestions();
            HELBReservationDetailView = new HELBReservationDetailView(controller);
            HELBRoomDetailView = new HELBRoomDetailView(controller);
            HELBVerifyCodeView = new HELBVerifyCodeView(controller);
            this.scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        }

        private void initiateViews() {
            root = new VBox();
            root.setPadding(new Insets(PADDING_GENERAL));

            VBox wrapper = new VBox();
            wrapper.setPadding(new Insets(PADDING_GENERAL));
            wrapper.setSpacing(SPACING_MAIN_WRAPPER);
            wrapper.setStyle(String.format(
                    "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: %d; -fx-background-radius: %d;",
                    COLOR_BACKGROUND, COLOR_BORDER, BORDER_WIDTH,
                    BORDER_RADIUS, BORDER_RADIUS));

            wrapper.setMaxWidth(Double.MAX_VALUE);
            wrapper.setMaxHeight(Double.MAX_VALUE);
            VBox.setVgrow(wrapper, Priority.ALWAYS);
            this.mainWrapper = wrapper;

            HBox content = new HBox();
            content.setSpacing(SPACING_MAIN_CONTENT);
            content.setPadding(new Insets(PADDING_MAIN_CONTENT));
            VBox.setVgrow(content, Priority.ALWAYS);
            this.mainContent = content;

            VBox panel = new VBox();
            panel.setStyle(String.format(
                    "-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;",
                    COLOR_BORDER, BORDER_WIDTH));
            panel.setMinWidth(PANEL_MIN_WIDTH);
            panel.setPrefHeight(PANEL_PREF_HEIGHT);
            panel.setAlignment(Pos.CENTER);

            this.leftPanel = new VBox();
            this.leftPanel.setStyle(String.format(
                    "-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;",
                    COLOR_BORDER, BORDER_WIDTH));
            this.leftPanel.setMinWidth(PANEL_MIN_WIDTH);
            this.leftPanel.setPrefHeight(PANEL_PREF_HEIGHT);
            this.leftPanel.setAlignment(Pos.CENTER);
            StackPane gridWrapper = new StackPane();
            gridWrapper.setPrefSize(PANEL_MIN_WIDTH, PANEL_PREF_HEIGHT);
            gridWrapper.setPadding(new Insets(10));
            this.leftPanel.getChildren().add(gridWrapper);
            HBox.setHgrow(this.leftPanel, Priority.ALWAYS);

            this.rightPanel = new VBox();
            this.rightPanel.setStyle(String.format(
                    "-fx-background-color: white; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: 15; -fx-background-radius: 15;",
                    COLOR_BORDER, BORDER_WIDTH));
            this.rightPanel.setMinWidth(PANEL_MIN_WIDTH);
            this.rightPanel.setPrefHeight(PANEL_PREF_HEIGHT);
            this.rightPanel.setAlignment(Pos.CENTER);
            this.buttonPanel = new VBox(SPACING_BUTTON_PANEL);
            this.buttonPanel.setAlignment(Pos.CENTER);
            ScrollPane rightScrollPane = new ScrollPane(buttonPanel);
            rightScrollPane.setFitToWidth(true);
            rightScrollPane.setPrefHeight(SCROLLPANE_PREF_HEIGHT);
            rightPanel.getChildren().add(rightScrollPane);

            this.topBox = new HBox(20);
            topBox.setPadding(new Insets(0, 0, 10, 0));
            topBox.setAlignment(Pos.CENTER_LEFT);

            mainContent.getChildren().addAll(this.leftPanel, this.rightPanel);
            mainWrapper.getChildren().addAll(this.topBox, this.mainContent);
            ScrollPane outerScroll = new ScrollPane(mainWrapper);
            outerScroll.setFitToWidth(true);
            outerScroll.setFitToHeight(true);
            root.getChildren().add(outerScroll);
        }

        public void setupLegend() {
            HBox legendBox = new HBox(SPACING_LEGEND_BOX);
            legendBox.setPadding(new Insets(PADDING_LEGEND));
            legendBox.setAlignment(Pos.CENTER_LEFT);

            for (int i = 0; i < controller.getRoomsInformation().size(); i++) {
                String[] info = controller.getRoomsInformation().get(i);
                legendBox.getChildren().add(HELBHotelViewComponents.createLegend(info[0], info[1]));
            }

            mainWrapper.getChildren().add(0, legendBox);
        }

        public void setupFloorSelector() {
            HBox box = new HBox(10);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setPadding(new Insets(10));
            Label floorLabel = HELBHotelViewComponents.createLabel("Floor :", LABEL_WIDTH, Pos.CENTER,
                    true);
            this.floorSelector = new ComboBox<>();
            floorSelector.setOnAction(e -> {
                String fullLabel = floorSelector.getValue();
                String actualLabel = fullLabel.replaceAll("\\d", ""); // supprime les chiffres (A1 → A, AA → AA)
                setupRooms(actualLabel);

            });
            List<String> floorLabels = controller.getFloorNames();

            for (int i = 0; i < floorLabels.size(); i++) {
                String label = floorLabels.get(i);
                String displayLabel = label + (i + 1); // A1, B2, ...

                if (i < 26) {
                    displayLabel = label + (i + 1); // A1, B2, ..., Z26
                } else {
                    displayLabel = label; // AA, AB, ...
                }

                floorSelector.getItems().add(displayLabel);
            }

            floorSelector.getSelectionModel().selectFirst();
            box.getChildren().addAll(floorLabel, floorSelector);
            HBox.setHgrow(box, Priority.ALWAYS);
            box.setMaxWidth(Double.MAX_VALUE);
            topBox.getChildren().add(box);
        }

        public void setupRooms(String floorLabel) {
            GridPane grid = new GridPane();
            grid.setHgap(18);
            grid.setVgap(20);
            grid.setPadding(new Insets(10));
            grid.setAlignment(Pos.CENTER);

            Room[][] floor = controller.getFloor(floorLabel);

            for (int row = 0; row < floor.length; row++) {
                for (int col = 0; col < floor[row].length; col++) {
                    Room room = floor[row][col];

                    if (room.getRoomType().equals("Z")) {
                        continue;
                    }

                    Button btn = HELBHotelViewComponents.createRoomButton(room);
                    if (!room.isAvailable()) {
                        btn.setOnAction(e -> HELBRoomDetailView.openView(room, this));
                    }

                    grid.add(btn, col, row);
                }
            }
            StackPane wrapper = (StackPane) leftPanel.getChildren().get(0);
            wrapper.getChildren().setAll(grid);
        }

        private void setupReservationActionBoxes() {
            VBox reservationActionBoxesContainer = new VBox(5);
            reservationActionBoxesContainer.setAlignment(Pos.CENTER_RIGHT);

            Button verifyCodeButton = HELBHotelViewComponents.createVerifyCodeButton(null);
            verifyCodeButton.setOnAction(e -> {
                HELBVerifyCodeView.openView();
            });

            this.reservationModeSelector = HELBHotelViewComponents.createReservationModeSelector(
                    controller.getStrategyNames(),
                    e -> {
                        String selectedMode = reservationModeSelector.getValue();
                        controller.setAssignmentStrategy(selectedMode);
                        controller.getSuggestions(); // la stratégie est mise à jour
                        setupSuggestions(); // recharge la liste dans la vue
                    });

            HBox sortContainer = new HBox(10);
            sortContainer.setAlignment(Pos.CENTER_LEFT);
            sortContainer.setPrefWidth(180);
            sortContainer.setMaxWidth(180);

            Label sortLabel = HELBHotelViewComponents.createLabel("Sort by:", 70, Pos.CENTER, true);
            sortLabel.setStyle(sortLabel.getStyle() +
                    "-fx-font-size: 13px;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 5 10;");

            ComboBox<String> sortComboBox = HELBHotelViewComponents.createSortByParamComboBox(
                    new ArrayList<>(Arrays.asList("Name", "Roomnumber")),
                    null);
            sortComboBox.setOnAction(e -> {
                String selected = sortComboBox.getValue();
                controller.sortSuggestionsBy(selected);
                setupSuggestions(); // recharge la liste triée
            });


            sortContainer.getChildren().addAll(sortLabel, sortComboBox);

            reservationActionBoxesContainer.getChildren().addAll(verifyCodeButton, reservationModeSelector, sortContainer);
            topBox.getChildren().add(reservationActionBoxesContainer);
        }

        public void setupSuggestions() {
            List<RoomAssignmentSuggestion> suggestions = controller.getSuggestions();
            buttonPanel.getChildren().clear();
            for (int i = 0; i < suggestions.size(); i++) {
                RoomAssignmentSuggestion s = suggestions.get(i);
                StringBuilder suggestionName = new StringBuilder();
                suggestionName.append(s.getReservation().getFullName().split(" ")[0].substring(0, 1));
                suggestionName.append(".");
                suggestionName.append(s.getReservation().getFullName().split(" ")[1]);
                suggestionName.append("\n");
                suggestionName.append(s.getRoom().getName());

                Button btn = HELBHotelViewComponents.createButton(suggestionName.toString(),
                        s.getRoom().getColor(), false);
                btn.setPrefSize(BUTTON_PREF_WIDTH,
                        BUTTON_PREF_HEIGHT);

                int index = i; // required for lambda expressions
                btn.setOnAction(e -> HELBReservationDetailView.openView(s, this, index));
                buttonPanel.getChildren().add(btn);
            }

        }

        public void reloadData() {
            setupRooms("A");
            setupSuggestions();
        }
    }

