package com.example.navigationapp_backend.dto;

import com.example.navigationapp_backend.entity.Block;
import com.example.navigationapp_backend.entity.Room;

public class LocationDto {

    private Long id;

    private Block block;

    private Room room;

    private GroundFloorPlanDto groundFloorPlan;

    private CoordinatesDto coordinates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public GroundFloorPlanDto getGroundFloorPlan() {
        return groundFloorPlan;
    }

    public void setGroundFloorPlan(GroundFloorPlanDto groundFloorPlan) {
        this.groundFloorPlan = groundFloorPlan;
    }

    public CoordinatesDto getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesDto coordinates) {
        this.coordinates = coordinates;
    }
}
