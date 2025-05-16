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

    public static final Label createLabel(String text, double width, Pos alignment, boolean bold) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setAlignment(alignment);
        String style = "-fx-font-size: 14px;" +
                (bold ? "-fx-font-weight: bold;" : "") +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;" +
                "-fx-padding: 5 10 5 10;";
        label.setStyle(style);
        return label;
    }

    public static final HBox createLegend(String text, String color) {
        HBox box = new HBox(HELBHotelViewStyle.HBOX_SPACING);
        box.setAlignment(Pos.CENTER);
        Label lbl = createLabel(text, HELBHotelViewStyle.LABEL_WIDTH, Pos.CENTER, true);
        lbl.setMinHeight(HELBHotelViewStyle.LABEL_HEIGHT);
        Region colorBox = new Region();
        colorBox.setPrefSize(HELBHotelViewStyle.COLOR_BOX_WIDTH, HELBHotelViewStyle.COLOR_BOX_HEIGHT);
        colorBox.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4;",
                color));
        box.getChildren().addAll(lbl, colorBox);
        return box;
    }

    public static final Button createRoomButton(Room room) {
        Button btn = new Button(room.getName());
        btn.setPrefSize(70, 70);
        btn.setAlignment(Pos.CENTER);
        String bgColor = room.isAvailable() ? room.getColor() : "#FF0000";
        btn.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 14px;",
                bgColor, HELBHotelViewStyle.COLOR_BORDER));
        return btn;
    }

    public static final Button createButton(String text, String bgColor, boolean isBold) {
        String fontWeight = isBold ? "-fx-font-weight: bold;" : "";
        Button btn = new Button(text);
        btn.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: %s; -fx-font-size: %dpx; %s -fx-padding: %dpx; -fx-border-radius: 5; -fx-border-color: black; -fx-border-width: 1;",
                bgColor,
                HELBHotelViewStyle.BUTTON_TEXT_FILL,
                HELBHotelViewStyle.FONT_SIZE_BUTTON,
                fontWeight,
                HELBHotelViewStyle.BUTTON_PADDING));
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
        Button verifyButton = new Button("Verify Code");
        verifyButton.setPrefSize(180, HELBHotelViewStyle.BUTTON_PREF_HEIGHT);
        verifyButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        verifyButton.setOnAction(eventHandler);
        return verifyButton;
    }

    public static final ComboBox<String> createSortByParamComboBox(List<String> options,
                                                                   EventHandler<ActionEvent> eventHandler) {
        ComboBox<String> sortComboBox = createComboBox(options, eventHandler);
        sortComboBox.setPrefWidth(100);
        return sortComboBox;
    }

    public static final ComboBox<String> createReservationModeSelector(List<String> options,
                                                                       EventHandler<ActionEvent> eventHandler) {
        ComboBox<String> modeSelector = createComboBox(options, eventHandler);
        modeSelector.setStyle("-fx-font-size: 12px; -fx-background-radius: 5; -fx-padding: 4 8;");
        modeSelector.setPrefWidth(180);
        return modeSelector;
    }

}
