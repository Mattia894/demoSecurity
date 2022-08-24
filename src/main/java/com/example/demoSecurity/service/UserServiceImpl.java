package com.example.demoSecurity.service;

import com.example.demoSecurity.entity.Role;
import com.example.demoSecurity.entity.User;
import com.example.demoSecurity.repo.RolesRepo;
import com.example.demoSecurity.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional //if u use transactional on the service class you don't have to call the save method while calling a post Request
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RolesRepo rolesRepo;

    @Override
    public User saveUser(User user) {
        log.info("saving new user {}", user.getUsername());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {}", role.getName());
        return rolesRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("add role: {}, to user: {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = rolesRepo.findByname(roleName);
        user.getRoles().add(role);
        //we don't save because of the transactional annotation on the service class

    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
