package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.CommentDTO;
import rikkei.academy.model.Comment;
import rikkei.academy.service.commentService.ICommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    ICommentService commentService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<Comment> getCommentByVideoId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findCommentByVideoId(id));
    }
    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<Comment>> getReplies(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment != null) {
            List<Comment> replies = commentService.getRepliesByComment(comment);
            return ResponseEntity.ok(replies);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createComment")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        comment.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(commentService.save(comment));
    }
    @PostMapping("/{commentId}/reply")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<Comment> replyComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO replyDTO) {
        Comment parentComment = commentService.findById(commentId);
        if (parentComment == null) {
            return ResponseEntity.notFound().build();
        }
        // Tạo một comment mới dựa trên replyDTO
        Comment replyComment = new Comment();
        replyComment.setText(replyDTO.getText());
        replyComment.setUser(replyDTO.getUser());
        replyComment.setTimestamp(LocalDateTime.now());
        // Thiết lập mối quan hệ cha-con với comment gốc
        replyComment.setParentComment(parentComment);
        // Lưu comment trả lời vào cơ sở dữ liệu
        commentService.save(replyComment);
        // Trả về comment trả lời đã lưu thành công
        return ResponseEntity.ok(replyComment);
    }

    @PutMapping("/updateComment")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Comment> updateComment(@RequestBody CommentDTO comment) {
        Comment commentUp = commentService.findById(comment.getId());
        if (comment.getText()!=null){
            commentUp.setText(comment.getText());
        }
        return ResponseEntity.ok(commentService.save(commentUp));
    }

    @DeleteMapping("/deleteComment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }
}
