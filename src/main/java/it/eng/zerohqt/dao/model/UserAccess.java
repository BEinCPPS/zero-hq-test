package it.eng.zerohqt.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ascatox on 09/05/17.
 */
public class UserAccess implements Serializable{

    private String uid;
    private String fullName;
    private String email;
    private Date timestamp;

    public UserAccess() {
        this.timestamp = new Date();
    }

    public UserAccess(String uid, String fullName, String email) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.timestamp = new Date();
    }

    public String getUid() {
        return uid;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
