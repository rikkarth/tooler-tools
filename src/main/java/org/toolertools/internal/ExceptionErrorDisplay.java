package org.toolertools.internal;

public enum ExceptionErrorDisplay {
    PROPERTIES_LOADING_ERROR("Error loading %s.properties."),
    INVALID_PATH_TO_PROP("Invalid path to %s.properties. Path provided -> %s");

    private final String value;

    private ExceptionErrorDisplay(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }

    public String get(String propName) {
        return String.format(this.value, propName);
    }

    public String get(String propName, String path) {
        return String.format(this.value, propName, path);
    }
}
