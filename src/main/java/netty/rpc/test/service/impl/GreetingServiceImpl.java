package netty.rpc.test.service.impl;

import netty.rpc.test.service.GreetingService;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class GreetingServiceImpl implements GreetingService {

    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayGoodBye(String name) {
        return "Bye " + name;
    }
}
