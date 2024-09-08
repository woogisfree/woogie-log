package com.example.woogisfree.domain.comment.api;

import com.example.woogisfree.domain.comment.dto.AddCommentRequest;
import com.example.woogisfree.domain.comment.dto.AddCommentResponse;
import com.example.woogisfree.domain.comment.dto.UpdateCommentRequest;
import com.example.woogisfree.domain.comment.service.CommentService;
import com.example.woogisfree.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Comment", description = "댓글 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/{articleId}")
    public ResponseEntity<AddCommentResponse> addComment(@AuthenticationPrincipal UserDetails userDetails,
                                                         @PathVariable("articleId") long articleId,
                                                         @RequestBody AddCommentRequest request) {
        Long userId = userService.getUserIdFromUserDetails(userDetails);
        AddCommentResponse result = commentService.save(articleId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable("commentId") long commentId,
                                              @RequestBody UpdateCommentRequest request) {
        commentService.update(commentId, request.getContent());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
