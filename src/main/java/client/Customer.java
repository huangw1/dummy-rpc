package client;

import rpc.RpcFramework;
import server.service.CalculateService;
import server.service.GreetingService;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class Customer {

    public static void main(String[] args) {

        try {
            GreetingService greetingService = RpcFramework.subscribe(GreetingService.class, "127.0.0.1", 3456);
            CalculateService calculateService = RpcFramework.subscribe(CalculateService.class, "127.0.0.1", 5634);

            String hello = greetingService.sayHello("huang");
            String bye = greetingService.sayGoodBye("huang");
            System.out.println(hello);
            System.out.println(bye);

            String add = calculateService.add(1, 2);
            String minus = calculateService.minus(1, 2);
            System.out.println(add);
            System.out.println(minus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
