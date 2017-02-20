package it.eng.zerohqt.orion.client.model.subscribe;


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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionResponse {

    SubscribeResponse subscribeResponse;
    String subscribeError;


    public SubscribeResponse getSubscribeResponse() {
        return subscribeResponse;
    }

    public void setSubscribeResponse(SubscribeResponse subscribeResponse) {
        this.subscribeResponse = subscribeResponse;
    }

    public String getSubscribeError() {
        return subscribeError;
    }

    public void setSubscribeError(String subscribeError) {
        this.subscribeError = subscribeError;
    }

    @Override
    public String toString() {
        return "SubscriptionResponse{" +
                "subscribeResponse=" + subscribeResponse +
                '}';
    }
}
