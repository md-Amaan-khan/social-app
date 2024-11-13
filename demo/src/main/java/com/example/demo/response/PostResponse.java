package com.example.demo.response;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private Long authorId;
    private String createdAt;
    private String updatedAt;

    public PostResponse(Post source) {
        this.postId = source.getPostId();
        this.title = source.getTitle();
        this.content = source.getContent();
        this.authorId = mapAuthor(source.getUser());
        this.createdAt = source.getCreatedAt();
        this.updatedAt = source.getUpdatedAt();
    }

    public Long mapAuthor(User user) {
        if (user!=null){
            return user.getUserId();
        }
        return null;
    }
}
