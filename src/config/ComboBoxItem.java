package config;

public class ComboBoxItem {
    private String displayText;
    private String value;

    public ComboBoxItem(String displayText, String value) {
        this.displayText = displayText;
        this.value = value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
