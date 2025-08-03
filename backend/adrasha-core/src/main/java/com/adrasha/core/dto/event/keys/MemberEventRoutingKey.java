package com.adrasha.core.dto.event.keys;

public enum MemberEventRoutingKey {
    MEMBER_CREATED("adrasha.data.member.created"),
    MEMBER_UPDATED("adrasha.data.member.updated"),
    MEMBER_DELETED("adrasha.data.member.deleted");
	
    private final String key;
    
    private MemberEventRoutingKey(String key) {
    	this.key = key;
    }
    
    public String key() {
    	return this.key;
    }
}
