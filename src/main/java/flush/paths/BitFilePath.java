package flush.paths;

import flush.IFlushVisitor;

import java.nio.file.Path;

public class BitFilePath implements IPath{

    private Path path;
    private String RT;

    public BitFilePath(Path path, String RT) {
        this.path = path;
        this.RT = RT;
    }

    @Override
    public Path getPath(){ return path; }

    @Override
    public String getRT() {
        return this.RT;
    }

    @Override
    public int getDepth() {
        return 1;
    }

    @Override
    public TYPE getType(){
        return TYPE.BIT_FILE;
    }


    @Override
    public void accept(IFlushVisitor visitor) {
        visitor.visit(this);
    }
}
