package netty.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc.common.bean.RpcRequest;
import netty.rpc.common.bean.RpcResponse;
import netty.rpc.common.codec.RpcDecoder;
import netty.rpc.common.codec.RpcEncoder;

/**
 * Created by huangw1 on 2017/12/16.
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    private String host;
    private int port;

    private RpcResponse response;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        this.response = rpcResponse;
    }

    public RpcResponse send(RpcRequest request) throws Exception {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loop)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            // 编码 解码 RPC 服务端与客户端相反
                            pipeline.addLast(new RpcDecoder(RpcResponse.class));
                            pipeline.addLast(new RpcEncoder(RpcRequest.class));
                            pipeline.addLast(RpcClient.this);
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().writeAndFlush(request).sync();
            future.channel().closeFuture().sync();
            return response;
        } finally {
            loop.shutdownGracefully();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}
