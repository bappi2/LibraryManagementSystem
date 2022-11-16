package com.mmefta.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @JsonProperty("name")
    private String name;

    @JsonProperty("biography")
    private String biography;
}
