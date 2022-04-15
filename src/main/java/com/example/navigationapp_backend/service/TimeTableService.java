package com.example.navigationapp_backend.service;

import com.example.navigationapp_backend.dto.TimeTableDto;
import com.example.navigationapp_backend.entity.TimeTable;
import com.example.navigationapp_backend.repository.TimeTableRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class TimeTableService implements CrudService<TimeTableDto, File> {

    @Inject
    JsoupService jsoupService;

    @Inject
    TimeTableRepository timeTableRepository;

    @Inject
    TimeTableMapper timeTableMapper;

    @Override
    public TimeTableDto save(File file) {
        TimeTable timetable = jsoupService.createTimeTable(file);
        return timeTableMapper.timeTableToTimeTableDto(timeTableRepository.save(timetable));
    }

    @Override
    public void delete(Long id) {
        timeTableRepository.delete(timeTableRepository.getById(id));
    }

    @Override
    public TimeTableDto getById(Long id) {
        return timeTableMapper.timeTableToTimeTableDto(timeTableRepository.getById(id));
    }

    @Override
    public List<TimeTableDto> getAll() {
        List<TimeTableDto> list = timeTableRepository.getAll().stream().map(timeTableMapper::timeTableToTimeTableDto).collect(Collectors.toList());
        return list;
    }

    public List<TimeTableDto> saveAll(List<File> files){
        List<TimeTableDto> savedTimeTables = new ArrayList<>();
        files.forEach(f -> savedTimeTables.add(this.save(f)));
        return savedTimeTables;
    }
}
