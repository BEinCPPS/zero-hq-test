package it.eng.zerohqt.orion.model;

import it.eng.zerohqt.config.Constants;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 03/05/17.
 */
public enum FeedbackAcknowledgeContextAttribute {
    acknowledge;

    //Warning not used at the moment only for precaution
    public static String getContextFatherNamePrefix() {
        return Constants.ORION_CONTEXT_PREFIX_FEEDBACK_ACKNOWLEDGE;
    }

    public static String[] getValuesString() {
        String[] values = new String[FeedbackAcknowledgeContextAttribute.values().length];
        return Arrays.stream(FeedbackAcknowledgeContextAttribute.values())
                .map(f -> f.name())
                .collect(Collectors.toList())
                .toArray(values);
    }
}
