package org.homeassignment.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ImpactTypes {

    MULTIPLY_REWARD("multiply_reward"),

    EXTRA_BONUS("extra_bonus"),

    MISS("miss");

    private String value;

    ImpactTypes(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ImpactTypes fromString(String s) {
        for (ImpactTypes b : ImpactTypes.values()) {
            if (java.util.Objects.toString(b.value).equals(s)) {
                return b;
            }
        }
        return null;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static ImpactTypes fromValue(String value) {
        for (ImpactTypes b : ImpactTypes.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        return null;
    }
}


