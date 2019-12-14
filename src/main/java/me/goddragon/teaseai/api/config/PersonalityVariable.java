package me.goddragon.teaseai.api.config;

/**
 * Created by GodDragon on 06.04.2018.
 */
public class PersonalityVariable<T> {

    private final String configName;
    private T value;

    //The personality this variable belongs to
    private String personalityName;
    private String customName;
    private String description;
    private boolean supportedByPersonality = false;
    private boolean temporary = false;

    public PersonalityVariable(String configName, T value, String personalityName) {
        this(configName, value, null, null, personalityName);
    }

    public PersonalityVariable(String configName, T value, String customName, String description, String personalityName) {
        this.configName = configName.toLowerCase();
        this.value = value;
        this.customName = customName;
        this.description = description;
        this.supportedByPersonality = true;
        this.personalityName = personalityName;
    }

    public String getConfigName() {
        return configName;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSupportedByPersonality() {
        return supportedByPersonality;
    }

    public void setSupportedByPersonality(boolean supportedByPersonality) {
        this.supportedByPersonality = supportedByPersonality;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public boolean isTemporary() {
        return this.temporary;
    }

    @Override
    public String toString() {
        return customName != null ? customName : configName;
    }

    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof PersonalityVariable) {
    		return this.equals((PersonalityVariable<?>)obj);
    	}
    	return false;
    }
    
    public boolean equals(PersonalityVariable<?> variable) {
        return configName.equals(variable.getConfigName()) && customName.equals(variable.getCustomName()) && personalityName.equals(variable.getPersonalityString());
    }

    public String getPersonalityString() {
        return personalityName;
    }
}
