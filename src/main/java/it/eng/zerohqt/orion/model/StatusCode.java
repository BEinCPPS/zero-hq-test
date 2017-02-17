package it.eng.zerohqt.orion.model;

public class StatusCode {
	
    private String code;
    private String reasonPhrase;
    
    
	public StatusCode () {
		
	}

    public StatusCode(String code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }


    
}
