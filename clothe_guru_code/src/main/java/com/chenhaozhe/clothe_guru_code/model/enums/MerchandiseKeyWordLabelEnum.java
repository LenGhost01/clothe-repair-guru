package com.chenhaozhe.clothe_guru_code.model.enums;

public enum MerchandiseKeyWordLabelEnum {

    MERCHANDISE_NAME("merchandise_name"),
    MERCHANT_NAME("merchant_name")
    ;


    private final String label;

    MerchandiseKeyWordLabelEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
