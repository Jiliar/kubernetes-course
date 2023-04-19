package com.jsolutions.springcloud.msvc.users.services;

import java.util.List;
import java.util.Optional;

import com.jsolutions.springcloud.msvc.users.models.entities.User;

public interface UserService {
    
    List<User> listAll();
    Optional<User> findById(Long id);
    User save(User user);
    void delete(Long id);

    Optional<User> findByEmail(String email);
    List<User> findByLastname(String lastname);
    List<User> findAllByIds(Iterable<Long> ids);
    
}
