package com.example.navigationapp_backend.service;

import com.example.navigationapp_backend.dto.*;
import com.example.navigationapp_backend.entity.Location;
import com.example.navigationapp_backend.entity.Note;
import com.example.navigationapp_backend.entity.TimeTable;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class TimeTableMapper {

    public TimeTableDto timeTableToTimeTableDto(TimeTable timeTable){
        if(timeTable == null){
            return null;
        }
        if(timeTable.getRows()==null){
            TimeTableDto timeTableDto = new TimeTableDto();
            timeTableDto.setId(timeTable.getId());
            timeTableDto.setClass_room(timeTable.getClass_room());
            timeTableDto.setSemester(timeTable.getSemester());
            timeTableDto.setYear(timeTable.getYear());
            return timeTableDto;
        }else{
            List<TimeTableRowDto> timeTableRowDtos =  timeTable.getRows().stream().map(t->
                    new TimeTableRowDto(t.getId(),t.getDayInWeek(),t.getStartDateTime(),t.getEndDateTime(),t.getType(),
                    t.getSubjectAbbreviation(),t.getSubject(),locationToLocationDto(t.getLocation()),
                            t.getProfessor(),t.getRegularity(),notesToNoteDtos(t.getNotes())))
                    .collect(Collectors.toList());
            return new TimeTableDto(timeTable.getId(), timeTable.getClass_room(), timeTable.getSemester(), timeTable.getYear(), timeTableRowDtos);
        }
    }

    private List<NoteDto> notesToNoteDtos(List<Note> notes){
        return notes.stream().map(n-> new NoteDto(n.getId(),n.getTitle(),n.getCompleted())).collect(Collectors.toList());
    }

    public Note noteDtoToNote(NoteDto noteDto){
        if(noteDto==null){
            return null;
        }
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setCompleted(noteDto.getCompleted());
        return note;
    }

    public LocationDto locationToLocationDto(Location location){
        if(location == null) {
            return null;
        }
        LocationDto locationDto = new LocationDto();
        locationDto.setBlock(location.getBlock());
        locationDto.setRoom(location.getRoom());

        if(location.getCoordinates() != null) {
            CoordinatesDto coordinatesDto = new CoordinatesDto();
            coordinatesDto.setId(location.getCoordinates().getId());
            coordinatesDto.setLatitude(location.getCoordinates().getLatitude());
            coordinatesDto.setLongitude(location.getCoordinates().getLongitude());
            locationDto.setCoordinates(coordinatesDto);
        }
        if(location.getGroundFloorPlan() != null) {
            GroundFloorPlanDto groundFloorPlanDto = new GroundFloorPlanDto();
            groundFloorPlanDto.setId(location.getGroundFloorPlan().getId());
            groundFloorPlanDto.setPlan(Base64.getEncoder().encodeToString(location.getGroundFloorPlan().getPlan()));
            locationDto.setGroundFloorPlan(groundFloorPlanDto);
        }
        return locationDto;
    }
}
