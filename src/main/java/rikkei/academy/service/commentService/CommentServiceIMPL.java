package rikkei.academy.service.commentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Comment;
import rikkei.academy.repository.ICommentRepository;

import java.util.List;

@Service
public class CommentServiceIMPL implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findCommentByVideoId(Long videoId) {
        return commentRepository.findCommentByVideoId(videoId);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public List<Comment> getRepliesByComment(Comment comment) {
        return comment.getReplies();
    }
}
