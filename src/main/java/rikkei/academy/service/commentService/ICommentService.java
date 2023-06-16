package rikkei.academy.service.commentService;

import rikkei.academy.model.Comment;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface ICommentService extends IGenericService<Comment,Long> {
    Comment findCommentByVideoId(Long videoId);
    Comment getCommentById(Long commentId);
    List<Comment> getRepliesByComment(Comment comment);
}
