package it.eng.zerohqt.business.model;

import java.io.Serializable;

/**
 * Created by ascatox on 09/03/17.
 */
public class BaseBayInfo extends ZeroHQTModel implements Serializable {

    protected String stationName;
    protected Integer bayNumber;
    protected String bayCode;

    public BaseBayInfo() {
    }

    public BaseBayInfo(String stationName, Integer bayNumber, String bayCode) {
        this.stationName = stationName;
        this.bayNumber = bayNumber;
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

    public String getBayCode() {
        return bayCode;
    }

    public void setBayCode(String bayCode) {
        this.bayCode = bayCode;
    }

    @Override
    public String toString() {
        return "BaseBayInfo{" +
                "stationName='" + stationName + '\'' +
                ", bayNumber=" + bayNumber +
                ", bayCode='" + bayCode + '\'' +
                '}';
    }
}
