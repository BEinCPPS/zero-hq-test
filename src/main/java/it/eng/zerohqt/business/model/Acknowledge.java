package it.eng.zerohqt.business.model;

import it.eng.zerohqt.dao.model.AcknowledgeTypes;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by ascatox on 09/03/17.
 */
public class Acknowledge extends BaseBayInfo implements Serializable {

    private AcknowledgeTypes ackType;
    private String description;
    public Acknowledge() {
    }

    public Acknowledge(AcknowledgeTypes ackType, String description) {
        this.ackType = ackType;
        this.description = description;
    }

    public AcknowledgeTypes getAckType() {
        return ackType;
    }

    public void setAckType(AcknowledgeTypes ackType) {
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
