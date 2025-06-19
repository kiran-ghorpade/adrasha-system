package com.adrasha.data.model;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.Auditable;
import com.adrasha.core.model.Gender;
import com.adrasha.core.model.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private UUID familyId;

	@Embedded
	private Name name;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private Instant dateOfBirth;

	private String birthPlace;

	@Column(unique = true)
	private String adharNumber;

	private String abhaNumber;

	private String mobileNumber;

	private boolean maritalStatus;

	@Default
	private boolean alive = true;

}
