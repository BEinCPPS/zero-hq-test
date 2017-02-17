package it.eng.zerohqt.dao.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alberti on 17/02/2017.
 */
public class TestStationState implements Serializable {


    private String code;
    private String description;
    private String payload;
    private Date dateTimeArrival;

    public TestStationState(String code, String description, String payload, Date dateTimeArrival) {
        this.code = code;
        this.description = description;
        this.payload = payload;
        this.dateTimeArrival = dateTimeArrival;
    }

    public TestStationState() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getDateTimeArrival() {
        return dateTimeArrival;
    }

    public void setDateTimeArrival(Date dateTimeArrival) {
        this.dateTimeArrival = dateTimeArrival;
    }
}
