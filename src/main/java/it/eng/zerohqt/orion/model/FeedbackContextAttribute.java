package it.eng.zerohqt.orion.model;

import it.eng.zerohqt.config.Constants;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 03/05/17.
 */
public enum FeedbackContextAttribute {
    measure1, measure2, measure3, measure4, measure5, measure6, measure7, measure8, measure9, measure10, measure11,
    measure12, measure13, measure14;

    //Warning not used at the moment only for precaution
    public static String getContextFatherNamePrefix() {
        return Constants.ORION_CONTEXT_PREFIX_FEEDBACK;
    }

    public static String[] getValuesString() {
        String[] values = new String[FeedbackContextAttribute.values().length];
        return Arrays.stream(FeedbackContextAttribute.values())
                .map(f -> f.name())
                .collect(Collectors.toList())
                .toArray(values);
    }
}
