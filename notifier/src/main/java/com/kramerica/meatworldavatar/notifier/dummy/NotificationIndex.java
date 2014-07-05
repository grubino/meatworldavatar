package com.kramerica.meatworldavatar.notifier.dummy;

/**
 * Created by greg on 6/21/14.
 */
public enum NotificationIndex {
    STARTUP_NOTIFICATION("meat world data sink started"),
    POSITIVE_SENSOR_CHANGE("suddenly I can see much better"),
    NEGATIVE_SENSOR_CHANGE("I can't see very well right now"),
    INTERRUPT_NOTIFICATION("I've been interrupted!");

    NotificationIndex(final String msg) {
        this.msg = msg;
    }

    private String msg;

    public String message() { return msg; }
}
