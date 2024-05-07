package com.apex.picloud.repositories;

import com.apex.picloud.dtos.ForumDTO;
import com.apex.picloud.models.Forum;
import com.apex.picloud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum,Long> {

    Forum save(ForumDTO forum);
}
