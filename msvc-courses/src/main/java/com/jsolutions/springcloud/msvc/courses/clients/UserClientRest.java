package com.jsolutions.springcloud.msvc.courses.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsolutions.springcloud.msvc.courses.models.User;

import java.util.List;

@FeignClient(name="msvc-users", url="localhost:8001/users")
public interface UserClientRest{

    @GetMapping("/id/{id}")
    User getUserById(@PathVariable(name = "id") Long id);

    @PostMapping
    User create(@RequestBody User user);

    @GetMapping("/users-details")
    List<User> getUsersDetailsByCourse(@RequestParam List<Long> ids);
}