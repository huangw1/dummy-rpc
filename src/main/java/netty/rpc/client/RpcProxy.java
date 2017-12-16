package netty.rpc.client;

import netty.rpc.common.bean.RpcRequest;
import netty.rpc.common.bean.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by huangw1 on 2017/12/16.
 */
public class RpcProxy {

    @SuppressWarnings("unchecked")
    public static <T> T subscribe(Class<?> interfaceClass, final String host, final int port) {
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setRequestId(UUID.randomUUID().toString());
                request.setMethodName(method.getName());
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);

                RpcClient client = new RpcClient(host, port);
                long time = System.currentTimeMillis();
                RpcResponse response = client.send(request);
                long cost = System.currentTimeMillis() - time;
                System.out.println(method.getName() + " spends time: " + cost + "ms;");

                if(response == null) {
                    throw new RuntimeException("response is null");
                }
                if(response.hasException()) {
                    return response.getException();
                }
                return response.getResult();
            }
        });
    }
}
