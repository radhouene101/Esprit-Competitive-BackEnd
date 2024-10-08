package com.apex.picloud.repositories;

import com.apex.picloud.models.Post;
import com.apex.picloud.models.Topic;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Post save(Post post);
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.pinned = true WHERE p.post_id = (SELECT p2.post_id FROM Post p2 ORDER BY p2.LikesCount DESC LIMIT 1)")
    void pinMostLikedPost();
    @Query("SELECT p FROM Post p WHERE p.pinned = true")
    Post findPinnedPost();

    @Query("SELECT p FROM Post p WHERE p.topic.topic_id = :topic_id")
    List<Post> findAllByTopicId(Long topic_id);
}
