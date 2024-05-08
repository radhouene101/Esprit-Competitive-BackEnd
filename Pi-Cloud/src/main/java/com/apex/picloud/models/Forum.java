package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="forums")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="forum_id")
    private Long forum_id;

    @Column(nullable = false)
    String forum_name ;

    String description ;

    @ManyToOne
    private User createdBy ;
    LocalDateTime created_at ;

    @OneToMany
    private List<Topic> topics ;

}
