package com.example.course_work.data;

import com.example.course_work.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByRole(String role);
    User findUserByRole(String role);
    User deleteUserById(Long id);
    User deleteUserByUsername(String username);
}