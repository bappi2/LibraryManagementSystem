package com.mmefta.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.PrimitiveIterator;

@lombok.Getter
@lombok.Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonProperty("ISBN")
    private String ISBN;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("language")
    private String language;

    @JsonProperty("numOfPages")
    private int numOfPages;

    @JsonProperty("authorList")
    private List<Author> authorList;
}
