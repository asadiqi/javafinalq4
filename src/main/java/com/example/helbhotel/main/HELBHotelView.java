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
    private final String EMPTY_SPACE = "Z";
    private final int BORDER_WIDTH = 2;
    private final int BORDER_RADIUS = 25;
    private final int LABEL_WIDTH = 110;
    private final double BUTTON_PREF_WIDTH = 100;
    private final double BUTTON_PREF_HEIGHT = 60;
    private static final int HBOX_SPACING = 20;
    private static final int FLOOR_SELECTOR_SPACING = 10;
    private static final int FLOOR_SELECTOR_PADDING = 10;
    private static final int SORT_CONTAINER_SPACING = 10;
    private static final int SORT_LABEL_WIDTH = 70;
    private static final int SORT_CONTAINER_WIDTH = 180;
    private static final double GRID_WRAPPER_PADDING = 10;
    private static final String LABEL_FLOOR = "Floor :";
    private static final String LABEL_SORT_BY = "Sort by:";
    private static final List<String> SORT_OPTIONS = Arrays.asList("Name", "Roomnumber");
    private static final double INSET_TOP = 0;
    private static final double INSET_RIGHT = 0;
    private static final double INSET_LEFT = 0;
    private static final double TOPBOX_BOTTOM_PADDING = 10;
    private final int INITIAT_INDEX = 0;
    private final int ALPHABET_NUMBERS = 26;
    private final int FIRST_NUMBER = 1;
    private static final double ROOM_GRID_HGAP = 18;
    private static final double ROOM_GRID_VGAP = 20;
    private static final double ROOM_GRID_PADDING = 10;
    private static final double SPACING_RESERVATION_ACTION_BOXES = 5;
    private static final int SORT_LABEL_FONT_SIZE = 13;
    private static final int SORT_LABEL_BORDER_WIDTH = 1;
    private static final int SORT_LABEL_RADIUS = 5;
    private static final String SORT_LABEL_PADDING = "5 10";
    private HELBHotelController controller;
    private final String FIRST_LETTER="A";
    private static final int BORDER_RADIUS_PANEL = 15;
    private static final String COLOR_PANEL_BACKGROUND = "white";

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


        public HELBHotelView(HELBHotelController controller) {
            this.controller = controller;
            initiateViews();
            setupLegend();
            setupRooms(FIRST_LETTER);
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
                    "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: %d; -fx-border-radius: %d; -fx-background-radius: %d;",
                    COLOR_PANEL_BACKGROUND, COLOR_BORDER, BORDER_WIDTH, BORDER_RADIUS_PANEL, BORDER_RADIUS_PANEL
            ));
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
            gridWrapper.setPadding(new Insets(GRID_WRAPPER_PADDING));
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

            this.topBox = new HBox(HBOX_SPACING);
            topBox.setPadding(new Insets(INSET_TOP, INSET_RIGHT, TOPBOX_BOTTOM_PADDING, INSET_LEFT));
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

            for (int i = INITIAT_INDEX ; i < controller.getRoomsInformation().size(); i++) {
                String[] info = controller.getRoomsInformation().get(i);
                legendBox.getChildren().add(HELBHotelViewComponents.createLegend(info[INITIAT_INDEX], info[FIRST_NUMBER]));
            }

            mainWrapper.getChildren().add(INITIAT_INDEX, legendBox);
        }

        public void setupFloorSelector() {
            HBox box = new HBox(FLOOR_SELECTOR_SPACING);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setPadding(new Insets(FLOOR_SELECTOR_PADDING));
            Label floorLabel = HELBHotelViewComponents.createLabel(LABEL_FLOOR, LABEL_WIDTH, Pos.CENTER,
                    true);
            this.floorSelector = new ComboBox<>();
            floorSelector.setOnAction(e -> {
                String fullLabel = floorSelector.getValue();
                String actualLabel = fullLabel.replaceAll("\\d", ""); // supprime les chiffres (A1 → A, AA → AA)
                setupRooms(actualLabel);

            });
            List<String> floorLabels = controller.getFloorNames();

            for (int i = INITIAT_INDEX; i < floorLabels.size(); i++) {
                String label = floorLabels.get(i);
                String displayLabel = label + (i + 1); // A1, B2, ...

                if (i < ALPHABET_NUMBERS) {
                    displayLabel = label + (i + FIRST_NUMBER); // A1, B2, ..., Z26
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
            grid.setHgap(ROOM_GRID_HGAP);
            grid.setVgap(ROOM_GRID_VGAP);
            grid.setPadding(new Insets(GRID_WRAPPER_PADDING));
            grid.setAlignment(Pos.CENTER);

            Room[][] floor = controller.getFloor(floorLabel);

            for (int row = INITIAT_INDEX; row < floor.length; row++) {
                for (int col = INITIAT_INDEX; col < floor[row].length; col++) {
                    Room room = floor[row][col];

                    if (room.getRoomType().equals(EMPTY_SPACE)) {
                        continue;
                    }

                    Button btn = HELBHotelViewComponents.createRoomButton(room);
                    if (!room.isAvailable()) {
                        btn.setOnAction(e -> HELBRoomDetailView.openView(room, this));
                    }

                    grid.add(btn, col, row);
                }
            }
            StackPane wrapper = (StackPane) leftPanel.getChildren().get(INITIAT_INDEX);
            wrapper.getChildren().setAll(grid);
        }

        private void setupReservationActionBoxes() {
            VBox reservationActionBoxesContainer = new VBox(SPACING_RESERVATION_ACTION_BOXES);
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

            HBox sortContainer = new HBox(SORT_CONTAINER_SPACING);
            sortContainer.setAlignment(Pos.CENTER_LEFT);
            sortContainer.setPrefWidth(SORT_CONTAINER_WIDTH);
            sortContainer.setMaxWidth(SORT_CONTAINER_WIDTH);

            Label sortLabel = HELBHotelViewComponents.createLabel(LABEL_SORT_BY, SORT_LABEL_WIDTH, Pos.CENTER, true);
            sortLabel.setStyle(sortLabel.getStyle() +
                    String.format("-fx-font-size: %dpx;", SORT_LABEL_FONT_SIZE) +
                    String.format("-fx-border-width: %d;", SORT_LABEL_BORDER_WIDTH) +
                    String.format("-fx-border-radius: %d;", SORT_LABEL_RADIUS) +
                    String.format("-fx-background-radius: %d;", SORT_LABEL_RADIUS) +
                    String.format("-fx-padding: %s;", SORT_LABEL_PADDING));


            ComboBox<String> sortComboBox = HELBHotelViewComponents.createSortByParamComboBox(
                    new ArrayList<>(SORT_OPTIONS),
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
            for (int i = INITIAT_INDEX; i < suggestions.size(); i++) {
                RoomAssignmentSuggestion s = suggestions.get(i);
                StringBuilder suggestionName = new StringBuilder();
                suggestionName.append(s.getReservation().getFullName().split(" ")[INITIAT_INDEX].substring(INITIAT_INDEX, FIRST_NUMBER));
                suggestionName.append(".");
                suggestionName.append(s.getReservation().getFullName().split(" ")[FIRST_NUMBER]);
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
            setupRooms(FIRST_LETTER);
            setupSuggestions();
        }
    }

