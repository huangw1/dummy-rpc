package netty.rpc.test.service;

/**
 * Created by huangw1 on 2017/12/14.
 */
public interface CalculateService {

    String add(Integer a, Integer b);

    String minus(Integer a, Integer b);
}
