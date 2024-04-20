package com.apex.picloud.services.forum;

import com.apex.picloud.dtos.ForumDTO;
import com.apex.picloud.models.Forum;
import com.apex.picloud.repositories.ForumRepository;
import com.apex.picloud.services.forum.ForumService;
import com.apex.picloud.validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private  final ObjectsValidator validator;


    @Override
    public ForumDTO createForum(ForumDTO dto){

        validator.validate(dto);
        Forum forum1 = ForumDTO.toEntity(dto);
        forumRepository.save(forum1);
        return ForumDTO.fromEntity(forum1);    }

    @Override
    public List<Forum> getAllForums() {
        return forumRepository.findAll();    }

    @Override
    public Forum getForumById(Long forum_id) {
        Optional<Forum> forumOptional = forumRepository.findById(forum_id);
        return forumOptional.orElse(null);    }

    @Override
    public ForumDTO updateForum(ForumDTO dto) {
        Optional<Forum> existingForumOptional = forumRepository.findById(dto.getForum_id());
        if (existingForumOptional.isPresent()) {
            Forum existingForum = existingForumOptional.get();
            existingForum.setForum_name(dto.getForum_name());
            existingForum.setDescription(dto.getDescription());
            existingForum = forumRepository.save(existingForum);
            return ForumDTO.fromEntity(existingForum);
        } else {
            return null;
        }
    }

    @Override
    public void deleteForum(Long forum_id) {
        Optional<Forum> existingForumOptional = forumRepository.findById(forum_id);
        if (!existingForumOptional.isPresent()) {
        }
        forumRepository.deleteById(forum_id);
    }
}
