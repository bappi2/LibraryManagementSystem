package com.mmefta.lms.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmefta.lms.model.BookItem;
import com.mmefta.lms.mongo.MongoManager;

import java.util.ArrayList;
import java.util.List;

public class LibraryManagementService {
    private final MongoManager mongoManager;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public LibraryManagementService(MongoManager mongoManager) {
        this.mongoManager = mongoManager;
    }

    public void addBookItem(BookItem bookItem) throws Exception {
        List<String> bookItemList = new ArrayList<>();
        bookItemList.add(objectMapper.writeValueAsString(bookItem));
        mongoManager.insertAll("BookItems", bookItemList);
    }
}
