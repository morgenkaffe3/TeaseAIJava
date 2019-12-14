package me.goddragon.teaseai.api.config;

import me.goddragon.teaseai.utils.TeaseLogger;

import java.util.logging.Level;

public abstract class VariableBasedComponent<T> extends GUISettingComponent {

    protected final PersonalityVariable<T> variable;

    public VariableBasedComponent(String settingString, int columnNumber, PersonalityVariable<T> variable) {
        super(settingString, columnNumber);
        this.variable = variable;
    }

    public VariableBasedComponent(String settingString, PersonalityVariable<T> variable) {
        super(settingString);
        this.variable = variable;
    }

    public VariableBasedComponent(String settingString, String description, int columnNumber, PersonalityVariable<T> variable) {
        super(settingString, description, columnNumber);
        this.variable = variable;
    }

    public VariableBasedComponent(String settingString, String description, PersonalityVariable<T> variable) {
        super(settingString, description);
        this.variable = variable;
    }

    protected void handleMissAssignedValue(String expectedType) {
        TeaseLogger.getLogger().log(Level.SEVERE, "Variable '" + variable.getConfigName() + "' was assigned to the custom setting '" +
                "" + settingLabel.getText() + "' but is malformed/has the wrong type. Expected Type: " + expectedType);
    }
}
