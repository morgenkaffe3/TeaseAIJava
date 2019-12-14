package me.goddragon.teaseai.api.config;

import javafx.scene.control.TextFormatter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

public class SpinnerComponent<T> extends VariableBasedComponent<T> {

    public static final int INTEGER_TYPE = 0;
    public static final int DOUBLE_TYPE = 1;


    public SpinnerComponent(String settingString, int columnNumber, PersonalityVariable<T> variable) {
        super(settingString, columnNumber, variable);
    }

    public SpinnerComponent(String settingString, PersonalityVariable<T> variable) {
        super(settingString, variable);
    }

    public SpinnerComponent(String settingString, String description, int columnNumber, PersonalityVariable<T> variable) {
        super(settingString, description, columnNumber, variable);
    }

    public SpinnerComponent(String settingString, String description, PersonalityVariable<T> variable) {
        super(settingString, description, variable);
    }

    protected UnaryOperator<TextFormatter.Change> getFilter(NumberFormat format) {
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);

                //NumberFormat evaluates the beginning of the text
                format.parse(c.getControlNewText(), parsePosition);

                if (parsePosition.getIndex() == 0 || parsePosition.getIndex() < c.getControlNewText().length()) {
                    //Reject parsing the complete text failed
                    return null;
                }
            }

            return c;
        };

        return filter;
    }
}
