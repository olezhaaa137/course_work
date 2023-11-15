package com.example.course_work;

import com.example.course_work.data.ImageRepository;
import com.example.course_work.data.PropertyRepository;
import com.example.course_work.entities.Property;
import com.example.course_work.parser.PropertyParser;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class CourseWorkApplication {


	public static void main(String[] args) {
		SpringApplication.run(CourseWorkApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(PropertyRepository propertyRepo,
										ImageRepository imageRepo) {
		return args -> {
			Runnable runnable = () -> {
				try {
					List<Property> properties = PropertyParser.getAllProperties();
					for (int i = 0; i < properties.size(); i++) {
						imageRepo.saveAll(properties.get(i).getImages());
					}

					propertyRepo.saveAll(properties);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
			thread.join();
		};
	}


}
