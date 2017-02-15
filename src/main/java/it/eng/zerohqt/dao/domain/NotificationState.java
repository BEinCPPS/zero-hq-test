package it.eng.zerohqt.dao.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ascatox on 14/02/17.
 */
public enum NotificationState {
    state400(400), state106(106), state700(700), state1000(1000);

    NotificationState(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public static List<Integer> getValueStates() {
        return Arrays.stream(NotificationState.values()).map(notificationState
                -> notificationState.getValue()).collect(Collectors.toList());
    }

    public static List<String> getValueStatesString() {
        return Arrays.stream(NotificationState.values()).map(notificationState
                -> notificationState.getValue() + "").collect(Collectors.toList());
    }
}
