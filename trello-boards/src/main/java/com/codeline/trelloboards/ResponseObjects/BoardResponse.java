package com.codeline.trelloboards.ResponseObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {

    private Long boardId;
    private String name;
    private Map<Integer, String> columns;


}

