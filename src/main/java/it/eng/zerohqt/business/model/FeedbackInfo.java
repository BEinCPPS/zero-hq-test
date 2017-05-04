package it.eng.zerohqt.business.model;

import java.util.Date;

/**
 * Created by ascatox on 03/05/17.
 */
public class FeedbackInfo extends ZeroHQTModel{
    private String measureId;
    private Double value;
    private String origin;

    public FeedbackInfo() {
        this.origin = this.getClass().getSimpleName();
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getOrigin() {
        return origin;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "FeedbackInfo{" +
                "measureId='" + measureId + '\'' +
                ", value=" + value +
                ", origin='" + origin + '\'' +
                '}';
    }
}
