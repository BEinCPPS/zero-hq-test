package it.eng.zerohqt.orion.model.subscribe;


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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubscribeContextAvailabilityRequest {
    List<OrionEntity> entities;
    String reference;
    String duration;
    List<NotifyConditions> notifyConditions;
    String throttling;
    List<String> attributes;

    public SubscribeContextAvailabilityRequest() {
        this.entities = new ArrayList<OrionEntity>();
        this.attributes = new ArrayList<String>();
        this.duration = "P1D";
        this.notifyConditions = new ArrayList<NotifyConditions>();
        this.throttling="PT1S";
    }

    public List<OrionEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<OrionEntity> entities) {
        this.entities = entities;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<NotifyConditions> getNotifyConditions() {
        return notifyConditions;
    }

    public void setNotifyConditions(List<NotifyConditions> notifyConditions) {
        this.notifyConditions = notifyConditions;
    }

    public String getThrottling() {
        return throttling;
    }

    public void setThrottling(String throttling) {
        this.throttling = throttling;
    }

    @Override
    public String toString() {
        return "SubscribeContextAvailabilityRequest{" +
                "entities=" + entities +
                ", attributes=" + attributes +
                ", reference='" + reference + '\'' +
                ", duration='" + duration + '\'' +
                ", notifyConditions=" + notifyConditions +
                ", throttling='" + throttling + '\'' +
                '}';
    }
}
