package com.apex.picloud.repositories;

import com.apex.picloud.entities.CategoryProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProjectsRepository extends JpaRepository<CategoryProjects,Long> {
}
