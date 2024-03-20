package com.apex.picloud.services;

import com.apex.picloud.models.Forum;
import com.apex.picloud.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumServiceImpl implements ForumService{
    @Autowired
    private ForumRepository forumRepository;

    @Override
    public Forum createForum(Forum forum){
        return forumRepository.save(forum);
    }

    @Override
    public List<Forum> getAllForums() {
        return forumRepository.findAll();    }

    @Override
    public Forum getForumById(Long forum_id) {
        Optional<Forum> forumOptional = forumRepository.findById(forum_id);
        return forumOptional.orElse(null);    }

    @Override
    public Forum updateForum(Forum forum) {
        Optional<Forum> existingForumOptional = forumRepository.findById(forum.getForum_id());
        if (!existingForumOptional.isPresent()) {
                   }

        Forum existingForum = existingForumOptional.get();
        // Update specific fields if needed (e.g., name, description)
        existingForum.setForum_name(forum.getForum_name());
        existingForum.setDescription(forum.getDescription());

        // Save the updated forum
        forumRepository.save(existingForum);
        return existingForum;
    }

    @Override
    public void deleteForum(Long forum_id) {
        // Check if forum exists before deleting
        Optional<Forum> existingForumOptional = forumRepository.findById(forum_id);
        if (!existingForumOptional.isPresent()) {
        }

        // Consider cascading deletion of associated topics and posts (optional)
        // Or handle orphaned topics/posts if deletion is not cascaded

        forumRepository.deleteById(forum_id);
    }
}
