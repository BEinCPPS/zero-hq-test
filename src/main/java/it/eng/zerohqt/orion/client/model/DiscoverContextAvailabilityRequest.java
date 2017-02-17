package it.eng.zerohqt.orion.client.model;

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
public class DiscoverContextAvailabilityRequest {
    List<OrionQueryElement> entities;
    List<String> attributes;

    public DiscoverContextAvailabilityRequest() {
        entities = new ArrayList<OrionQueryElement>();
    }

    public List<OrionQueryElement> getEntities() {
        return entities;
    }

    public void setEntities(List<OrionQueryElement> entities) {
        this.entities = entities;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
}
