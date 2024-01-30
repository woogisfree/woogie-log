//package com.example.woogisfree.domain.comment.controller;
//
//import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
//import com.example.woogisfree.domain.comment.entity.Comment;
//import com.example.woogisfree.domain.comment.service.CommentService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Tag(name = "Comment API")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/comments")
//public class CommentController {
//
//    private final CommentService commentService;
//
//    @PostMapping("/{articleId}")
//    public ResponseEntity<Comment> addComment(@PathVariable("articleId") long articleId,
//                                              @RequestBody AddCommentRequest request) {
//
//        Comment newComment = commentService.addComment(articleId, request);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(newComment);
//    }
//}
