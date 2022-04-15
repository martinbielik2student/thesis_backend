package com.example.navigationapp_backend.dto;

import com.example.navigationapp_backend.ax.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeTableRowDto {

    private Long id;

    private String dayInWeek;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime startDateTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime endDateTime;

    private String type;

    private String subjectAbbreviation;

    private String subject;

    private LocationDto location;

    private List<NoteDto> notes;

    private String professor;

    private String regularity;

    public TimeTableRowDto(){

    }

    public TimeTableRowDto(Long id, String dayInWeek, LocalDateTime startDateTime, LocalDateTime endDateTime, String type, String subjectAbbreviation, String subject, LocationDto location, String professor, String regularity, List<NoteDto> notes) {
        this.id = id;
        this.dayInWeek = dayInWeek;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.type = type;
        this.subjectAbbreviation = subjectAbbreviation;
        this.subject = subject;
        this.location = location;
        this.professor = professor;
        this.regularity = regularity;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(String dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubjectAbbreviation() {
        return subjectAbbreviation;
    }

    public void setSubjectAbbreviation(String subjectAbbreviation) {
        this.subjectAbbreviation = subjectAbbreviation;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getRegularity() {
        return regularity;
    }

    public void setRegularity(String regularity) {
        this.regularity = regularity;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public List<NoteDto> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDto> notes) {
        this.notes = notes;
    }
}
