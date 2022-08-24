package com.example.demoSecurity.repo;

import com.example.demoSecurity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Role, Long> {

    Role findByname(String name);

}
