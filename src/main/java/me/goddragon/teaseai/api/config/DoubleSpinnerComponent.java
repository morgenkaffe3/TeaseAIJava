package me.goddragon.teaseai.api.config;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

import java.text.NumberFormat;

public class DoubleSpinnerComponent extends SpinnerComponent<Double> {
    private Spinner<Double> spinner;
    private final double min;
    private final double max;

    public DoubleSpinnerComponent(PersonalityVariable<Double> variable, String settingString, double min, double max) {
        super(settingString, variable.getDescription(), variable);
        this.min = min;
        this.max = max;

        setUp();
    }

    public DoubleSpinnerComponent(PersonalityVariable<Double> variable, String settingString, double min, double max, String description) {
        super(settingString, description, variable);
        this.min = min;
        this.max = max;

        setUp();
    }

    private void setUp() {
        double startingValue = variable.getValue();

        spinner = new Spinner<>(min, max, startingValue);

        NumberFormat format = NumberFormat.getNumberInstance();

        TextFormatter<Double> priceFormatter = new TextFormatter<>(new DoubleStringConverter(), startingValue, getFilter(format));

        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, startingValue));
        spinner.setEditable(true);
        spinner.getEditor().setTextFormatter(priceFormatter);
        spinner.getValueFactory().setValue(startingValue);

        spinner.valueProperty().addListener((observable, oldValue, newValue) -> variable.setValue(newValue));

        this.setting = spinner;
    }
}
