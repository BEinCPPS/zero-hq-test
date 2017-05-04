package it.eng.zerohqt.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DATETIME_MILLISECOND = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ORION_CONTEXT_PREFIX_TESTSTATION = "teststation";
    public static final String ORION_CONTEXT_PREFIX_FEEDBACK = "feedbackContext";
    public static final String ORION_CONTEXT_PREFIX_FEEDBACK_SCALE = "feedbackScale";



    private Constants() {
    }
}
