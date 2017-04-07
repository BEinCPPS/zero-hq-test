package it.eng.zerohqt.business.model;

import it.eng.zerohqt.dao.model.AcknowledgeType;

import java.io.Serializable;

/**
 * Created by ascatox on 09/03/17.
 */
public class Acknowledge extends BaseBayInfo implements Serializable {

    private AcknowledgeType ackType;
    private String description;
    public Acknowledge() {
    }

    public Acknowledge(AcknowledgeType ackType, String description) {
        this.ackType = ackType;
        this.description = description;
    }

    public AcknowledgeType getAckType() {
        return ackType;
    }

    public void setAckType(AcknowledgeType ackType) {
        this.ackType = ackType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString()+"Acknowledge{" +
                "ackType=" + ackType +
                ", description='" + description + '\'' +
                '}';
    }
}
