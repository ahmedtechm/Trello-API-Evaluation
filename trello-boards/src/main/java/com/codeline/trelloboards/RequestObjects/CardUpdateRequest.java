package com.codeline.trelloboards.RequestObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardUpdateRequest {

    private String title;
    private String description;
    private Integer section;
}
