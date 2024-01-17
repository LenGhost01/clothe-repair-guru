package com.chenhaozhe.clothe_guru_code.model.enums;

public enum ApplicationKeyWordEnum {
    MERCHANT_NAME("merchant_name"),
    USERNAME("username"),
    NICKNAME("nickname")
    ;

    private final String label;

    ApplicationKeyWordEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
