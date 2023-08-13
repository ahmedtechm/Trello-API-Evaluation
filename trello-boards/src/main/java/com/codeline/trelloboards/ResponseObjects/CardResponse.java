package com.codeline.trelloboards.ResponseObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {

    private Long card_id;
    private String title;
    private String description;
    private Integer section;
}
