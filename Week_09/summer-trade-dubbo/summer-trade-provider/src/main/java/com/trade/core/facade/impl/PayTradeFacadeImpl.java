package com.trade.core.facade.impl;

import com.trade.dto.PayRequestDto;
import com.trade.dto.PayResponseDto;
import com.trade.facade.PayTradeFacade;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author xwt
 */
@DubboService
public class PayTradeFacadeImpl implements PayTradeFacade {
    @Override
    public PayResponseDto payRequest(PayRequestDto payRequestDto) {
        PayResponseDto payResponseDto = new PayResponseDto();
        payResponseDto.setReturnCode("200");
        payResponseDto.setReturnMsg("Success");
        return payResponseDto;
    }
}
