package me.goddragon.teaseai.api.config;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;

public class IntegerSpinnerComponent extends SpinnerComponent<Integer> {
    private Spinner<Integer> spinner;
    private final int min;
    private final int max;

    public IntegerSpinnerComponent(PersonalityVariable<Integer> variable, String settingString, int min, int max) {
        super(settingString, variable.getDescription(), variable);
        this.min = min;
        this.max = max;

        setUp();
    }

    public IntegerSpinnerComponent(PersonalityVariable<Integer> variable, String settingString, int min, int max, String description) {
        super(settingString, description, variable);
        this.min = min;
        this.max = max;

        setUp();
    }

    private void setUp() {
        int startingValue = variable.getValue();

        spinner = new Spinner<>(min, max, startingValue);

        NumberFormat format = NumberFormat.getIntegerInstance();

        TextFormatter<Integer> valueFormatter = new TextFormatter<>(new IntegerStringConverter(), startingValue, getFilter(format));

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, startingValue));
        spinner.setEditable(true);
        spinner.getEditor().setTextFormatter(valueFormatter);
        spinner.getValueFactory().setValue(startingValue);

        spinner.valueProperty().addListener((observable, oldValue, newValue) -> variable.setValue(newValue));

        this.setting = spinner;
    }

}
