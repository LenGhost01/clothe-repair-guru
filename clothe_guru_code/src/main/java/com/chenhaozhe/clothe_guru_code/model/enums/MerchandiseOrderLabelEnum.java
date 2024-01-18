package com.chenhaozhe.clothe_guru_code.model.enums;

public enum MerchandiseOrderLabelEnum {
    SALES("sales"),
    PUBLISH_TIME("publish_time"),
    LOW_PRICE("low_price"),
    HIGH_PRICE("high_price"),
    SATISFACTION_RATE("satisfaction_rate"),
    ADDRESS("address");
    private final String label;

    MerchandiseOrderLabelEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
