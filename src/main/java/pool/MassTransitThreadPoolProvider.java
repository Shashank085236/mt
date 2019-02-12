package pool;

import java.util.HashMap;
import java.util.Map;

public class MassTransitThreadPoolProvider {

    public enum POOL_TYPE { L_POOL, B_POOL, M_POOL }
    private static Map<POOL_TYPE, MasstransitThreadPool> pool_set = new HashMap<>(POOL_TYPE.values().length);

    public static MasstransitThreadPool getPool(MassTransitThreadPoolConfig config){
        MasstransitThreadPool masstransitThreadPool = pool_set.get(config.getType());
        if(null == masstransitThreadPool) {
            synchronized (pool_set) {
                if(null == masstransitThreadPool){
                    pool_set.put(config.getType(), new MasstransitThreadPool(config));
                    masstransitThreadPool = pool_set.get(config.getType());
                }
            }
        }
        return masstransitThreadPool;
    }

}
