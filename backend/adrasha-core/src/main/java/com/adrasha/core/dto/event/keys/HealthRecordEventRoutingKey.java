package com.adrasha.core.dto.event.keys;

public enum HealthRecordEventRoutingKey {
	HEALTH_RECORD_CREATED("adrasha.data.healthRecord.created"),
    HEALTH_RECORD_UPDATED("adrasha.data.healthRecord.updated"),
    HEALTH_RECORD_DELETED("adrasha.data.healthRecord.deleted");
	
    private final String key;
    
    private HealthRecordEventRoutingKey(String key) {
    	this.key = key;
    }
    
    public String key() {
    	return this.key;
    }
}
