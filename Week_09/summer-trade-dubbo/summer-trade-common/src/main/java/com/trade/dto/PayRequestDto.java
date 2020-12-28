package com.trade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwt
 */
@Data
public class PayRequestDto implements Serializable {

    private static final long serialVersionUID = 2441587446882479767L;
    /**
     * 商户订单号
     */
    private Long merchantOrderNo;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 商户请求时间
     */
    private Date merchantReqTime;
    /**
     * 订单币种
     */
    private String orderCurrency;
}
