package it.eng.zerohqt.business.model;

import java.util.Date;

/**
 * Created by ascatox on 03/05/17.
 */
public class FeedbackScale {
    private Date timestamp;
    private String measureId;
    private Double valueMin;
    private Double valueMax;

    public FeedbackScale() {
        this.timestamp = new Date();
    }

    public FeedbackScale( String measureId, Double valueMin, Double valueMax) {
        this.timestamp = new Date();
        this.measureId = measureId;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public Double getValueMin() {
        return valueMin;
    }

    public void setValueMin(Double valueMin) {
        this.valueMin = valueMin;
    }

    public Double getValueMax() {
        return valueMax;
    }

    public void setValueMax(Double valueMax) {
        this.valueMax = valueMax;
    }
}


