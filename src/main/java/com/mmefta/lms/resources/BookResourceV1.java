package com.mmefta.lms.resources;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.mmefta.lms.model.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.PATCH;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Path("/v1/book")
//@Api("/v1/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResourceV1 {
    private static final int BULK_LIMIT = 100;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find Book by Id", response = Book.class)
    public void getBook(
            @NotNull @PathParam("id") Long bookId,
            @HeaderParam("x-catalog-userid") String userId,
            @NotNull @HeaderParam("Authorization") String authToken,
            @Suspended final AsyncResponse response) {

    }


    @PATCH
    @Path("/bulk")
    //@Secured
    @ExceptionMetered
    @ApiOperation(value = "Bulk patch product")
    public void patchProduct(
            @Size(min = 1, max = BULK_LIMIT) List<Book> bookList,
            @NotNull @HeaderParam("x-catalog-userid") String userId,
            @NotNull @HeaderParam("Authorization") String authToken,
            @Suspended final AsyncResponse response) {

    }

    @PUT
    //@Secured
    @ExceptionMetered
    @ApiOperation(
            value = "Upsert book",
            notes = "Create book if not exists, otherwise update it")
    public void putBook(
            @NotNull Book book,
            @NotNull @HeaderParam("x-catalog-userid") String userId,
            @NotNull @HeaderParam("Authorization") String authToken,
            @Suspended final AsyncResponse response) {


    }

    @DELETE
    @Path("/{id}")
    //@Secured
    @ApiOperation(value = "Delete book by Id", response = Book.class, hidden = false)
    public void deleteBook(
            @NotNull @PathParam("id") Long productId,
            @HeaderParam("x-catalog-userid") String userId,
            @NotNull @HeaderParam("Authorization") String authToken,
            @Suspended final AsyncResponse response) {


    }


}
