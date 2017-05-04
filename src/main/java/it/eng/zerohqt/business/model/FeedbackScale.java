package it.eng.zerohqt.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 03/05/17.
 */
public class FeedbackScale extends ZeroHQTModel implements Serializable {
    private String measureId;
    private Double valueMin;
    private Double valueMax;

    public FeedbackScale() {
        super.timestamp = new Date();
        this.origin = FeedbackScale.class.getSimpleName().toLowerCase();
    }

    public FeedbackScale(String measureId, Double valueMin, Double valueMax) {
        super.timestamp = new Date();
        super.origin = FeedbackScale.class.getSimpleName().toLowerCase();
        this.measureId = measureId;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
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


