package com.codeline.trelloboards.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Data
@Entity

public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    //private Long boardId;


    // Define a map to store columns with their positions
    @ElementCollection
    //@CollectionTable(name = "board_columns_mapping", joinColumns = @JoinColumn(name = "board_id"))
    @MapKeyColumn(name = "column_position")
    @Column(name = "column_name")
    private Map<Integer, String> columns = new HashMap<>();
}