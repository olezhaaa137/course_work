package com.example.course_work.data;

import com.example.course_work.entities.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Long> {
}
