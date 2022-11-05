package com.jsolutions.springcloud.msvc.users.services.impl;

import java.util.List;
import java.util.Optional;

import com.jsolutions.springcloud.msvc.users.clients.CourseClientRest;
import com.jsolutions.springcloud.msvc.users.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsolutions.springcloud.msvc.users.repositories.UserRepository;
import com.jsolutions.springcloud.msvc.users.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private CourseClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<User> lisAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        client.deleteUserInCourse(id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByLastname(String lastname) {
        return repository.findUserByLastname(lastname);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByIds(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

}