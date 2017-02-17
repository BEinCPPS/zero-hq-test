package it.eng.zerohqt.dao.domain;

import it.eng.zerohqt.config.Constants;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 16/02/17.
 */
public class OrionSubscription implements Serializable {

    private String subscriptionId;
    private String creationDate;
    private int enabled;
    private String entity;
    private String type;


    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public OrionSubscription(String subscriptionId, Date creationDate, boolean enabled) {
        this.subscriptionId = subscriptionId;
        setCreationDate(creationDate);
        setBoolEnabled(enabled);
    }

    public OrionSubscription(String subscriptionId, Date creationDate, boolean enabled, String entity, String type) {
        this.subscriptionId = subscriptionId;
        setCreationDate(creationDate);
        setBoolEnabled(enabled);
        this.entity = entity;
        this.type = type;
    }

    public OrionSubscription() {
        setBoolEnabled(false);
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate == null)
            this.creationDate = null;
        this.creationDate = DateFormatUtils.format(creationDate, Constants.DATETIME_MILLISECOND);
    }

    public boolean getBooleanEnabled() {
        return enabled == 1;
    }

    public void setBoolEnabled(boolean enabled) {
        this.enabled = enabled ? 1 : 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
