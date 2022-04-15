package com.example.navigationapp_backend.entity;

import javax.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Block block;

    @Enumerated(EnumType.STRING)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    private GroundFloorPlan groundFloorPlan;

    @ManyToOne(fetch = FetchType.EAGER)
    private Coordinates coordinates;

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

    public GroundFloorPlan getGroundFloorPlan() {
        return groundFloorPlan;
    }

    public void setGroundFloorPlan(GroundFloorPlan groundFloorPlan) {
        this.groundFloorPlan = groundFloorPlan;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (getBlock() != location.getBlock()) return false;
        if (getRoom() != location.getRoom()) return false;
        if (getGroundFloorPlan() != null ? !getGroundFloorPlan().equals(location.getGroundFloorPlan()) : location.getGroundFloorPlan() != null)
            return false;
        return getCoordinates() != null ? getCoordinates().equals(location.getCoordinates()) : location.getCoordinates() == null;
    }

    @Override
    public int hashCode() {
        int result = getBlock() != null ? getBlock().hashCode() : 0;
        result = 31 * result + (getRoom() != null ? getRoom().hashCode() : 0);
        result = 31 * result + (getGroundFloorPlan() != null ? getGroundFloorPlan().hashCode() : 0);
        result = 31 * result + (getCoordinates() != null ? getCoordinates().hashCode() : 0);
        return result;
    }
}
