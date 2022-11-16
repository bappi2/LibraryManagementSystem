package com.mmefta.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmefta.lms.util.Constants.*;
import com.mmefta.lms.util.Constants.BookFormat;
import java.util.Date;
@lombok.Getter
@lombok.Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookItem {
    @JsonProperty("barCode")
    private String barCode;
    @JsonProperty("isReferenceOnly")
    private boolean isReferenceOnly;
    @JsonProperty("borrowed")
    private Date borrowed;
    @JsonProperty("dueDate")
    private Date dueDate;
    @JsonProperty("price")
    private double price;
    @JsonProperty("format")
    private BookFormat format;
    @JsonProperty("status")
    private BookStatus status;
    @JsonProperty("purchaseDate")
    private Date purchaseDate;
    @JsonProperty("publicationDate")
    private Date publicationDate;
    @JsonProperty("book")
    private Book book;
}
