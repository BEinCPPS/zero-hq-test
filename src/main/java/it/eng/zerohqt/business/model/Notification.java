package it.eng.zerohqt.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 17/02/17.
 */
public class Notification implements Serializable {

    private String stateCode;
    private String statePayload;
    private String stateDescription;
    private Date timestamp;

    public Notification() {
    }

    public Notification(String stateCode, String statePayload, String stateDescription, Date timestamp) {
        this.stateCode = stateCode;
        this.statePayload = statePayload;
        this.stateDescription = stateDescription;
        this.timestamp = timestamp;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStatePayload() {
        return statePayload;
    }

    public void setStatePayload(String statePayload) {
        this.statePayload = statePayload;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
