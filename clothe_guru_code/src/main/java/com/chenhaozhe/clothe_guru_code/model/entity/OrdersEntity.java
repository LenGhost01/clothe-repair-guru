package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersEntity {
    private Integer orderId;
    private Long userId;
    private Long merchantId;
    private Integer orderState;
    private Integer paymentMethod;
    private String placeTime;
    private String dispatchLocation;
    private String orderDisplay;
    private String orderRemark;
    private Short cancellation;
    private String cancelReason;

}
