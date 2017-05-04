package it.eng.zerohqt.business.model;

import it.eng.zerohqt.dao.model.StateType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 17/02/17.
 */
public class StateInfo extends ZeroHQTModel implements Serializable {

    private String stateCode;
    private String statePayload;
    private String stateDescription;
    private StateType type;


    public StateInfo() {
        this.type = StateType.normal;
    }

    public StateInfo(String stateCode, String statePayload, String stateDescription, Date timestamp, StateType type) {
        this.stateCode = stateCode;
        this.statePayload = statePayload;
        this.stateDescription = stateDescription;
        super.timestamp = timestamp;
        this.type = type;
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

    public StateType getType() {
        return type;
    }

    public void setType(StateType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StateInfo{" +
                "stateCode='" + stateCode + '\'' +
                ", statePayload='" + statePayload + '\'' +
                ", stateDescription='" + stateDescription + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }


}
