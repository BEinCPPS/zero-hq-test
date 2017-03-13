package it.eng.zerohqt.config;

import org.apache.commons.lang3.StringUtils;

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
}
