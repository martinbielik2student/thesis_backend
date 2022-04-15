package com.example.navigationapp_backend.dto;

public class AddNoteRequest {

    private Long timeTableRowId;

    private NoteDto note;

    public Long getTimeTableRowId() {
        return timeTableRowId;
    }

    public void setTimeTableRowId(Long timeTableRowId) {
        this.timeTableRowId = timeTableRowId;
    }

    public NoteDto getNote() {
        return note;
    }

    public void setNote(NoteDto note) {
        this.note = note;
    }
}
