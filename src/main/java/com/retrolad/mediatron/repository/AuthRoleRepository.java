package com.retrolad.mediatron.repository;

import com.retrolad.mediatron.security.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRoleRepository extends JpaRepository<AuthRole, Integer> {

    Optional<AuthRole> findByName(String name);
}
