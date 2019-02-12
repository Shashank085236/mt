package flush;

import java.util.HashMap;
import java.util.Map;

/**
 * Writer Instance should be created one per RT
 */
public class FileMergerPoolManager {

    private static final Map<String, FlushStore> WRITER_POOL = new HashMap<>();
    private final FileMergerPoolManager INSTANCE = new FileMergerPoolManager();

    private FileMergerPoolManager(){}

    public FileMergerPoolManager getInstance() {
        return INSTANCE;
    }

    public static FlushStore getWriter(String fqfn){
        FlushStore writer =  WRITER_POOL.get(fqfn);
        if(null == writer){
            synchronized (WRITER_POOL) {
                writer = new OnDemandInventoryFilesWriterImpl(fqfn, true);
                WRITER_POOL.put(fqfn, writer);
            }
        }
        return writer;
    }

}
