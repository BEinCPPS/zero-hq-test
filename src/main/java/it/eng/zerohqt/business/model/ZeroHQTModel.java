package it.eng.zerohqt.business.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.eng.zerohqt.config.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 03/05/17.
 */
public class ZeroHQTModel implements Serializable {

   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN_CONTEXT)
    protected Date timestamp;
    protected String origin; //simple class name

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public ZeroHQTModel() {
    }
}
