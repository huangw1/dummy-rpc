package netty.rpc.test;

import netty.rpc.server.RpcServer;
import netty.rpc.test.service.CalculateService;
import netty.rpc.test.service.GreetingService;
import netty.rpc.test.service.impl.CalculateServiceImpl;
import netty.rpc.test.service.impl.GreetingServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class Provider {
    
    public static void main(String[] args) throws Exception {

        GreetingService greetingService = new GreetingServiceImpl();
        CalculateService calculateService = new CalculateServiceImpl();

        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(3456, greetingService);
        map.put(5634, calculateService);

        RpcServer.publish(map);
    }
}
