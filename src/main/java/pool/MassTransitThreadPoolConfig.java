package pool;

import java.sql.Timestamp;

public class MassTransitThreadPoolConfig {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final long keepAlive; //in ms
    private final MassTransitThreadPoolProvider.POOL_TYPE type;

    public static class Builder{
        private final int corePoolSize;
        private final MassTransitThreadPoolProvider.POOL_TYPE type;
        private int maxPoolSize;
        private long keepAlive;

        public Builder(int corePoolSize, MassTransitThreadPoolProvider.POOL_TYPE type){
            this.corePoolSize = corePoolSize;
            this.maxPoolSize = corePoolSize;
            this.keepAlive = 0L;
            this.type = type;
        }

        public Builder maxPoolSize(int maxPoolSize){
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public Builder keepAlive(long keepAlive){
            this.keepAlive = keepAlive;
            return this;
        }

        public MassTransitThreadPoolConfig build(){
            return new MassTransitThreadPoolConfig(this);
        }
    }

    public MassTransitThreadPoolConfig(Builder builder){
        this.corePoolSize = builder.corePoolSize;
        this.maxPoolSize = builder.maxPoolSize;
        this.keepAlive = builder.keepAlive;
        this.type = builder.type;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }


    public int getMaxPoolSize() {
        return maxPoolSize;
    }



    public long getKeepAlive() {
        return keepAlive;
    }


    public String getThreadName() {
        return getType().name()+"-MasstransitThread-";
    }

    public MassTransitThreadPoolProvider.POOL_TYPE getType() {
        return type;
    }

}
