package com.adrasha.data.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.adrasha.core.dto.filter.MemberFilterDTO;
import com.adrasha.data.model.Member;

import jakarta.persistence.criteria.Predicate;

public class MemberSpecification {
    public static Specification<Member> withSearch(MemberFilterDTO filter, String searchTerm) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchTerm != null && !searchTerm.isBlank()) {
                String like = "%" + searchTerm.toLowerCase() + "%";

                Predicate nameFirst = cb.like(cb.lower(root.get("name").get("firstName")), like);
                Predicate nameLast = cb.like(cb.lower(root.get("name").get("lastName")), like);
                Predicate mobile = cb.like(cb.lower(root.get("mobileNumber")), like);
                Predicate adhar = cb.like(cb.lower(root.get("adharNumber")), like);
                Predicate abha = cb.like(cb.lower(root.get("abhaNumber")), like);

                predicates.add(cb.or(nameFirst, nameLast, mobile, adhar, abha));
            }

            if (filter.getFamilyId() != null) {
                predicates.add(cb.equal(root.get("familyId"), filter.getFamilyId()));
            }

            if (filter.getAshaId() != null) {
                predicates.add(cb.equal(root.get("ashaId"), filter.getAshaId()));
            }

            if (filter.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filter.getGender()));
            }

            if (filter.getDateOfBirth() != null) {
                predicates.add(cb.equal(root.get("dateOfBirth"), filter.getDateOfBirth()));
            }

            if (filter.getAliveStatus() != null) {
                predicates.add(cb.equal(root.get("aliveStatus"), filter.getAliveStatus()));
            }

            if (filter.getMinAge() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("age"), filter.getMinAge()));
            }

            if (filter.getMaxAge() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("age"), filter.getMaxAge()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
