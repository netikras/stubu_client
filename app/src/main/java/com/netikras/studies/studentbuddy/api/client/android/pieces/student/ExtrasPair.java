package com.netikras.studies.studentbuddy.api.client.android.pieces.student;

/**
 * Created by netikras on 17.10.22.
 */

public class ExtrasPair {
    private String key;
    private String value;

    public ExtrasPair() {
    }

    public ExtrasPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
