package com.example.navigationapp_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({@NamedQuery(name = "findAllUsers",query = "SELECT u FROM User u")})
public class User {
    @Id
    private Long aisId;

    @Column(name = "password")
    private String encryptedPassword;

    private String salt;

    @ManyToOne(fetch = FetchType.LAZY)
    private TimeTable timeTable;

    public void setAisId(Long aisId) {
        this.aisId = aisId;
    }

    public Long getAisId() {
        return aisId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
}
