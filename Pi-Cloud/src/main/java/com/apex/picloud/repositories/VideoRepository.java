package com.apex.picloud.repositories;

import com.apex.picloud.entities.farouk.Video;
import com.apex.picloud.entities.farouk.enums.Niveau;
import com.apex.picloud.entities.farouk.enums.OptionEnum;
import com.apex.picloud.entities.farouk.enums.Visible;
import com.apex.picloud.entities.farouk.enums.videoTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findVideoByVideoId(Long videoId);
    List<Video> findVideoByNiveau(Niveau niveau);
    List<Video> findVideoByVisibility(Visible visibility);
    List<Video> findVideoByOption(OptionEnum option);
    List<Video> findVideoByTags(videoTags tags);
}
