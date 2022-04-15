package com.example.navigationapp_backend.controller;

import com.example.navigationapp_backend.annotations.Security;
import com.example.navigationapp_backend.dto.TimeTableDto;
import com.example.navigationapp_backend.dto.UserDto;
import com.example.navigationapp_backend.entity.TimeTable;
import com.example.navigationapp_backend.repository.TimeTableRepository;
import com.example.navigationapp_backend.service.JsoupService;
import com.example.navigationapp_backend.service.TimeTableService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Path("timetables")
@Produces({MediaType.APPLICATION_JSON})
public class TimeTableRestController {

    @Inject
    TimeTableService timeTableService;

    @POST
    @Consumes({MediaType.APPLICATION_OCTET_STREAM,"image/png","image/jpg","image/jpeg"})
    public Response save(File file){
        timeTableService.save(file);
        return Response.status(Response.Status.OK).entity(true).build();
    }

    @POST
    @Path("saveAll")
    @Security
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response saveAll(List<File> files){
        List<TimeTableDto> timeTables = timeTableService.saveAll(files);
        return Response.status(Response.Status.CREATED).entity(true).build();
    }

    @GET
    @Path("getAll")
    @Security
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll(){
        return Response.status(Response.Status.OK).entity(timeTableService.getAll()).build();
    }

    @GET
    @Path("getByAisId/{id}")
    @Security
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByAisId(@PathParam("id") Long id){
        return Response.status(Response.Status.OK).entity(timeTableService.getById(id)).build();
    }

    @GET
    @Path("delete/{id}")
    @Security
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id){
        timeTableService.delete(id);
        return Response.status(Response.Status.OK).entity(true).build();
    }
}
