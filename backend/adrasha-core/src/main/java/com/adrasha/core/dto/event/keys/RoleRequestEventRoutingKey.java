package com.adrasha.core.dto.event.keys;

public enum RoleRequestEventRoutingKey {
    ROLE_REQUEST_CREATED("adrasha.user.roleRequest.created"),
    ROLE_REQUEST_UPDATED("adrasha.user.roleRequest.updated"),
    ROLE_REQUEST_DELETED("adrasha.user.roleRequest.deleted");
	
    private final String key;
    
    private RoleRequestEventRoutingKey(String key) {
    	this.key = key;
    }
    
    public String key() {
    	return this.key;
    }
}
