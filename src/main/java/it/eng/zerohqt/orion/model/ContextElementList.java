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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

/**
 * Maps a List of Orion Context Elements.
 *
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ContextElementList implements Serializable {
    List<OrionContextElementWrapper> contextResponses;
    StatusCode statusCode;
    StatusCode errorCode;

    public List<OrionContextElementWrapper> getContextResponses() {
        return contextResponses;
    }

    public void setContextResponses(List<OrionContextElementWrapper> contextResponses) {
        this.contextResponses = contextResponses;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(StatusCode errorCode) {
        this.errorCode = errorCode;
    }

    public boolean hasMore(final long offset) {
        try {
            System.out.println("Offset:" + offset + " count:" + getErrorCode().getCount());
            return getErrorCode().getCount() > offset;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ContextElementList{" +
                "statusCode=" + statusCode +
                '}';
    }
}
