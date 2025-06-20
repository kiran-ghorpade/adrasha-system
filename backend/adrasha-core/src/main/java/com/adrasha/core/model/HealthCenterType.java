package com.adrasha.core.model;

public enum HealthCenterType {

    SUB_CENTER("Subcenter"),
    PRIMARY_HEALTH_CENTER("Primary Health Center"),
    COMMUNITY_HEALTH_CENTER("Community Health Center"),
    SUB_DISTRICT_HOSPITAL("Sub-district Hospital"),
    DISTRICT_HOSPITAL("District Hospital"),
    MEDICAL_COLLEGE("Medical College");

    private final String displayName;
    

    HealthCenterType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
