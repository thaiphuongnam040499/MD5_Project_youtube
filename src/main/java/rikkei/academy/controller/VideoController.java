package rikkei.academy.controller;

import javafx.scene.shape.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rikkei.academy.dto.request.VideoDTO;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Video;
import rikkei.academy.service.uploadFile.FileStorageService;
import rikkei.academy.service.videoService.IVideoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/video")
@CrossOrigin(origins = "*")
public class VideoController {
    @Autowired
    private IVideoService videoService;
    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/index")
    public ResponseEntity<List<Video>> getAllVideo() {
        return ResponseEntity.ok(videoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Video video = videoService.findById(id);
        video.setViews(video.getViews() + 1);
        return ResponseEntity.ok(videoService.save(video));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createVideo(@RequestBody Video video) {
        if (video.getChannel().getStatus() == 1 || video.getChannel().getStatus() == 2 || video.getChannel().getStatus() == 3) {
            video.setUploadDate(LocalDateTime.now());
            return ResponseEntity.ok(videoService.save(video));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                ResponseMessage.builder()
                        .status("")
                        .message("Thêm mới thất bại! Channel của bạn đã bị khóa")
                        .data("")
                        .build()
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Video> updateVideo(@RequestBody VideoDTO video) {
        Video videoUp = videoService.findById(video.getId());
        if (video.getTitle()!=null){
        videoUp.setTitle(video.getTitle());
        }
        if (video.getDescription()!=null){
        videoUp.setDescription(video.getDescription());
        }
        if(video.getUrl()!= null){
            videoUp.setVideoUrl(video.getUrl());
        }
        return ResponseEntity.ok(videoService.save(videoUp));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void delete(@PathVariable Long id) {
        videoService.delete(id);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<Video>> search(@PathVariable String title) {
        List<Video> videos = videoService.searchVideoByTitle(title);
        if (!videos.isEmpty()) {
            return ResponseEntity.ok(videos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Video>> getVideos(@RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<Video> videoPage = videoService.findAll(page, size);
        if (!videoPage.isEmpty()) {
            return ResponseEntity.ok(videoPage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
