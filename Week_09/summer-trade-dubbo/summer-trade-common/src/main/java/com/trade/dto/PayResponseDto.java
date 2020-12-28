package com.trade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xwt
 */
@Data
public class PayResponseDto implements Serializable {
    private static final long serialVersionUID = 2887058598295895600L;

    /**
     * 返回码
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMsg;
}
