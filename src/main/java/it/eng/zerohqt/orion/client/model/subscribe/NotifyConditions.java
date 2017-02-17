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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaxilatis on 5/7/2015.
 */
public class NotifyConditions {
    String type;
    List<String> condValues;

    public NotifyConditions(String type, String condValues) {
        this.type = type;
        this.condValues = new ArrayList<String>();
        if (condValues != null) {
            this.condValues.add(condValues);
        }
    }

    public NotifyConditions() {
        this.type = "ONTIMEINTERVAL";
        this.condValues = new ArrayList<String>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCondValues() {
        return condValues;
    }

    public void setCondValues(List<String> condValues) {
        this.condValues = condValues;
    }
}
