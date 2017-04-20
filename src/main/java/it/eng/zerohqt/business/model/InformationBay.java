package it.eng.zerohqt.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 17/02/17.
 */
public class InformationBay extends BaseBayInfo implements Serializable {

    private String stationDescription;
    private String ipAddress;
    private StateInfo stateInfo;
    private Date timestamp;
    private Acknowledge acknowledge;

    public Acknowledge getAcknowledge() {
        return acknowledge;
    }
    public void setAcknowledge(Acknowledge acknowledge) {
        this.acknowledge = acknowledge;
    }

    public InformationBay() {

    }

    public InformationBay(String stationName, Integer bayNumber, String bayCode, String stationDescription, String ipAddress, StateInfo notification, Date timestamp) {
        super(stationName, bayNumber, bayCode);
        this.stationDescription = stationDescription;
        this.ipAddress = ipAddress;
        this.stateInfo = notification;
        this.timestamp = timestamp;
    }

    public String getStationDescription() {
        return stationDescription;
    }

    public void setStationDescription(String stationDescription) {
        this.stationDescription = stationDescription;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public StateInfo getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(StateInfo stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "InformationBay{" +
                "stationDescription='" + stationDescription + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", stateInfo=" + stateInfo +
                ", timestamp=" + timestamp +
                ", acknowledge=" + acknowledge +
                '}';
    }
}
