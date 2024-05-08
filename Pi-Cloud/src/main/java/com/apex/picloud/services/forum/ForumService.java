package com.apex.picloud.services.forum;

import com.apex.picloud.dtos.ForumDTO;
import com.apex.picloud.models.Forum;

import java.util.List;

public interface ForumService {
    ForumDTO createForum(ForumDTO dto);

    List<Forum> getAllForums();
    Forum getForumById(Long forum_id);
    ForumDTO updateForum(ForumDTO forum);
    void deleteForum(Long forum_id);
}
