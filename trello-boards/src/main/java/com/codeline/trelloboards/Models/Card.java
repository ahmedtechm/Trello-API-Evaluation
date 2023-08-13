package com.codeline.trelloboards.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "card")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer section;

    // Create a Many-to-One relationship with the Board entity
    @ManyToOne
    @JoinColumn(name = "board_id") // Specify the foreign key column in the card table
    private Board board;

}