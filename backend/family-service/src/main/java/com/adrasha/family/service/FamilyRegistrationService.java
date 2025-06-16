package com.adrasha.family.service;

import com.adrasha.family.model.Family;
import com.adrasha.family.model.Member;

public interface FamilyRegistrationService {

	Family registerFamilyWithHead(Family family, Member headMember);
}
