package netty.rpc.test.service.impl;

import netty.rpc.test.service.CalculateService;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class CalculateServiceImpl implements CalculateService {

    public String add(Integer a, Integer b) {
        return a + " + " + b + " = " + (a + b);
    }

    public String minus(Integer a, Integer b) {
        return a + " - " + b + " = " + (a - b);
    }
}
