package normal.rpc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangw1 on 2017/12/14.
 */
public class ThreadPoolHelper {

    private static final int nThreads = 10;
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(nThreads);
    }

    public static ExecutorService getExecutorInstance() {
        return executor;
    }
}
