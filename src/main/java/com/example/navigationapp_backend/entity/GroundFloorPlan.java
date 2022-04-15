package com.example.navigationapp_backend.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class GroundFloorPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPlan() {
        return plan;
    }

    public void setPlan(byte[] plan) {
        this.plan = plan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroundFloorPlan that = (GroundFloorPlan) o;

        return Arrays.equals(getPlan(), that.getPlan());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getPlan());
    }
}
