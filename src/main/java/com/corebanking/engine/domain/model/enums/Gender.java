package com.corebanking.engine.domain.model.enums;

public enum Gender {

    MALE("M"),
    FEMALE("F"),
    OTHER("O"),
    UNSPECIFIED("U");

    private final String regulatoryCode;

    Gender(String regulatoryCode) {
        this.regulatoryCode = regulatoryCode;
    }

    public String regulatoryCode() {
        return regulatoryCode;
    }

    public static Gender fromCode(String code) {
        for (Gender g : values()) {
            if (g.regulatoryCode.equalsIgnoreCase(code)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Unknown gender code: " + code);
    }
}
