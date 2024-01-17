package com.chenhaozhe.clothe_guru_code.model.enums;

public enum MerchantOrderValueEnum {
    MERCHANT_NAME("merchant_name"),
    ADDRESS("address"),
    JOIN_TIME("join_time")

    ;

    private final String label;

    MerchantOrderValueEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
