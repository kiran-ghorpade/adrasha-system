package com.adrasha.auth.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.adrasha.auth.model.User;

public class UserSpecification{

    public static Specification<User> hasRole(String department) {
        return (root, query, cb) -> cb.equal(root.get("department"), department);
    }
}
