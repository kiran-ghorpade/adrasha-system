package com.adrasha.family.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Embedded
	private Name name;

	@Enumerated
	private Gender gender;

	private Instant dateOfBirth;

	private String birthPlace;


	@Column(unique = true)
	private String adharNumber;

	@Column(unique = true)
	private String abhaNumber;

	private String mobileNumber;

	private boolean maritalStatus;

//	private Instant marriageDate;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "spouse_id")
//	private Member spouse;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "father_id")
//	private Member father;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "mother_id")
//	private Member mother;

	@Default
	private boolean alive = true;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(nullable = true)
	private Family family;
}
