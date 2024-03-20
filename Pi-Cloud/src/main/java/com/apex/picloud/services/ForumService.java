package com.apex.picloud.services;

import com.apex.picloud.models.Forum;

import java.util.List;

public interface ForumService {
    Forum createForum(Forum forum);
    List<Forum> getAllForums();
    Forum getForumById(Long forum_id);
    Forum updateForum(Forum forum);
    void deleteForum(Long forum_id);
}
