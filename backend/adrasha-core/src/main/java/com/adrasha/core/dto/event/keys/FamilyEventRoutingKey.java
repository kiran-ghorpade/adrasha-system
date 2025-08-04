package com.adrasha.core.dto.event.keys;

public enum FamilyEventRoutingKey {
    FAMILY_CREATED("adrasha.data.family.created"),
    FAMILY_UPDATED("adrasha.data.family.updated"),
    FAMILY_DELETED("adrasha.data.family.deleted");
	
    private final String key;
    
    private FamilyEventRoutingKey(String key) {
    	this.key = key;
    }
    
    public String key() {
    	return this.key;
    }
}
