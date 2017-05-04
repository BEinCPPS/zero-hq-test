package it.eng.zerohqt.orion.model;

import java.util.ArrayList;

public class ZeroHQTContext {
    private String subscriptionId;
    private String originator;
    private ArrayList<ContextResponses> contextResponses;

	public ZeroHQTContext() {
		
	}
    public ZeroHQTContext(String subscriptionId, String originator, ArrayList<ContextResponses> contextResponses) {
        this.subscriptionId = subscriptionId;
        this.originator = originator;
        this.contextResponses = contextResponses;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getOriginator() {
        return this.originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public ArrayList<ContextResponses> getContextResponses() {
        return this.contextResponses;
    }

    public void setContextResponses(ArrayList<ContextResponses> contextResponses) {
        this.contextResponses = contextResponses;
    }


    
}
