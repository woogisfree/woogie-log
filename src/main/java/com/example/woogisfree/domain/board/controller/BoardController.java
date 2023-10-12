package com.example.woogisfree.domain.board.controller;

import com.example.woogisfree.domain.board.entity.Board;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Board API")
@RequestMapping("/api/board")
@RestController
public class BoardController {

    @GetMapping("/list")
    public ResponseEntity<List<Board>> findAll() {
        return null;
    }
}
