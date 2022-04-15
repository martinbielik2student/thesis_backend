package com.example.navigationapp_backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "findAllTimeTables",query = "SELECT t FROM TimeTable t")})
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String class_room;

    private String semester;

    @Column(name = "shool_year")
    private String year;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "TimeTable_TimeTableRow",
            joinColumns = { @JoinColumn(name = "timeTable_id") },
            inverseJoinColumns = { @JoinColumn(name = "timeTableRow_id") }
    )
    public List<TimeTableRow> rows = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<TimeTableRow> getRows() {
        return rows;
    }

    public void setRows(List<TimeTableRow> rows) {
        this.rows = rows;
    }

    public String getClass_room() {
        return class_room;
    }

    public void setClass_room(String class_room) {
        this.class_room = class_room;
    }

    @Override
    public String toString() {
        return "TimeTables{" +
                "id=" + id +
                ", cell='" + class_room + '\'' +
                ", semester='" + semester + '\'' +
                ", year='" + year + '\'' +
                ", rows=" + rows +
                '}';
    }
}
