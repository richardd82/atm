package com.atm.atm.Controllers;

import com.atm.atm.Entities.User;
import com.atm.atm.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUsersById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setId(id);
        return userRepository.save(user);
    }
}
