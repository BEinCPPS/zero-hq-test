package it.eng.zerohqt.dao.domain;

import java.io.Serializable;

/**
 * Created by ascatox on 13/02/17.
 */
public class TestStation implements Serializable {
    private String recvTimeTs;
    private String recvTime;
    private String fiwareServicePath;
    private String entityId;
    private String entityType;
    private String attryType;
    private String attrValue;
    private String attrMd;

    public TestStation(String recvTimeTs, String recvTime, String fiwareServicePath, String entityId, String entityType, String attryType, String attrValue, String attrMd) {
        this.recvTimeTs = recvTimeTs;
        this.recvTime = recvTime;
        this.fiwareServicePath = fiwareServicePath;
        this.entityId = entityId;
        this.entityType = entityType;
        this.attryType = attryType;
        this.attrValue = attrValue;
        this.attrMd = attrMd;
    }

    public TestStation() {
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

    public String getAttryType() {
        return attryType;
    }

    public void setAttryType(String attryType) {
        this.attryType = attryType;
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
}
