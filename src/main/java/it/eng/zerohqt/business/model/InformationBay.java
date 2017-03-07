package it.eng.zerohqt.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 17/02/17.
 */
public class InformationBay implements Serializable{

    private String stationName;
    private String stationDescription;
    private String bayCode;
    private Integer bayNumber;
    private String ipAddress;
    private Notification notification;
    private Date timestamp;

    public InformationBay() {
    }

    public InformationBay(String stationName, String stationDescription, String bayCode, Integer bayNumber,  String ipAddress, Notification notification, Date timestamp) {

        this.stationName = stationName;
        this.stationDescription = stationDescription;
        this.bayCode = bayCode;
        this.ipAddress = ipAddress;
        this.notification = notification;
        this.timestamp = timestamp;
        this.bayNumber = bayNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationDescription() {
        return stationDescription;
    }

    public void setStationDescription(String stationDescription) {
        this.stationDescription = stationDescription;
    }

    public String getBayCode() {
        return bayCode;
    }

    public void setBayCode(String bayCode) {
        this.bayCode = bayCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getBayNumber() {
        return bayNumber;
    }

    public void setBayNumber(Integer bayNumber) {
        this.bayNumber = bayNumber;
    }

    @Override
    public String toString() {
        return "InformationBay{" +
                "stationName='" + stationName + '\'' +
                ", stationDescription='" + stationDescription + '\'' +
                ", bayCode='" + bayCode + '\'' +
                ", bayNumber=" + bayNumber +
                ", ipAddress='" + ipAddress + '\'' +
                ", notification=" + notification +
                ", timestamp=" + timestamp +
                '}';
    }
}
