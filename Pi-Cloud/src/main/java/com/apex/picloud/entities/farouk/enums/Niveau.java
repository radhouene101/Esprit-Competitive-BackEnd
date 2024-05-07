package com.apex.picloud.entities.farouk.enums;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Niveau {
    _1A("1A"),
    _2A("2A"),
    _3A("3A"),
    _4A("4A"),
    _5A("5A");

    private final String niveauValue;

    Niveau(String niveauValue) {
        this.niveauValue = niveauValue;
    }

    @JsonValue
    public String getNiveauValue() {
        return niveauValue;
    }

    public static Niveau fromString(String value) {
        for (Niveau niveau : Niveau.values()) {
            if (niveau.niveauValue.equalsIgnoreCase(value)) {
                return niveau;
            }
        }
        throw new IllegalArgumentException("Invalid Niveau: " + value);
    }
}
