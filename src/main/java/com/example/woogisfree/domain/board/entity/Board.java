package com.example.woogisfree.domain.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Board {
    @Id
    @Column(name = "board_id")
    private Long id;

}
