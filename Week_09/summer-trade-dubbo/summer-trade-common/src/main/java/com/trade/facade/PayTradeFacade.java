package com.trade.facade;

import com.trade.dto.PayRequestDto;
import com.trade.dto.PayResponseDto;

/**
 * @author xwt
 */
public interface PayTradeFacade {
    /**
     * 支付请求
     * @param payRequestDto
     * @return
     */
    PayResponseDto payRequest(PayRequestDto payRequestDto);
}
