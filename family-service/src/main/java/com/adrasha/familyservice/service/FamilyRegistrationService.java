package com.adrasha.familyservice.service;

import com.adrasha.familyservice.model.Family;
import com.adrasha.familyservice.model.Member;

public interface FamilyRegistrationService {

    public Family registerFamilyWithHead(Member member, Family family);
}
