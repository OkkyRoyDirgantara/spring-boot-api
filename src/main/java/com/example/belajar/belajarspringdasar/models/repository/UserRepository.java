package com.example.belajar.belajarspringdasar.models.repository;

import com.example.belajar.belajarspringdasar.models.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public Boolean existsByEmail(String email);
}
