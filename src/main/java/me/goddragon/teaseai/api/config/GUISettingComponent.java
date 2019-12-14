package me.goddragon.teaseai.api.config;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.PopupWindow;
import javafx.util.Duration;

public abstract class GUISettingComponent {

    protected Label settingLabel;
    protected String description;
    protected Node setting;
    protected final int columnNumber;

    private static final int DEFAULT_COLUMN_NUMBER = -1;

    public GUISettingComponent(String settingString, int columnNumber) {
        this(settingString, null, columnNumber);
    }

    public GUISettingComponent(String settingString) {
        this(settingString, null);
    }

    public GUISettingComponent(String settingString, String description, int columnNumber) {
     
        settingString += ":";
        settingLabel = new Label(settingString);
        
        this.description = description;
        
        if(description != null) {
        	settingLabel.setTooltip(makeBubble(new Tooltip(this.description)));
        }
        
        this.columnNumber = columnNumber;
    }

    public GUISettingComponent(String settingString, String description) {
        this(settingString, description, DEFAULT_COLUMN_NUMBER);
    }

    private Tooltip makeBubble(Tooltip tooltip) {
        tooltip.setStyle("-fx-font-size: 14px;");
        tooltip.setMaxWidth(300);
        tooltip.setWrapText(true);
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
        tooltip.setShowDelay(new Duration(100));

        return tooltip;
    }

    public Label getLabel() {
        return settingLabel;
    }

    public Node getSetting() {
        return setting;
    }

    public int getColumnID() {
        return columnNumber;
    }
}
