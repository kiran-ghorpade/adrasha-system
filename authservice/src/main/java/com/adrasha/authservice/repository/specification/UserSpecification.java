package com.adrasha.authservice.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.adrasha.authservice.model.User;

public class UserSpecification{

    public static Specification<User> hasRole(String department) {
        return (root, query, cb) -> cb.equal(root.get("department"), department);
    }
}
