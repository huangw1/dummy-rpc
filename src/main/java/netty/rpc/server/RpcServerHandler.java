package netty.rpc.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.rpc.common.bean.RpcRequest;
import netty.rpc.common.bean.RpcResponse;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Created by huangw1 on 2017/12/15.
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Map<Integer, Object> map;

    public RpcServerHandler(Map<Integer, Object> map) {
        this.map = map;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());

        Object result = handle(channelHandlerContext, rpcRequest);
        rpcResponse.setResult(result);
        // 写入对象后关闭连接
        channelHandlerContext.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameter = rpcRequest.getParameters();

        InetSocketAddress address = (InetSocketAddress)channelHandlerContext.channel().localAddress();
        Object service = map.get(Integer.parseInt(String.valueOf(address.getPort())));

        Method method = service.getClass().getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(service, parameter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}
