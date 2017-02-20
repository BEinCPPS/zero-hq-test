package it.eng.zerohqt.orion.model;

import java.util.ArrayList;

public class ContextElement {
	
    private String id;
    private String type;
    private String isPattern;
    private ArrayList<Attributes> attributes;
    
    
	public ContextElement () {
		
	}

    public ContextElement(String id, String type, String isPattern, ArrayList<Attributes> attributes) {
        this.id = id;
        this.type = type;
        this.isPattern = isPattern;
        this.attributes = attributes;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsPattern() {
        return this.isPattern;
    }

    public void setIsPattern(String isPattern) {
        this.isPattern = isPattern;
    }

    public ArrayList<Attributes> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(ArrayList<Attributes> attributes) {
        this.attributes = attributes;
    }


    
}
