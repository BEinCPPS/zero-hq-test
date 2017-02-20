package it.eng.zerohqt.orion.model;

import java.util.ArrayList;

public class Attributes {

    private String value;
    private ArrayList<Metadatas> metadatas;
    private String name;
    private String type;


    public Attributes() {

    }

    public Attributes(String value, ArrayList<Metadatas> metadatas, String name, String type) {
        this.value = value;
        this.metadatas = metadatas;
        this.name = name;
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<Metadatas> getMetadatas() {
        return this.metadatas;
    }

    public void setMetadatas(ArrayList<Metadatas> metadatas) {
        this.metadatas = metadatas;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
