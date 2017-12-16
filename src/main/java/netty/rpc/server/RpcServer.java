package netty.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.rpc.common.bean.RpcRequest;
import netty.rpc.common.bean.RpcResponse;
import netty.rpc.common.codec.RpcDecoder;
import netty.rpc.common.codec.RpcEncoder;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Created by huangw1 on 2017/12/15.
 */
public class RpcServer {

    public static void publish(final Map<Integer, Object> map) throws Exception {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 编码 解码 RPC 服务端与客户端相反
                            pipeline.addLast(new RpcDecoder(RpcRequest.class));
                            pipeline.addLast(new RpcEncoder(RpcResponse.class));
                            pipeline.addLast(new RpcServerHandler(map));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            for(int port : map.keySet()) {
                System.out.println(port);
                ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(port)).sync();
                channelFuture.channel().closeFuture().sync();
            }
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
