package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public enum AliveStatus {

    ALIVE("Alive"),
    DECEASED("Deceased"),
    UNKNOWN("Unknown");

    private final String displayName;

    private AliveStatus(String displayName) {
        this.displayName = displayName;
    }
}
