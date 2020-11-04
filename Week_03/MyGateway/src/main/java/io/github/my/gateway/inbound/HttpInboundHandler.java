package io.github.my.gateway.inbound;

import io.github.my.gateway.filter.HttpRequestFilter;
import io.github.my.gateway.outbound.httpClient.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private HttpOutboundHandler handler;
    private HttpRequestFilterHandler filter;

    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        
        handler =new HttpOutboundHandler(this.proxyServer);
        httpRequestFilter = new HttpRequestFilterHandler();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            //添加filter，request头里面添加key,value(nio:xwt)
            if (filter != null){
                httpRequestFilter.filter(fullRequest,ctx);
            }

            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
