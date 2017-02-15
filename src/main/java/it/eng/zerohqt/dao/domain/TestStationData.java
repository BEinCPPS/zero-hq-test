package it.eng.zerohqt.dao.domain;

import it.eng.zerohqt.config.Constants;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by ascatox on 13/02/17.
 */
public class TestStationData implements Serializable, Comparable {

    private String recvTimeTs;
    private String recvTime;
    private String fiwareServicePath;
    private String entityId;
    private String entityType;
    private String attrName;
    private String attrType;
    private String attrValue;
    private String attrMd;

    public TestStationData(String recvTimeTs, String recvTime, String fiwareServicePath, String entityId, String entityType, String attrName, String attrType, String attrValue, String attrMd) {
        this.recvTimeTs = recvTimeTs;
        this.recvTime = recvTime;
        this.fiwareServicePath = fiwareServicePath;
        this.entityId = entityId;
        this.entityType = entityType;
        this.attrName = attrName;
        this.attrType = attrType;
        this.attrValue = attrValue;
        this.attrMd = attrMd;
    }

    public TestStationData() {
    }

    public String getRecvTimeTs() {
        return recvTimeTs;
    }

    public void setRecvTimeTs(String recvTimeTs) {
        this.recvTimeTs = recvTimeTs;
    }

    public String getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(String recvTime) {
        this.recvTime = recvTime;
    }

    public String getFiwareServicePath() {
        return fiwareServicePath;
    }

    public void setFiwareServicePath(String fiwareServicePath) {
        this.fiwareServicePath = fiwareServicePath;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getAttrMd() {
        return attrMd;
    }

    public void setAttrMd(String attrMd) {
        this.attrMd = attrMd;
    }


    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestStationData that = (TestStationData) o;

        if (!recvTimeTs.equals(that.recvTimeTs)) return false;
        return recvTime.equals(that.recvTime);
    }

    @Override
    public int hashCode() {
        int result = recvTimeTs.hashCode();
        result = 31 * result + recvTime.hashCode();
        return result;
    }

    //SORTED DESC
    @Override
    public int compareTo(Object o) {
        Date dateIn, dateOut;
        TestStationData obj = (TestStationData) o;
        try {
            dateIn = DateUtils.parseDate(this.recvTime, Constants.DATETIME_MILLISECOND);
            dateOut = DateUtils.parseDate(obj.recvTime, Constants.DATETIME_MILLISECOND);
            if (dateIn.after(dateOut))
                return -1;
            else if (dateIn.before(dateOut))
                return 1;
            else
                return 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
