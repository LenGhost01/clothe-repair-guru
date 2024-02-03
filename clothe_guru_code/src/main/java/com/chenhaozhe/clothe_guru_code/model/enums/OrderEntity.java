package com.chenhaozhe.clothe_guru_code.model.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    private Integer orderId;
    private Long userId;
    private Long merchantId;
    private Integer merchandiseId;
    private Integer orderState;
    private String paymentMethod;
    private String placeTime;
    private String dispatchLocation;
    private String sourceLocation;
    private String orderDisplay;
    private String orderRemark;
    private Short cancellation;
    private String cancelReason;

}
