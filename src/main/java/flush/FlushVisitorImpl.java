package flush;

import flush.paths.BitFilePath;
import flush.paths.LevelPath;
import utils.BitFilePathCollector;

import java.io.IOException;
import java.util.Objects;

public class FlushVisitorImpl implements IFlushVisitor {

    FlushStore store;
    public FlushVisitorImpl(FlushStore store){
        Objects.requireNonNull(store);
        this.store = store;
    }

    @Override
    public void visit(LevelPath path) {
        if(BitFilePathCollector.EOF == path.getPath()){
            System.out.println("Closing store as EOF reached.");
            store.close();
            return;
        }

        try {
            System.out.println("save level to DB/File - " + path.getPath());
            store.store(path.getPath().toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(BitFilePath path) {

        if(BitFilePathCollector.EOF == path.getPath()){
            System.out.println("Closing store as EOF reached.");
            store.close();
            return;
        }

        try {
            System.out.println("save bitfilepath to DB/File - " + path.getPath());
            store.store(path.getPath().toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

