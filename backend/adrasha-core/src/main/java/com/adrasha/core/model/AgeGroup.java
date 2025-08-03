package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.stream.Stream;

@Schema
@Getter
public enum AgeGroup {
	
	INFANT("Infant", 0, 1),
	CHILD("Child", 1, 12),
	TEEN("Teen", 12, 18),
	ADULT("Adult", 18, 35),
	MID_AGED_ADULT("Mid-age Adult", 35, 60),
	SENIOR("Senior", 60, 200),
	UNKNOWN("Unknown", -1, -1);
	
	private final String displayName;
	private final int minAge;
	private final int maxAge;
	
	private AgeGroup(String displayName, int minAge, int maxAge) {
		this.displayName = displayName;
		this.minAge = minAge;
		this.maxAge = maxAge;
	}
	
    public static AgeGroup fromAge(Integer age) {
        if (age == null) return AgeGroup.UNKNOWN;
        return Stream.of(AgeGroup.values())
                     .filter(g -> g.check(age))
                     .findFirst()
                     .orElse(AgeGroup.UNKNOWN);
    }
    
	private boolean check(int age) {
		
		if (age >= this.minAge && age < this.maxAge) {
			return true;
		}
		return false;
	}
	
}
