package com.example.navigationapp_backend.dto;

public class DeleteNoteRequest {

    private Long timeTableRowId;

    private Long noteId;

    public Long getTimeTableRowId() {
        return timeTableRowId;
    }

    public void setTimeTableRowId(Long timeTableRowId) {
        this.timeTableRowId = timeTableRowId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }
}
