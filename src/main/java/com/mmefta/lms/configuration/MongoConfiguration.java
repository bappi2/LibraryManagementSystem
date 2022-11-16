package com.mmefta.lms.configuration;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MongoConfiguration {
    private String dbName;
    private String mongoDBPrimary;
    private String mongoDBSecondary;
    private int mongoDBPort;
    private String authorsCollection;
    private String bookItemCollection;
    private String bookCollection;
}
