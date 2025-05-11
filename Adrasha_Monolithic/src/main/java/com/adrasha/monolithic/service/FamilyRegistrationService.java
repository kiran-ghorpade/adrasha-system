package com.adrasha.monolithic.service;

import com.adrasha.monolithic.model.Family;
import com.adrasha.monolithic.model.Member;

public interface FamilyRegistrationService {

    public Family registerFamilyWithHead(Member member, Family family);
}
