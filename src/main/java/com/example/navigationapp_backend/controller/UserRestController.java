package com.example.navigationapp_backend.controller;

import com.example.navigationapp_backend.annotations.Security;
import com.example.navigationapp_backend.dto.*;
import com.example.navigationapp_backend.service.ServiceQualifier;
import com.example.navigationapp_backend.service.UserDtoService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("user")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserRestController {

    @Inject
    @ServiceQualifier(ServiceQualifier.ServiceQualifierType.USERDTOREPOSITORY)
    UserDtoService userDtoService;

    @POST
    @Path("saveAll")
    @Security
    public Response saveAll(List<UserDto> users){
        return Response.status(Response.Status.CREATED).entity(userDtoService.saveAll(users)).build();
    }

    @GET
    @Path("getAll")
    @Security
    public Response getAll(){
        return Response.status(Response.Status.OK).entity(userDtoService.getAll()).build();
    }

    @GET
    @Path("getByAisId/{id}")
    @Security
    public Response getByAisId(@PathParam("id") Long id){
        return Response.status(Response.Status.OK).entity(userDtoService.getById(id)).build();
    }

    @GET
    @Path("delete/{id}")
    @Security
    public Response delete(@PathParam("id") Long id){
        userDtoService.delete(id);
        return Response.status(Response.Status.OK).entity(true).build();
    }

    @GET
    @Path("timetable")
    @Security
    public Response getTimeTable(){
        TimeTableDto timeTableDto = userDtoService.getTimeTable();
        return Response.status(Response.Status.OK).entity(timeTableDto).build();
    }

    @POST
    @Path("timetable_row/addNote")
    @Security
    public Response addNote(AddNoteRequest addNoteRequest){
        userDtoService.addNote(addNoteRequest);
        return Response.status(Response.Status.CREATED).entity(true).build();
    }

    @POST
    @Path("timetable_row/deleteNote")
    @Security
    public Response deleteNote(DeleteNoteRequest deleteNoteRequest){
        userDtoService.deleteNote(deleteNoteRequest);
        return Response.status(Response.Status.OK).entity(true).build();
    }

    @GET
    @Path("timetable_row/{id}")
    @Security
    public Response getTimeTableRow(@PathParam("id") Long id){
        TimeTableRowDto timeTableRowDto = userDtoService.getTimeTableRow(id);
        return Response.status(Response.Status.OK).entity(timeTableRowDto).build();
    }

    @POST
    @Path("timetable_row/changeNoteState")
    @Security
    public Response changeNoteState(ChangeNoteStatusRequest changeNoteStatusRequest){
        userDtoService.changeNoteState(changeNoteStatusRequest);
        return Response.status(Response.Status.OK).entity(true).build();
    }

    @GET
    @Path("timetable_row/notes/{id}")
    @Security
    public Response getRowNotes(@PathParam("id")Long id){
        List<NoteDto> noteDtos = userDtoService.getRowNotes(id);
        return Response.status(Response.Status.OK).entity(noteDtos).build();
    }

    @GET
    @Path("location/row/{id}")
    @Security
    public Response getRowLocation(@PathParam("id")Long rowId){
        LocationDto locationDto = userDtoService.getRowLocation(rowId);
        return Response.status(Response.Status.OK).entity(locationDto).build();
    }

    @GET
    @Path("plan/row/{id}")
    @Security
    public Response getRowPlan(@PathParam("id")Long rowId){
        String plan = userDtoService.getRowPlan(rowId);
        return Response.status(Response.Status.OK).entity(plan).build();
    }
}
