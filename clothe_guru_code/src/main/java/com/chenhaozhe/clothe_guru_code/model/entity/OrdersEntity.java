package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4665481508095114236L;
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
