package com.trade.consumer.controller;

import com.trade.dto.PayRequestDto;
import com.trade.dto.PayResponseDto;
import com.trade.facade.PayTradeFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xwt
 */
@Slf4j
@RestController
@RequestMapping("pay")
public class ConsumerController {
    @DubboReference
    private PayTradeFacade payTradeFacade;

    @RequestMapping("pay")
    public void payRequest(PayRequestDto payRequestDto){
        PayResponseDto payResponseDto = payTradeFacade.payRequest(payRequestDto);
        log.info("打印dubbo响应编码："+payResponseDto.getReturnCode());
    }
}
