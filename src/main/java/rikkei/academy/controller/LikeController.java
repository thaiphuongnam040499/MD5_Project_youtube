package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.LikeDTO;
import rikkei.academy.model.Like;
import rikkei.academy.service.likeService.ILikeService;
import rikkei.academy.service.userService.IUserService;
import rikkei.academy.service.videoService.IVideoService;

import java.util.Optional;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    ILikeService likeService;
    @Autowired
    IUserService userService;
    @Autowired
    IVideoService videoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findLikeByVideoId(@PathVariable Long id) {
        Long likeCount = likeService.countLikesByVideoId(id);
        return ResponseEntity.ok(likeCount);
    }

    @PostMapping("/createLike")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createLike(@RequestBody LikeDTO like) {
        Optional<Like> check = likeService.findLikeByUserIdAndVideoId(like.getUserId(), like.getVideoId());
        if (check.isPresent()) {
            return ResponseEntity.badRequest().body("Đã like video trước đó");
        }
        Like newLike = new Like();
        newLike.setUser(userService.findById(like.getUserId()));
        newLike.setVideo(videoService.findById(like.getVideoId()));
        return ResponseEntity.ok(likeService.save(newLike));
    }

    @DeleteMapping("/deleteLike/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteLike(@PathVariable Long id) {
        likeService.delete(id);
    }
}
