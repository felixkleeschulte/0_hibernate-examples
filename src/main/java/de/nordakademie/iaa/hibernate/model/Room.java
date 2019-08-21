package de.nordakademie.iaa.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
    private Long id;
    private String building;
    private Integer roomNumber;

    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public boolean isProjectorPresent() {
        return projectorPresent;
    }

    public void setProjectorPresent(boolean projectorPresent) {
        this.projectorPresent = projectorPresent;
    }

    private Integer seats;
    private boolean projectorPresent;

}
