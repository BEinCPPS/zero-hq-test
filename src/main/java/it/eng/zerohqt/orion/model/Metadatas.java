package it.eng.zerohqt.orion.model;

public class Metadatas {
	
    private String name;
    private String type;
    private String value;
    
	public Metadatas () {
	}

    public Metadatas(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    
}
