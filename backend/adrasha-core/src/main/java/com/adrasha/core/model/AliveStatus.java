package com.adrasha.core.model;

public enum AliveStatus {

    ALIVE("Alive"),
    DECEASED("Deceased"),
    UNKNOWN("Unknown");

    private final String displayName;

    AliveStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
