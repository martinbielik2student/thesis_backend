package com.example.navigationapp_backend.service;


import com.example.navigationapp_backend.ax.ApplicationState;
import com.example.navigationapp_backend.dto.*;
import com.example.navigationapp_backend.entity.Note;
import com.example.navigationapp_backend.entity.TimeTable;
import com.example.navigationapp_backend.entity.TimeTableRow;
import com.example.navigationapp_backend.entity.User;
import com.example.navigationapp_backend.repository.ICrudRepository;
import com.example.navigationapp_backend.repository.RepositoryQualifier;
import com.example.navigationapp_backend.repository.TimeTableRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@ServiceQualifier(ServiceQualifier.ServiceQualifierType.USERDTOREPOSITORY)
public class UserDtoService implements CrudService<UserBaseDto, UserDto>
{

	@Inject
	@RepositoryQualifier(RepositoryQualifier.RepositoryQualifierType.USERREPOSITORY)
	private ICrudRepository<User> repository;
	
	@Inject
	private UserMapper mapper;

	@Inject
	ApplicationState applicationState;

	@Inject
	TimeTableMapper timeTableMapper;
	
	@Override
	public UserBaseDto save(UserDto t) {
		// TODO Auto-generated method stub
			User user = repository.getById(t.getAisId());
			if (user==null){
				UserBaseDto u = mapper.userToUserBaseDto(repository.save(mapper.userDtoToUser(t)));
			return u;
			}else{
				throw new WebApplicationException("User with this ais Id is already registered.");
			}

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.delete(repository.getById(id));
	}

	@Override
	public UserBaseDto getById(Long id) {
		// TODO Auto-generated method stub
		return mapper.userToUserBaseDto(repository.getById(id));
	}

	@Override
	public List<UserBaseDto> getAll() {
		// TODO Auto-generated method stub
		List<UserBaseDto> list =repository.getAll().stream().map(mapper::userToUserBaseDto).collect(Collectors.toList());
		 return list;
	}

	public List<UserBaseDto> saveAll(List<UserDto> users) {
		List<UserBaseDto> userBaseDtos = new ArrayList<>();
		users.forEach(u -> userBaseDtos.add(this.save(u)));
		return userBaseDtos;
	}

	public TimeTableDto getTimeTable(){
		TimeTableDto timeTableDto = fetchTimeTableDto();
		timeTableDto.getRows().removeIf(r->{
			return r.getStartDateTime().isBefore(LocalDateTime.now());
		});
		timeTableDto.getRows().sort((o1, o2)->{
			if(o1.getStartDateTime().isBefore(o2.getStartDateTime())){
				return -1;
			}
			if(o1.getStartDateTime().isAfter(o2.getStartDateTime())){
				return 1;
			}
			return 0;
		});
		return timeTableDto;
	}

	public void addNote(AddNoteRequest addNoteRequest) {
		User user = repository.getById(applicationState.getAisId());
		TimeTable timeTable = user.getTimeTable();

		TimeTableRow timeTableRow = timeTable.getRows().stream()
				.filter(t -> {return t.getId().equals(addNoteRequest.getTimeTableRowId());})
				.findFirst().orElseThrow(()->new WebApplicationException(
						Response
								.status(Response.Status.BAD_REQUEST)
								.type(MediaType.TEXT_PLAIN)
								.entity("Non existing record in timetable.")
								.build()
				));
		timeTableRow.getNotes().add(timeTableMapper.noteDtoToNote(addNoteRequest.getNote()));
	}

	public void deleteNote(DeleteNoteRequest deleteNoteRequest) {
		User user = repository.getById(applicationState.getAisId());
		TimeTable timeTable = user.getTimeTable();

		TimeTableRow timeTableRow = timeTable.getRows().stream()
				.filter(t -> {return t.getId().equals(deleteNoteRequest.getTimeTableRowId());})
				.findFirst().orElseThrow(()->new WebApplicationException(
						Response
								.status(Response.Status.BAD_REQUEST)
								.type(MediaType.TEXT_PLAIN)
								.entity("Non existing record in timetable.")
								.build()
				));
		timeTableRow.getNotes().removeIf(noteDto -> noteDto.getId().equals(deleteNoteRequest.getNoteId()));
	}

	public TimeTableRowDto getTimeTableRow(Long id) {
		TimeTableDto timeTableDto = fetchTimeTableDto();
		TimeTableRowDto timeTableRow = timeTableDto.getRows().stream()
				.filter(timeTableRowDto -> {return timeTableRowDto.getId().equals(id);})
				.findFirst().orElseThrow(()->new WebApplicationException(
						Response
								.status(Response.Status.BAD_REQUEST)
								.type(MediaType.TEXT_PLAIN)
								.entity("Non existing record in timetable.")
								.build()
				));
		return timeTableRow;
	}

	private TimeTableRowDto fetchTimeTableRow(TimeTableDto timeTableDto, Long id){
		TimeTableRowDto timeTableRow = timeTableDto.getRows().stream()
				.filter(timeTableRowDto -> {return timeTableRowDto.getId().equals(id);})
				.findFirst().orElseThrow(()->new WebApplicationException(
						Response
								.status(Response.Status.BAD_REQUEST)
								.type(MediaType.TEXT_PLAIN)
								.entity("Non existing record in timetable.")
								.build()
				));
		return timeTableRow;
	}

	public void changeNoteState(ChangeNoteStatusRequest changeNoteStatusRequest) {
		User user = repository.getById(applicationState.getAisId());
		TimeTable timeTable = user.getTimeTable();

		TimeTableRow timeTableRow = timeTable.getRows().stream()
				.filter(t -> {return t.getId().equals(changeNoteStatusRequest.getTimeTableRowId());})
				.findFirst().orElseThrow(()->new WebApplicationException(
						Response
								.status(Response.Status.BAD_REQUEST)
								.type(MediaType.TEXT_PLAIN)
								.entity("Non existing record in timetable.")
								.build()
				));

		Note note = timeTableRow.getNotes().stream()
				.filter(noteDto -> noteDto.getId().equals(changeNoteStatusRequest.getNoteId()))
				.findFirst().orElseThrow(()->new WebApplicationException(
						Response
								.status(Response.Status.BAD_REQUEST)
								.type(MediaType.TEXT_PLAIN)
								.entity("Non existing note.")
								.build()
				));
		note.setCompleted(changeNoteStatusRequest.isCompleted());//note.getCompl..

	}

	public List<NoteDto> getRowNotes(Long id) {
		TimeTableDto timeTableDto = fetchTimeTableDto();
		TimeTableRowDto timeTableRow = fetchTimeTableRow(timeTableDto,id);
		timeTableRow.getNotes().sort((o1, o2) ->(int) (o1.getId()-o2.getId()));
		return timeTableRow.getNotes();
	}

	public LocationDto getRowLocation(Long rowId) {
		TimeTableDto timeTableDto = fetchTimeTableDto();
		TimeTableRowDto timeTableRow = fetchTimeTableRow(timeTableDto,rowId);
		return timeTableRow.getLocation();
	}

	public String getRowPlan(Long rowId) {
		TimeTableDto timeTableDto = fetchTimeTableDto();
		TimeTableRowDto timeTableRow = fetchTimeTableRow(timeTableDto,rowId);
		return timeTableRow.getLocation().getGroundFloorPlan().getPlan();
	}

	private TimeTableDto fetchTimeTableDto(){
		User user = repository.getById(applicationState.getAisId());
		TimeTable timeTable = user.getTimeTable();
		if(timeTable == null){
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity(false).build());
		}
		return timeTableMapper.timeTableToTimeTableDto(timeTable);
	}
}
