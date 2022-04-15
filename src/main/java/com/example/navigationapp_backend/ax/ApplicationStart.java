package com.example.navigationapp_backend.ax;

import com.example.navigationapp_backend.dto.UserBaseDto;
import com.example.navigationapp_backend.dto.UserDto;
import com.example.navigationapp_backend.entity.Coordinates;
import com.example.navigationapp_backend.entity.Note;
import com.example.navigationapp_backend.entity.TimeTable;
import com.example.navigationapp_backend.repository.TimeTableRepository;
import com.example.navigationapp_backend.service.JsoupService;
import com.example.navigationapp_backend.service.ServiceQualifier;
import com.example.navigationapp_backend.service.UserDtoService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.example.navigationapp_backend.service.ServiceQualifier.ServiceQualifierType.USERDTOREPOSITORY;

@Singleton
@Startup
public class ApplicationStart {

    @Inject
    @ServiceQualifier(value = USERDTOREPOSITORY)
    UserDtoService userService;

    @Inject
    private JsoupService jsoupService;

    @Inject
    TimeTableRepository timeTableRepository;

    @PostConstruct
    public void initializeAdmin() {
            fillDatabase();
    }

    private void fillDatabase(){
        try {
            File file = new File("navigationApp_backend\\src\\main\\resources\\Timetable.html");
            TimeTable timeTable = jsoupService.createTimeTable(file);
            timeTable.getRows().forEach(timeTableRow -> {
                List<Note> notes = new ArrayList<>();
                notes.add(new Note("Code", true));
                notes.add(new Note("Meeting with team at 9", false));
                notes.add(new Note("Check Emails", false));
                notes.add(new Note("Write an article", false));
                timeTableRow.setNotes(notes);
            });
            TimeTable savedTimeTable = timeTableRepository.save(timeTable);
            TimeTable toSave = timeTableRepository.getById(savedTimeTable.getId());

            UserDto userDto_one = new UserDto();
            userDto_one.setAisId(1111L);
            userDto_one.setPassword("password1");
            userDto_one.setTimeTable(toSave);
            UserBaseDto userBaseDto = userService.save(userDto_one);

            UserDto userDto_two = new UserDto();
            userDto_two.setAisId(2222L);
            userDto_two.setPassword("password2");
            userDto_two.setTimeTable(toSave);
            UserBaseDto userBaseDto_two = userService.save(userDto_two);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
