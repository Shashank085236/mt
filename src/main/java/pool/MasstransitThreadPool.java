package pool;

import tasks.Task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MasstransitThreadPool {

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadPoolExecutor service;

    public MasstransitThreadPool(MassTransitThreadPoolConfig config){
        //service = Executors.newWorkStealingPool();
        service = new ThreadPoolExecutor(
                config.getCorePoolSize(),
                config.getMaxPoolSize(),
                config.getKeepAlive(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                runnable -> {
                    Thread t = new Thread(Thread.currentThread().getThreadGroup(), runnable,
                    config.getThreadName() + threadNumber.getAndIncrement(), 0);
                    if (t.isDaemon()) {t.setDaemon(false);}
                    if (t.getPriority() != Thread.NORM_PRIORITY) { t.setPriority(Thread.NORM_PRIORITY); }
                    return t;
                });
    }


    public void execute(Task task){
        service.execute(task);
    }

    public void shutdown(){
        if(null != service) {
            service.shutdown();
        }
    }

}
