package com.apex.picloud.repositories;

import com.apex.picloud.models.Comment;
import com.apex.picloud.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.post_id = :post_id")
    List<Comment> findAllByPostId(Long post_id);


}