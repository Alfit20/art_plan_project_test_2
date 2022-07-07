package com.example.art_plan_project_2.repository;

import com.example.art_plan_project_2.entity.Animal;
import com.example.art_plan_project_2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    boolean existsByName(String name);

    List<Animal> findByUser(User user);

}
