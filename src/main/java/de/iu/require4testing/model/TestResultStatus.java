package de.iu.require4testing.model;

public enum TestResultStatus {

    NOT_EXECUTED("Nicht ausgeführt"),
    PASSED("Bestanden"),
    FAILED("Fehlgeschlagen");

    private final String displayName;

    TestResultStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}