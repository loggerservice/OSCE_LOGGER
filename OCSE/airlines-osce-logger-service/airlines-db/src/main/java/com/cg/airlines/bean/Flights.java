package com.cg.airlines.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@SequenceGenerator(name = "seq", sequenceName = "seq", initialValue = 100011, allocationSize = 10)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Flights implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8277222777283108869L;
    @Id
    @Column(name = "flightId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int flightId;
    private String flightName;
    private String depCity;
    private String arrCity;
    private String depDate;
    private String arrDate;
    private String depTime;
    private String arrTime;
    private int businessSeats;
    private int economySeats;
    private int firstclassSeats;

    @OneToMany(mappedBy = "flights")
    private List<Bookings> bookings;

    @OneToMany(mappedBy = "flight")
    private List<Passengers> passengers;

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public int getBusinessSeats() {
        return businessSeats;
    }

    public void setBusinessSeats(int businessSeats) {
        this.businessSeats = businessSeats;
    }

    public int getEconomySeats() {
        return economySeats;
    }

    public void setEconomySeats(int economySeats) {
        this.economySeats = economySeats;
    }

    public int getFirstclassSeats() {
        return firstclassSeats;
    }

    public void setFirstclassSeats(int firstclassSeats) {
        this.firstclassSeats = firstclassSeats;
    }

}
