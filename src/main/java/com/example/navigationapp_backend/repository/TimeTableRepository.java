package com.example.navigationapp_backend.repository;


import com.example.navigationapp_backend.entity.Coordinates;
import com.example.navigationapp_backend.entity.GroundFloorPlan;
import com.example.navigationapp_backend.entity.Location;
import com.example.navigationapp_backend.entity.TimeTable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TimeTableRepository implements ICrudRepository<TimeTable>{

    @Inject
    EntityManager em;

    @Override
    public TimeTable save(TimeTable timeTable) {
        if(timeTable.getId()==null) {
            timeTable.getRows().forEach(
                    timeTableRow -> {
                                if (timeTableRow.getLocation().getId() == null) {
                                    if (timeTableRow.getLocation().getCoordinates().getId() == null) {
                                        em.persist(timeTableRow.getLocation().getCoordinates());
                                        Coordinates coordinates = timeTableRow.getLocation().getCoordinates();
                                        timeTable.getRows().forEach(timeTableRow1 -> {
                                            if(timeTableRow1.getLocation().getCoordinates().equals(coordinates)){
                                                timeTableRow1.getLocation().getCoordinates().setId(coordinates.getId());
                                            }
                                        });
                                    } else {
                                        em.merge(timeTableRow.getLocation().getCoordinates());
                                    }
                                    if (timeTableRow.getLocation().getGroundFloorPlan().getId() == null) {
                                        em.persist(timeTableRow.getLocation().getGroundFloorPlan());
                                        GroundFloorPlan groundFloorPlan = timeTableRow.getLocation().getGroundFloorPlan();
                                        timeTable.getRows().forEach(timeTableRow1 -> {
                                            if(timeTableRow1.getLocation().getGroundFloorPlan().equals(groundFloorPlan)){
                                                timeTableRow1.getLocation().getGroundFloorPlan().setId(groundFloorPlan.getId());
                                            }
                                        });
                                    } else {
                                        em.merge(timeTableRow.getLocation().getGroundFloorPlan());
                                    }
                                    em.persist(timeTableRow.getLocation());
                                    Location location = timeTableRow.getLocation();
                                    timeTable.getRows().forEach(timeTableRow1 -> {
                                        if(timeTableRow1.getLocation().equals(location)){
                                            timeTableRow1.getLocation().setId(location.getId());
                                        }
                                    });
                                } else {
                                    em.merge(timeTableRow.getLocation());
                                }
                        }
                    );
            em.persist(timeTable);
        }else{
            em.merge(timeTable);
        }
        return timeTable;
    }

    @Override
    public void delete(TimeTable timeTable) {
        em.remove(timeTable);
    }

    @Override
    public TimeTable getById(Long id) {
        return em.find(TimeTable.class,id);
    }

    @Override
    public List<TimeTable> getAll() {
        return em.createNamedQuery("findAllTimeTables",TimeTable.class).getResultList();
    }
}
