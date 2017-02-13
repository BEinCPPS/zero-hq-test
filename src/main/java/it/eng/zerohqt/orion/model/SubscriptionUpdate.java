package it.eng.zerohqt.orion.model;

/*-
 * #%L
 * Orion Context Broker Client for Java
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2015 - 2016 amaxilatis
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.io.Serializable;
import java.util.List;

/**
 * Maps the Object posted by OrionContextBroker in subscriptions.
 *
 * @author Dimitrios Amaxilatis.
 */
public class SubscriptionUpdate implements Serializable {
    String subscriptionId;
    String originator;
    List<OrionContextElementWrapper> contextResponses;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public List<OrionContextElementWrapper> getContextResponses() {
        return contextResponses;
    }

    public void setContextResponses(List<OrionContextElementWrapper> contextResponses) {
        this.contextResponses = contextResponses;
    }

    @Override
    public String toString() {
        return "SubscriptionUpdate{" +
                "subscriptionId='" + subscriptionId + '\'' +
                ", originator='" + originator + '\'' +
                '}';
    }
}
