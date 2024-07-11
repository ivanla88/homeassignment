package org.homeassignment.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ConditionType {

    LINEAR_SYMBOLS("linear_symbols"),

    SAME_SYMBOLS("same_symbols");

    private String value;

    ConditionType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ConditionType fromString(String s) {
        for (ConditionType b : ConditionType.values()) {
            if (java.util.Objects.toString(b.value).equals(s)) {
                return b;
            }
        }
        return null;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static ConditionType fromValue(String value) {
        for (ConditionType b : ConditionType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        return null;
    }
}
