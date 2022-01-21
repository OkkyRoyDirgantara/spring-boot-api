package com.example.belajar.belajarspringdasar.services;

import com.example.belajar.belajarspringdasar.models.entity.User;
import com.example.belajar.belajarspringdasar.models.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
