package com.chenhaozhe.clothe_guru_code.model.enums;

public enum MerchantKeyWordEnum {
    MERCHANT_NAME("merchant_name"),
    ADDRESS("address"),

    ;

    private final String label;

    MerchantKeyWordEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
