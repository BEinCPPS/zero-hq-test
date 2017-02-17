package it.eng.zerohqt.orion.model;

public class ContextResponses {
	
    private ContextElement contextElement;
    private StatusCode statusCode;
    
    
	public ContextResponses () {
		
	}

    public ContextResponses(ContextElement contextElement, StatusCode statusCode) {
        this.contextElement = contextElement;
        this.statusCode = statusCode;
    }

    public ContextElement getContextElement() {
        return this.contextElement;
    }

    public void setContextElement(ContextElement contextElement) {
        this.contextElement = contextElement;
    }

    public StatusCode getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }


    
}
