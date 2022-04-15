package com.example.navigationapp_backend.dto;

public class ChangeNoteStatusRequest {

    private Long timeTableRowId;

    private Long noteId;

    private boolean completed;

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
