package com.apex.picloud.services;

import com.apex.picloud.entities.farouk.Video;
import com.apex.picloud.entities.farouk.enums.Niveau;
import com.apex.picloud.entities.farouk.enums.OptionEnum;
import com.apex.picloud.entities.farouk.enums.Visible;
import com.apex.picloud.entities.farouk.enums.videoTags;
import com.apex.picloud.repositories.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final S3AWSService s3AWSService;
    public VideoService(VideoRepository videoRepository , S3AWSService s3AWSService) {
        this.videoRepository = videoRepository;
        this.s3AWSService = s3AWSService;
    }
    public String uploadVideoToS3(MultipartFile video) {
        String videoUrl = s3AWSService.uploadFile(video);
        return videoUrl;
    }
    public String addVideo(Video video) {
        videoRepository.save(video);
        return "Video added successfully";
    }

    public String updateVideo(Video video) {
        Video existingVideo = videoRepository.findById(video.getVideoId()).orElse(null);
        if (existingVideo == null) {
            return "Video not found";
        }
        videoRepository.save(video);
        return "Video updated successfully";
    }

    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    public List<Video> saveAllVideos(List<Video> videos) {
        return videoRepository.saveAll(videos);
    }

    public Video findVideoByVideoId(Long videoId) {
        return videoRepository.findVideoByVideoId(videoId);
    }

    public List<Video> findVideosByNiveau(Niveau niveau) {
        return videoRepository.findVideoByNiveau(niveau);
    }

    public List<Video> findVideosByVisibility(String visibility) {
        Visible visibleEnum;
        try {
            visibleEnum = Visible.valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid visibility value
            return null;
        }
        return videoRepository.findVideoByVisibility(visibleEnum);
    }

    public List<Video> findVideosByOption(String option) {
        OptionEnum optionEnum;
        try {
            optionEnum = OptionEnum.valueOf(option.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid option value
            return null;
        }
        return videoRepository.findVideoByOption(optionEnum);
    }

    public List<Video> findVideosByTags(List<String> tags) {
        List<Video> videos = new ArrayList<>();
        for (String tag : tags) {
            videoTags enumTag;
            try {
                enumTag = videoTags.valueOf(tag.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Handle invalid tag value
                continue; // Skip this tag and proceed with the next one
            }
            videos.addAll(videoRepository.findVideoByTags(enumTag));
        }
        return videos;
    }


    public String deleteVideoByVideoId(Long videoId) {
        Video existingVideo = videoRepository.findById(videoId).orElse(null);
        if (existingVideo == null) {
            return "Video not found";
        }
        videoRepository.deleteById(videoId);
        return "Video deleted successfully";
    }
}
