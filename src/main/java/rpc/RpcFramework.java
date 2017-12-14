package rpc;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class RpcFramework {

    public static void publish(final Map<Integer, Object> map) throws Exception {
        Set<Map.Entry<Integer, Object>> set = map.entrySet();
        for(Map.Entry<Integer, Object> entry: set) {
            int port = entry.getKey();
            Object service = entry.getValue();

            ServerSocket server = new ServerSocket(port);
            ThreadPoolHelper.getExecutorInstance().execute(new WorkThread(server, service));
        }
    }

    /**
     * NIO
     * @param map
     * @throws Exception
     */
    public static void NioPublish(final Map<Integer, Object> map) throws Exception {
        Selector selector = SelectorProvider.provider().openSelector();
        for(Integer port: map.keySet()) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        }

    }

    public static <T> T subscribe(Class<T> interfaceClass, String host, int port) throws Exception {
        if(interfaceClass == null) {
            throw new IllegalArgumentException("interfaceClass can not be null");
        }
        if(!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("interfaceClass should be interface");
        }
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationProxy(host, port));
    }
}
