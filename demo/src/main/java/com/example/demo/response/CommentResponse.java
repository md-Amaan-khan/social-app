package com.example.demo.response;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String content;
    private Long authorId;
    private String createdAt;

    public CommentResponse(Comment source) {
        this.commentId = source.getCommentId();
        this.postId = mapPost(source.getPost());
        this.content = source.getContent();
        this.authorId = mapAuthor(source.getAuthor());
//        this.authorId = source.getUser().getUserId()!=null?source.getUser().getUserId():null;
        this.createdAt = source.getCreatedAt();
    }
public Long mapAuthor(User user){
        if(user!=null){
            return user.getUserId();
        }
        return null;
}
public  Long mapPost(Post post) {
    if (post != null) {
        return post.getPostId();
    }

    return null;
}
}

