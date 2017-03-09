package it.eng.zerohqt.business.model;

import it.eng.zerohqt.dao.model.AcknowledgeTypes;

import java.io.Serializable;

/**
 * Created by ascatox on 09/03/17.
 */
public class Acknowledge extends BaseBayInfo implements Serializable {

    private AcknowledgeTypes ackType;
    private String bayCode;
    private String stationName;
    private Integer bayNumber;

    public Acknowledge() {
    }

    public Acknowledge(AcknowledgeTypes ackType, String bayCode, String stationName, Integer bayNumber) {
        this.ackType = ackType;
        this.bayCode = bayCode;
        this.stationName = stationName;
        this.bayNumber = bayNumber;
    }

    public AcknowledgeTypes getAckType() {
        return ackType;
    }

    public void setAckType(AcknowledgeTypes ackType) {
        this.ackType = ackType;
    }

    public String getBayCode() {
        return bayCode;
    }

    public void setBayCode(String bayCode) {
        this.bayCode = bayCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getBayNumber() {
        return bayNumber;
    }

    public void setBayNumber(Integer bayNumber) {
        this.bayNumber = bayNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "Acknowledge{" +
                "ackType=" + ackType +
                ", bayCode='" + bayCode + '\'' +
                ", stationName='" + stationName + '\'' +
                ", bayNumber=" + bayNumber +
                '}';
    }
}
