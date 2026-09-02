package com.loanflow.identity.repository;

import com.loanflow.identity.entity.Role;
import com.loanflow.identity.entity.enums.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleCode(RoleCode roleCode);
}