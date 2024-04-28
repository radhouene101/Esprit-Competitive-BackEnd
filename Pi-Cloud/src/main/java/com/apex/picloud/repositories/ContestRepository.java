package com.apex.picloud.repositories;

import com.apex.picloud.entities.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Long> {
}
