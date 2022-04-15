package com.example.navigationapp_backend.dto;

import com.example.navigationapp_backend.entity.TimeTableRow;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class TimeTableDto {

    private Long id;

    private String class_room;

    private String semester;

    private String year;

    private List<TimeTableRowDto> rows = new ArrayList<>();

    public TimeTableDto(){

    }

    public TimeTableDto(Long id, String class_room, String semester, String year, List<TimeTableRowDto> rows) {
        this.id = id;
        this.class_room = class_room;
        this.semester = semester;
        this.year = year;
        this.rows = rows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClass_room() {
        return class_room;
    }

    public void setClass_room(String class_room) {
        this.class_room = class_room;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<TimeTableRowDto> getRows() {
        return rows;
    }

    public void setRows(List<TimeTableRowDto> rows) {
        this.rows = rows;
    }
}
