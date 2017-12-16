package normal.server;

import normal.rpc.RpcFramework;
import normal.server.service.CalculateService;
import normal.server.service.GreetingService;
import normal.server.service.impl.CalculateServiceImpl;
import normal.server.service.impl.GreetingServiceImpl;

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
