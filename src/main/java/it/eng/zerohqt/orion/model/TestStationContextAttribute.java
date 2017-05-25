package it.eng.zerohqt.orion.model;

import it.eng.zerohqt.config.Constants;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 15/02/17.
 */
public enum TestStationContextAttribute {
    state, acknowledge, statePayload, ipAddress, stationInfo;

    public static String getContextFatherNamePrefix() {
        return Constants.ORION_CONTEXT_PREFIX_TESTSTATION;
    }

    public static String[] getValuesString() {
        String[] values = new String[TestStationContextAttribute.values().length];
        return Arrays.stream(TestStationContextAttribute.values())
                .map(ts -> ts.name())
                .collect(Collectors.toList())
                .toArray(values);
    }
}
