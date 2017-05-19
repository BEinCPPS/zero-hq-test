package it.eng.zerohqt.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by ascatox on 13/03/17.
 */
public class Utils {


    public static boolean isStringBlankExt(String str) {
        return StringUtils.isBlank(str)
                || "null".equalsIgnoreCase(str);
    }

    public static boolean isStringNotBlankExt(String str) {
        return !isStringBlankExt(str);
    }


    public static Date convertContextMetadataTimestampToDate(String timestamp) {
        String timestampNoTimezone = timestamp.substring(0, timestamp.length() - 5);
        try {
            return DateUtils.parseDate(timestampNoTimezone, Constants.DATE_PATTERN_CONTEXT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
