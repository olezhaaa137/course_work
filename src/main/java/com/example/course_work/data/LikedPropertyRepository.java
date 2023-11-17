package com.example.course_work.data;


import com.example.course_work.entities.LikedProperty;
import com.example.course_work.entities.Property;
import com.example.course_work.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface LikedPropertyRepository extends CrudRepository<LikedProperty, Long> {
    LikedProperty findByPropertyAndUser(Property property, User user);
}
