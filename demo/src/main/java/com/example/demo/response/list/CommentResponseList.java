package com.example.demo.response.list;

import com.example.demo.response.CommentResponse;
import com.example.demo.response.UserResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CommentResponseList {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortDir;
    private String sortBy;
    private Integer totalPages;
    private List<CommentResponse> commentResponses;

    public CommentResponseList(Integer pageNumber, Integer pageSize, String sortDir, String sortBy, Integer totalPages, List<CommentResponse> commentResponses) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDir = sortDir;
        this.sortBy = sortBy;
        this.totalPages = totalPages;
        this.commentResponses = commentResponses;
    }
}
