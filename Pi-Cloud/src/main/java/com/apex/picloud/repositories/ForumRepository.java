package com.apex.picloud.repositories;

import com.apex.picloud.models.Forum;
import com.apex.picloud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum,Long> {
    @Override
    Forum save(Forum forum);
}
