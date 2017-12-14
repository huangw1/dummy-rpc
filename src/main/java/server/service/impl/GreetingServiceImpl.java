package server.service.impl;

import server.service.GreetingService;

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
