package server;

import rpc.RpcFramework;
import server.service.CalculateService;
import server.service.GreetingService;
import server.service.impl.CalculateServiceImpl;
import server.service.impl.GreetingServiceImpl;

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

        RpcFramework.publish(map);
    }
}
