package com.example.navigationapp_backend.service;


import com.example.navigationapp_backend.entity.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.ws.rs.WebApplicationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class JsoupService {

    public TimeTable createTimeTable(File file) {
            try {
                TimeTable timeTable = new TimeTable();
                Document doc = Jsoup.parse(file,"Windows-1250");
                Element body = doc.getElementsByTag("body").first();
                Element headingDiv = body.getElementsByTag("div").first();
                if(!headingDiv.text().equals("Rozvrh hodín")){
                    throw new WebApplicationException();
                }
                Element tableDescriptionDiv = body.getElementsByTag("div").get(1);
                String tableDescriptionString = tableDescriptionDiv.text();
                String[] tableDescriptionArray = tableDescriptionString.split(",");
                timeTable.setClass_room(tableDescriptionArray[0].trim());
                timeTable.setYear(tableDescriptionArray[1].trim());
                timeTable.setSemester(tableDescriptionArray[2].trim());
                Element tbody = doc.getElementsByTag("tbody").first();
                Elements trs = tbody.getElementsByTag("tr").first().siblingElements();
                for(Element tr : trs){
                    Element dateTd = tr.getElementsByTag("td").first();
                    Elements dateSpans = dateTd.getElementsByTag("span");
                    Elements tds = tr.getElementsByTag("td");
                    for(Element span : dateSpans) {
                        TimeTableRow row = new TimeTableRow();
                        for (Element td : tds) {

                            switch (tds.indexOf(td)) {
                                case 1:
                                    row.setDayInWeek(td.text());
                                    break;
                                case 2:
                                    String[] date = span.text().split("\\.");
                                    LocalDate localDate = LocalDate.of(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                                    String[] time = td.text().split("-");
                                    LocalTime startTime = LocalTime.of(Integer.parseInt(time[0].split(":")[0]),Integer.parseInt(time[0].split(":")[1]));
                                    LocalTime endTime = LocalTime.of(Integer.parseInt(time[1].split(":")[0]),Integer.parseInt(time[1].split(":")[1]));
                                    LocalDateTime startDateTime = LocalDateTime.of(localDate,startTime);
                                    LocalDateTime endDateTime = LocalDateTime.of(localDate,endTime);;
                                    row.setStartDateTime(startDateTime);
                                    row.setEndDateTime(endDateTime);
                                    break;
                                case 4:
                                    row.setType(td.text());
                                    ;
                                    break;
                                case 5:
                                    row.setSubjectAbbreviation(td.text());
                                    ;
                                    break;
                                case 6:
                                    row.setSubject(td.text());
                                    ;
                                    break;
                                case 7:
                                    row.setLocation(deriveLocation(td.text()));
                                    break;
                                case 8:
                                    row.setProfessor(td.text());
                                    ;
                                    break;
                                case 10:
                                    row.setRegularity(td.text());
                                    ;
                                    break;
                            }
                        }
                        timeTable.getRows().add(row);
                    }
                }
                return timeTable;
            }catch (IOException e) {
                throw new WebApplicationException();
            }

    }
    private Location deriveLocation(String room){
        Location location = new Location();
        location.setRoom(Room.valueOf(room));
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(48.3083587);
        coordinates.setLongitude(18.0910412);
        location.setCoordinates(coordinates);
        GroundFloorPlan groundFloorPlan = new GroundFloorPlan();
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            switch (Room.valueOf(room)) {
                case THB00250:
                    location.setBlock(Block.A);
                    groundFloorPlan.setPlan(classLoader.getResourceAsStream("block_A.PNG").readAllBytes());
                    ;
                    break;
                case THB01130:
                    location.setBlock(Block.B);
                    groundFloorPlan.setPlan(classLoader.getResourceAsStream("block_B.PNG").readAllBytes());
                    ;
                    break;
                case THB01140:
                    location.setBlock(Block.B);
                    groundFloorPlan.setPlan(classLoader.getResourceAsStream("block_B.PNG").readAllBytes());
                    ;
                    break;
                case THS01090:
                    location.setBlock(Block.C);
                    groundFloorPlan.setPlan(classLoader.getResourceAsStream("block_C.PNG").readAllBytes());
                    ;
                    break;
                case THS01100:
                    location.setBlock(Block.C);
                    groundFloorPlan.setPlan(classLoader.getResourceAsStream("block_C.PNG").readAllBytes());
                    ;
                    break;
                default:
                    location.setBlock(Block.A);
                    groundFloorPlan.setPlan(classLoader.getResourceAsStream("block_A.PNG").readAllBytes());
            }
            location.setGroundFloorPlan(groundFloorPlan);
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }
}
