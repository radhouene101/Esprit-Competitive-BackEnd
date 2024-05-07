package com.apex.picloud.controllers;

import com.apex.picloud.entities.farouk.Video;
import com.apex.picloud.entities.farouk.enums.Niveau;
import com.apex.picloud.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addVideo(@RequestBody Video video) {
        String message = videoService.addVideo(video);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateVideo(@RequestBody Video video) {
        String message = videoService.updateVideo(video);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Video>> findAllVideos() {
        List<Video> videos = videoService.findAllVideos();
        return ResponseEntity.ok(videos);
    }

    @PostMapping("/addAll")
    public ResponseEntity<List<Video>> saveAllVideos(@RequestBody List<Video> videos) {
        List<Video> savedVideos = videoService.saveAllVideos(videos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideos);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> findVideoByVideoId(@PathVariable Long videoId) {
        Video video = videoService.findVideoByVideoId(videoId);
        if (video != null) {
            return ResponseEntity.ok(video);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byNiveau/{niveau}")
    public ResponseEntity<List<Video>> findVideosByNiveau(@PathVariable String niveau) {
        Niveau niveauEnum;
        try {
            niveauEnum = Niveau.valueOf(niveau.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid niveau value
            return ResponseEntity.badRequest().build();
        }

        List<Video> videos = videoService.findVideosByNiveau(niveauEnum);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/byVisibility/{visibility}")
    public ResponseEntity<List<Video>> findVideosByVisibility(@PathVariable String visibility) {
        List<Video> videos = videoService.findVideosByVisibility(visibility);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/byOption/{option}")
    public ResponseEntity<List<Video>> findVideosByOption(@PathVariable String option) {
        List<Video> videos = videoService.findVideosByOption(option);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/byTags")
    public ResponseEntity<List<Video>> findVideosByTags(@RequestBody List<String> tags) {
        List<Video> videos = videoService.findVideosByTags(tags);
        return ResponseEntity.ok(videos);
    }


    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> deleteVideoByVideoId(@PathVariable Long videoId) {
        String message = videoService.deleteVideoByVideoId(videoId);
        return ResponseEntity.ok(message);
    }
}
