package it.eng.zerohqt.dao.model;

/**
 * Created by ascatox on 08/03/17.
 */
public enum AcknowledgeTypes {
    ack1("Required intervention with Elimination Rule", 1),
    ack2("Cancellation intervention with Elimination Rule", 2),
    ack3("Prompted intervention by notice cancellation FE", 3);
    //TODO Translation
    private String description;
    private Integer id;

    AcknowledgeTypes(String description, Integer id) {
        this.description = description;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
