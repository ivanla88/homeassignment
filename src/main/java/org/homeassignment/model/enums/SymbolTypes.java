package org.homeassignment.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SymbolTypes {

    BONUS("bonus"),

    STANDARD("standard");

    private String value;

    SymbolTypes(String value) {
        this.value = value;
    }

    @JsonCreator
    public static SymbolTypes fromString(String s) {
        for (SymbolTypes b : SymbolTypes.values()) {
            if (java.util.Objects.toString(b.value).equals(s)) {
                return b;
            }
        }
        return null;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static SymbolTypes fromValue(String value) {
        for (SymbolTypes b : SymbolTypes.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        return null;
    }
}


