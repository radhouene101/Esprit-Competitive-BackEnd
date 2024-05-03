package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="comment")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long comment_id;

    @Column(nullable = false)
    String content ;

    @ManyToOne(optional = false)
    @JoinColumn(name = "createdBy" ,nullable = false)
    private User createdBy;

    @ManyToOne(optional = false)
    private Post post;

    private Integer likes_count ;
    private Integer dislikes_count ;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Comment parentComment ;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_comment_id")
    private List<Comment> nestedComments;

    public Comment() {

    }


    public void addNestedComment(Comment nestedComment) {
        if (nestedComments == null) {
            nestedComments = new ArrayList<>();
        }
        nestedComments.add(nestedComment);
    }

    public void updateNestedComment(Comment updatedNestedComment) {
        // Find the nested comment by its ID and update it
        for (int i = 0; i < nestedComments.size(); i++) {
            Comment nestedComment = nestedComments.get(i);
            if (nestedComment.getComment_id().equals(updatedNestedComment.getComment_id())) {
                nestedComments.set(i, updatedNestedComment);
                break;
            }
        }
    }

    public void deleteNestedComment(Long nestedId) {
        // Find the nested comment by its ID and remove it
        for (Iterator<Comment> iterator = nestedComments.iterator(); iterator.hasNext(); ) {
            Comment nestedComment = iterator.next();
            if (nestedComment.getComment_id().equals(nestedId)) {
                iterator.remove();
                break;
            }
        }
    }
}
