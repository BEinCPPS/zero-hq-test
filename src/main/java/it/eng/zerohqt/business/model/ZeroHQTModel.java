package it.eng.zerohqt.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 03/05/17.
 */
public class ZeroHQTModel implements Serializable{

    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ZeroHQTModel() {
    }
}
