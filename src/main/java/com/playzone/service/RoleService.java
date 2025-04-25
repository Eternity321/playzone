package com.playzone.service;


import com.playzone.model.Role;
import com.playzone.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").get();
    }
}
