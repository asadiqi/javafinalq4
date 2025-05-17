package com.example.helbhotel.main;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class HELBHotelViewComponents {


    private static final double HBOX_SPACING = 8;
    private static final String COLOR_BORDER = "black";
    private static final int LABEL_WIDTH = 110;
    private static final int LABEL_HEIGHT = 35;
    private static final int COLOR_BOX_WIDTH = 35;
    private static final int COLOR_BOX_HEIGHT = 28;
    private static final int FONT_SIZE_LABEL = 14;
    private static final int FONT_SIZE_BUTTON = 14;
    private static final int BUTTON_PADDING = 10;
    private static final String BUTTON_BG_COLOR_DEFAULT = "#D9E1E8";
    private static final String BUTTON_TEXT_FILL = "black";
    private static final double BUTTON_PREF_WIDTH = 180;
    private static final double BUTTON_PREF_WIDTH_SORT = 100;
    private static final double BUTTON_PREF_HEIGHT = 60;
    private static final double ROOM_BUTTON_WIDTH = 70;
    private static final double ROOM_BUTTON_HEIGHT = 70;
    private static final String ROOM_BUTTON_UNAVAILABLE_COLOR = "#FF0000";
    private static final String LABEL_VERIFY =  "Verify Code";

    private static final String LABEL_STYLE_TEMPLATE =
            "-fx-font-size: %dpx;" +
                    "%s" + // font-weight
                    "-fx-border-color: black;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 4;" +
                    "-fx-background-radius: 4;" +
                    "-fx-padding: 5 10 5 10;";

    private static final String COLOR_BOX_STYLE_TEMPLATE =
            "-fx-background-color: %s; -fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;";

    private static final String BUTTON_STYLE_TEMPLATE =
            "-fx-background-color: %s; -fx-text-fill: %s; -fx-font-size: %dpx; %s -fx-padding: %dpx; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;";

    private static final String ROOM_BUTTON_STYLE_TEMPLATE =
            "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: %dpx;";

    private static final String VERIFY_BUTTON_STYLE = "-fx-font-size: 14px; -fx-font-weight: bold;";

    private static final String RESERVATION_MODE_SELECTOR_STYLE = "-fx-font-size: 12px; -fx-background-radius: 5; -fx-padding: 4 8;";

    public static final Label createLabel(String text, double width, Pos alignment, boolean bold) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setAlignment(alignment);
        String fontWeight = bold ? "-fx-font-weight: bold;" : "";
        String style = String.format(LABEL_STYLE_TEMPLATE, FONT_SIZE_LABEL, fontWeight);
        label.setStyle(style);
        return label;
    }


    public static final HBox createLegend(String text, String color) {
        HBox box = new HBox(HBOX_SPACING);
        box.setAlignment(Pos.CENTER);
        Label lbl = createLabel(text, LABEL_WIDTH, Pos.CENTER, true);
        lbl.setMinHeight(LABEL_HEIGHT);
        Region colorBox = new Region();
        colorBox.setPrefSize(COLOR_BOX_WIDTH, COLOR_BOX_HEIGHT);
        colorBox.setStyle(String.format(COLOR_BOX_STYLE_TEMPLATE, color));
        box.getChildren().addAll(lbl, colorBox);
        return box;
    }

    public static final Button createRoomButton(Room room) {
        Button btn = new Button(room.getName());
        btn.setPrefSize(ROOM_BUTTON_WIDTH, ROOM_BUTTON_HEIGHT);
        btn.setAlignment(Pos.CENTER);
        String bgColor = room.isAvailable() ? room.getColor() : ROOM_BUTTON_UNAVAILABLE_COLOR;
        btn.setStyle(String.format(ROOM_BUTTON_STYLE_TEMPLATE, bgColor, COLOR_BORDER, FONT_SIZE_BUTTON));

        return btn;
    }

    public static final Button createButton(String text, String bgColor, boolean isBold) {
        String safeColor = (bgColor == null || bgColor.isEmpty()) ? BUTTON_BG_COLOR_DEFAULT : bgColor;
        String fontWeight = isBold ? "-fx-font-weight: bold;" : "";
        Button btn = new Button(text);
        btn.setStyle(String.format(BUTTON_STYLE_TEMPLATE, safeColor, BUTTON_TEXT_FILL, FONT_SIZE_BUTTON, fontWeight, BUTTON_PADDING));
        return btn;
    }

    public static final ComboBox<String> createComboBox(List<String> options, EventHandler<ActionEvent> eventHandler) {
        ComboBox<String> sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll(options);
        sortComboBox.getSelectionModel().selectFirst();
        sortComboBox.setOnAction(eventHandler);
        return sortComboBox;
    }

    public static final Button createVerifyCodeButton(EventHandler<ActionEvent> eventHandler) {
        Button verifyButton = new Button(LABEL_VERIFY);
        verifyButton.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
        verifyButton.setStyle(VERIFY_BUTTON_STYLE);
        verifyButton.setOnAction(eventHandler);
        return verifyButton;
    }

    public static final ComboBox<String> createSortByParamComboBox(List<String> options,
                                                                   EventHandler<ActionEvent> eventHandler) {
        ComboBox<String> sortComboBox = createComboBox(options, eventHandler);
        sortComboBox.setPrefWidth(BUTTON_PREF_WIDTH_SORT);
        return sortComboBox;
    }

    public static final ComboBox<String> createReservationModeSelector(List<String> options,
                                                                       EventHandler<ActionEvent> eventHandler) {
        ComboBox<String> modeSelector = createComboBox(options, eventHandler);
        modeSelector.setStyle(RESERVATION_MODE_SELECTOR_STYLE);
        modeSelector.setPrefWidth(BUTTON_PREF_WIDTH);
        return modeSelector;
    }

}
