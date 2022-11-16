package com.mmefta.lms.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.mmefta.lms.model.Book;
import com.mmefta.lms.model.BookItem;
import com.mmefta.lms.services.LibraryManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/lms")
@Api("/lms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibraryManagementServiceResourceV1 {
    private final LibraryManagementService lms;

    public LibraryManagementServiceResourceV1(LibraryManagementService lms) {
        this.lms = lms;
    }

    @GET
    @Path("/searchbook")
    @ExceptionMetered
    @ApiOperation(
            value = "a member returns a book",
            notes = "creates a transaction to returns the book.")
    public void searchbook(
            @NotNull String barCode,
            @NotNull @HeaderParam("x-catalog-userid") String userId,
            @NotNull @HeaderParam("Authorization") String authToken,
            @Suspended final AsyncResponse response) {

    }

    @PUT
    @Path("/addbookitem")
    @ExceptionMetered
    @ApiOperation(
            value = "Upsert bookItem",
            notes = "Create bookItem if not exists, otherwise update it")
    public Response putBook (
            @NotNull BookItem bookItem
            //@NotNull @HeaderParam("x-catalog-userid") String userId,
            //@NotNull @HeaderParam("Authorization") String authToken,
            //@Suspended final AsyncResponse response
    ) throws Exception {
        lms.addBookItem(bookItem);
        return Response.status(Response.Status.OK).entity(true).build();
    }
/*
    @PUT
    @Path("/issuebook")
    @ExceptionMetered
    @ApiOperation(
            value = "a member requests a book, librarian  issuebook",
            notes = "creates a transaction to cover the request and issues the book.")
    public void issuebook(
            @NotNull String barCode,
            @NotNull String memberId,
            @NotNull @HeaderParam("x-catalog-userid") String userId,
            @NotNull @HeaderParam("Authorization") String authToken,
            @Suspended final AsyncResponse response) {

    }

    @PUT
    @Path("/returnbook")
    @ExceptionMetered
    @ApiOperation(
            value = "a member returns a book",
            notes = "creates a transaction to returns the book.")
    public void returnbook(
            @NotNull String barCode,
            @NotNull String memberId,
            @NotNull @HeaderParam("x-catalog-userid") String userId,
            @NotNull @HeaderParam("Authorization") String authToken,
            @Suspended final AsyncResponse response) {

    }
 */
}
